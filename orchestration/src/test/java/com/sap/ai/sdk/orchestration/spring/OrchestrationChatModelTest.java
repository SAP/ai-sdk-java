package com.sap.ai.sdk.orchestration.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO_16K;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

@WireMockTest
public class OrchestrationChatModelTest {

  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private static OrchestrationChatModel client;
  private static OrchestrationModuleConfig config;
  private static Prompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationChatModel(new OrchestrationClient(destination));
    config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO_16K);
    prompt =
        new Prompt(
            "Hello World! Why is this phrase so famous?", new OrchestrationChatOptions(config));
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void testCompletion() {
    stubFor(
        post(urlPathEqualTo("/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var result = client.call(prompt);

    assertThat(result).isNotNull();
    assertThat(result.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testThrowsOnMissingChatOptions() {
    assertThatThrownBy(() -> client.call(new Prompt("test")))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please add OrchestrationChatOptions to the Prompt");
    assertThatThrownBy(() -> client.stream(new Prompt("test")))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please add OrchestrationChatOptions to the Prompt");
  }

  @Test
  void testThrowsOnMissingLlmConfig() {
    OrchestrationChatOptions emptyConfig =
        new OrchestrationChatOptions(new OrchestrationModuleConfig());

    assertThatThrownBy(() -> client.call(new Prompt("test", emptyConfig)))
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM config is required");
    assertThatThrownBy(() -> client.stream(new Prompt("test", emptyConfig)))
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM config is required");
  }

  @Disabled
  @Test
  void streamChatCompletionOutputFilterErrorHandling() throws IOException {
    try (var inputStream = spy(fileLoader.apply("streamChatCompletionOutputFilter.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      Flux<ChatResponse> flux = client.stream(prompt);
      assertThatThrownBy(() -> flux.toStream().forEach(System.out::println))
          .isInstanceOf(OrchestrationClientException.class)
          .hasMessage("Content filter filtered the output.");

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void testStreamCompletion() throws IOException {
    try (var inputStream = spy(fileLoader.apply("streamChatCompletion.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-flux");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      Flux<ChatResponse> flux = client.stream(prompt);
      var deltaList = flux.toStream().toList();

      assertThat(deltaList).hasSize(3);
      // the first delta doesn't have any content
      assertThat(deltaList.get(0).getResult().getOutput().getContent()).isEqualTo("");
      assertThat(deltaList.get(1).getResult().getOutput().getContent()).isEqualTo("Sure");
      assertThat(deltaList.get(2).getResult().getOutput().getContent()).isEqualTo("!");

      assertThat(deltaList.get(1).getResult().getMetadata().getFinishReason()).isEqualTo("");
      assertThat(deltaList.get(1).getResult().getMetadata().getFinishReason()).isEqualTo("");
      assertThat(deltaList.get(2).getResult().getMetadata().getFinishReason()).isEqualTo("stop");

      Mockito.verify(inputStream, times(1)).close();
    }
  }
}
