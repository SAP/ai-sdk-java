package com.sap.ai.sdk.foundationmodels.anthropic;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
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
import static org.mockito.Mockito.spy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class AnthropicClientTest {

  private AnthropicClient client;

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final var destination = DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = AnthropicClient.forDestination(destination);
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
  void testChatCompletion(final WireMockRuntimeInfo server) {
    stubFor(
        post(urlPathEqualTo("/invoke"))
            .willReturn(
                aResponse()
                    .withBodyFile("invokeResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var request =
        new AnthropicChatCompletionRequest().withUserMessage("Hello!").withMaxTokens(256);

    final var response = client.chatCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("msg_01XFDUDYJgAACzvnptvVoYEL");
    assertThat(response.getRole()).isEqualTo("assistant");
    assertThat(response.getStopReason()).isEqualTo("end_turn");
    assertThat(response.getContent()).isEqualTo("Hello! How can I assist you today?");
    assertThat(response.getUsage()).isNotNull();
    assertThat(response.getUsage().getInputTokens()).isEqualTo(10);
    assertThat(response.getUsage().getOutputTokens()).isEqualTo(9);
  }

  @Test
  void testChatCompletionRequestFormat(final WireMockRuntimeInfo server) {
    stubFor(
        post(urlPathEqualTo("/invoke"))
            .willReturn(
                aResponse()
                    .withBodyFile("invokeResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var request =
        new AnthropicChatCompletionRequest()
            .withSystemPrompt("You are a helpful assistant.")
            .withUserMessage("What is 2+2?")
            .withMaxTokens(100)
            .withTemperature(0.7);

    client.chatCompletion(request);

    verify(
        postRequestedFor(urlPathEqualTo("/invoke"))
            .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
            .withRequestBody(
                com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath(
                    "$.anthropic_version", equalTo("bedrock-2023-05-31")))
            .withRequestBody(
                com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath(
                    "$.messages[0].role", equalTo("user")))
            .withRequestBody(
                com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath(
                    "$.system", equalTo("You are a helpful assistant."))));
  }

  @Test
  void testChatCompletionMultiTurn(final WireMockRuntimeInfo server) {
    stubFor(
        post(urlPathEqualTo("/invoke"))
            .willReturn(
                aResponse()
                    .withBodyFile("invokeResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var request =
        new AnthropicChatCompletionRequest()
            .withUserMessage("Hello!")
            .withAssistantMessage("Hi there! How can I help?")
            .withUserMessage("What is the capital of France?")
            .withMaxTokens(256);

    final var response = client.chatCompletion(request);

    assertThat(response).isNotNull();
    assertThat(request.getMessages()).hasSize(3);
    assertThat(request.getMessages().get(0).getRole()).isEqualTo(AnthropicChatMessage.Role.USER);
    assertThat(request.getMessages().get(1).getRole())
        .isEqualTo(AnthropicChatMessage.Role.ASSISTANT);
    assertThat(request.getMessages().get(2).getRole()).isEqualTo(AnthropicChatMessage.Role.USER);
  }

  @Test
  void testStreamChatCompletion() throws IOException {
    final var inputStream =
        spy(
            Objects.requireNonNull(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("__files/invokeStreamResponse.txt")));

    final var httpClient = mock(HttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_EVENT_STREAM);
    mockResponse.setEntity(entity);
    mockResponse.setHeader("Content-Type", "text/event-stream");

    doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

    final var request =
        new AnthropicChatCompletionRequest().withUserMessage("Hello!").withMaxTokens(256);

    try (var stream = client.streamChatCompletion(request)) {
      final var chunks =
          stream
              .map(AnthropicChatCompletionDelta::getDeltaContent)
              .filter(s -> !s.isEmpty())
              .collect(Collectors.toList());

      assertThat(chunks).containsExactly("Hello", "!", " How can I", " assist", " you today?");
    }
  }

  @Test
  void testStreamChatCompletionReturnsStopReason() throws IOException {
    final var inputStream =
        spy(
            Objects.requireNonNull(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("__files/invokeStreamResponse.txt")));

    final var httpClient = mock(HttpClient.class);
    ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

    final var mockResponse = new BasicClassicHttpResponse(200, "OK");
    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_EVENT_STREAM);
    mockResponse.setEntity(entity);
    mockResponse.setHeader("Content-Type", "text/event-stream");

    doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

    final var request =
        new AnthropicChatCompletionRequest().withUserMessage("Hello!").withMaxTokens(256);

    try (var stream = client.streamChatCompletion(request)) {
      final var allDeltas = stream.collect(Collectors.toList());
      final var stopDelta = allDeltas.stream().filter(d -> d.getStopReason() != null).findFirst();

      assertThat(stopDelta).isPresent();
      assertThat(stopDelta.get().getStopReason()).isEqualTo("end_turn");
    }
  }

  @Test
  void testErrorHandling() {
    final var errorJson =
        """
        {"type":"error","error":{"type":"invalid_request_error","message":"max_tokens is required"}}
        """;
    stubFor(
        post(anyUrl())
            .willReturn(
                badRequest().withBody(errorJson).withHeader("Content-Type", "application/json")));

    final var request = new AnthropicChatCompletionRequest().withUserMessage("Hello!");

    assertThatThrownBy(() -> client.chatCompletion(request))
        .isInstanceOf(AnthropicClientException.class)
        .hasMessageContaining("400");
  }

  @Test
  void testServerError() {
    stubFor(post(anyUrl()).willReturn(serverError()));

    final var request = new AnthropicChatCompletionRequest().withUserMessage("Hello!");

    assertThatThrownBy(() -> client.chatCompletion(request))
        .isInstanceOf(AnthropicClientException.class)
        .hasMessageContaining("500");
  }

  @Test
  void testAnthropicMessage() {
    final var userMsg = AnthropicChatMessage.ofUser("Hello");
    assertThat(userMsg.getRole()).isEqualTo(AnthropicChatMessage.Role.USER);
    assertThat(userMsg.getContent()).hasSize(1);
    assertThat(userMsg.getContent().get(0).getText()).isEqualTo("Hello");

    final var assistantMsg = AnthropicChatMessage.ofAssistant("Hi there");
    assertThat(assistantMsg.getRole()).isEqualTo(AnthropicChatMessage.Role.ASSISTANT);
    assertThat(assistantMsg.getContent().get(0).getText()).isEqualTo("Hi there");
  }

  @Test
  void testAnthropicDeltaGetContent() {
    final var delta = new AnthropicChatCompletionDelta();
    assertThat(delta.getDeltaContent()).isEmpty();
    assertThat(delta.getStopReason()).isNull();

    final var innerDelta = new AnthropicChatCompletionDelta.Delta().setText("Hello");
    delta.setDelta(innerDelta);
    assertThat(delta.getDeltaContent()).isEqualTo("Hello");
  }

  @Test
  void testEmptyContentResponse() {
    final var response = new AnthropicChatCompletionResponse();
    assertThat(response.getContent()).isEmpty();

    final var responseWithEmptyList = new AnthropicChatCompletionResponse();
    responseWithEmptyList.setContent(List.of());
    assertThat(responseWithEmptyList.getContent()).isEmpty();
  }

  @Test
  void testRequestAnthropicVersionDefault() {
    final var request = new AnthropicChatCompletionRequest();
    assertThat(request.getAnthropicVersion()).isEqualTo("bedrock-2023-05-31");
  }
}
