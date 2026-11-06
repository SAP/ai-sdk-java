package com.sap.ai.sdk.foundationmodels.anthropic;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.anthropic.errors.AnthropicException;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.RawContentBlockDeltaEvent;
import com.anthropic.models.messages.StopReason;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class AiCoreAnthropicClientTest {

  private AiCoreAnthropicClient client;
  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = AiCoreAnthropicClient.forDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testAnthropicModels() {
    assertThat(AnthropicModel.CLAUDE_3_HAIKU.name()).isEqualTo("anthropic--claude-3-haiku");
    assertThat(AnthropicModel.CLAUDE_3_HAIKU.version()).isNull();

    assertThat(AnthropicModel.CLAUDE_45_SONNET.name()).isEqualTo("anthropic--claude-4.5-sonnet");

    final var modelWithVersion = AnthropicModel.CLAUDE_3_HAIKU.withVersion("v1");
    assertThat(modelWithVersion.name()).isEqualTo("anthropic--claude-3-haiku");
    assertThat(modelWithVersion.version()).isEqualTo("v1");
  }

  @Test
  void testChatCompletion() throws IOException {
    stubFor(
        post(urlPathEqualTo("/invoke"))
            .willReturn(
                aResponse()
                    .withBodyFile("invokeResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var params =
        MessageCreateParams.builder()
            .model("claude-3-haiku-20240307")
            .maxTokens(100)
            .addSystemMessage("You are a helpful assistant.")
            .addUserMessage("Hello!")
            .build();

    client.messages().create(params);

    try (var requestStream = fileLoader.apply("chatCompletionRequest.json")) {
      final var expectedRequest = new String(requestStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/invoke"))
              .withHeader("Content-Type", equalTo("application/json"))
              .withRequestBody(equalToJson(expectedRequest)));
    }

    final var response = client.messages().create(params);

    assertThat(response).isNotNull();
    assertThat(response.id()).isEqualTo("msg_01XFDUDYJgAACzvnptvVoYEL");
    assertThat(response.stopReason()).hasValue(StopReason.END_TURN);
    assertThat(response.content()).hasSize(1);
    assertThat(response.content().get(0).text().map(TextBlock::text))
        .hasValue("Hello! How can I assist you today?");
    assertThat(response.usage().inputTokens()).isEqualTo(10);
    assertThat(response.usage().outputTokens()).isEqualTo(9);
  }

  @Test
  void testModelFieldRemovedFromRequest() {
    stubFor(
        post(urlPathEqualTo("/invoke"))
            .willReturn(
                aResponse()
                    .withBodyFile("invokeResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var params =
        MessageCreateParams.builder()
            .model("claude-3-haiku-20240307")
            .maxTokens(256)
            .addUserMessage("Hello!")
            .build();

    client.messages().create(params);

    verify(
        postRequestedFor(urlPathEqualTo("/invoke")).withRequestBody(notMatching(".*\"model\".*")));
  }

  @Test
  void testStreamChatCompletion() throws IOException {
    final var httpClient = mock(HttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    mockResponse.setEntity(
        new InputStreamEntity(
            Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("invokeStreamResponse.txt")),
            ContentType.TEXT_EVENT_STREAM));
    mockResponse.setHeader("Content-Type", "text/event-stream");
    doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

    final var params =
        MessageCreateParams.builder()
            .model("claude-3-haiku-20240307")
            .maxTokens(256)
            .addUserMessage("Hello!")
            .build();

    try (var stream = client.messages().createStreaming(params)) {
      final var events = stream.stream().toList();

      final var chunks =
          events.stream()
              .filter(RawMessageStreamEvent::isContentBlockDelta)
              .map(RawMessageStreamEvent::asContentBlockDelta)
              .map(RawContentBlockDeltaEvent::delta)
              .filter(RawContentBlockDelta::isText)
              .map(d -> d.asText().text())
              .toList();

      assertThat(chunks).containsExactly("Hello", "!", " How can I", " assist", " you today?");

      final var stopReason =
          events.stream()
              .filter(RawMessageStreamEvent::isMessageDelta)
              .map(e -> e.asMessageDelta().delta().stopReason())
              .filter(Optional::isPresent)
              .map(Optional::get)
              .findFirst();

      assertThat(stopReason).hasValue(StopReason.END_TURN);
    }
  }

  @Test
  void testBuilderErrorHandling() {
    final var params =
        MessageCreateParams.builder().model("claude-3-haiku-20240307").addUserMessage("Hello!");

    assertThatThrownBy(params::build)
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("`maxTokens` is required");
  }

  @Test
  void testErrorHandling() {
    stubFor(
        post(anyUrl())
            .willReturn(
                badRequest()
                    .withBody(
                        """
                        {"type":"error","error":{"type":"invalid_request_error","message":"field_x is required"}}
                        """)
                    .withHeader("Content-Type", "application/json")));

    final var params =
        MessageCreateParams.builder()
            .model("claude-3-haiku-20240307")
            .maxTokens(256)
            .addUserMessage("Hello!")
            .build();

    assertThatThrownBy(() -> client.messages().create(params))
        .isInstanceOf(AnthropicException.class)
        .hasMessageContaining("400")
        .hasMessageContaining("field_x is required");
  }

  @Test
  void testServerError() {
    stubFor(post(anyUrl()).willReturn(serverError()));

    final var params =
        MessageCreateParams.builder()
            .model("claude-3-haiku-20240307")
            .maxTokens(256)
            .addUserMessage("Hello!")
            .build();

    assertThatThrownBy(() -> client.messages().create(params))
        .isInstanceOf(AnthropicException.class)
        .hasMessageContaining("500");
  }
}
