package com.sap.ai.sdk.orchestration.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
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
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.val;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbacks;
import reactor.core.publisher.Flux;

@WireMockTest
public class OrchestrationChatModelTest {

  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private static OrchestrationChatModel client;
  private static OrchestrationChatOptions defaultOptions;
  private static Prompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationChatModel(new OrchestrationClient(destination));
    defaultOptions =
        new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(GPT_4O));
    prompt = new Prompt("Hello World! Why is this phrase so famous?", defaultOptions);
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
    val result = client.call(prompt);

    assertThat(result).isNotNull();
    assertThat(result.getResult().getOutput().getText()).isNotEmpty();
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

  @Test
  void testStreamCompletion() throws IOException {
    try (val inputStream = spy(fileLoader.apply("streamChatCompletion.txt"))) {

      val httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      val mockResponse = new BasicClassicHttpResponse(200, "OK");
      val inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-flux");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      Flux<ChatResponse> flux = client.stream(prompt);
      val deltaList = flux.toStream().toList();

      assertThat(deltaList).hasSize(3);
      // the first delta doesn't have any content
      assertThat(deltaList.get(0).getResult().getOutput().getText()).isEqualTo("");
      assertThat(deltaList.get(1).getResult().getOutput().getText()).isEqualTo("Sure");
      assertThat(deltaList.get(2).getResult().getOutput().getText()).isEqualTo("!");

      assertThat(deltaList.get(0).getResult().getMetadata().getFinishReason()).isEqualTo("");
      assertThat(deltaList.get(1).getResult().getMetadata().getFinishReason()).isEqualTo("");
      assertThat(deltaList.get(2).getResult().getMetadata().getFinishReason()).isEqualTo("stop");

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void testToolCallsWithoutExecution() throws IOException {
    stubFor(
        post(urlPathEqualTo("/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("toolCallsResponse.json")
                    .withHeader("Content-Type", "application/json")));

    defaultOptions.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));
    defaultOptions.setInternalToolExecutionEnabled(false);
    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", defaultOptions);
    val result = client.call(prompt);

    List<ToolCall> toolCalls = result.getResult().getOutput().getToolCalls();
    assertThat(toolCalls).hasSize(2);
    ToolCall toolCall1 = toolCalls.get(0);
    ToolCall toolCall2 = toolCalls.get(1);
    assertThat(toolCall1.type()).isEqualTo("function");
    assertThat(toolCall2.type()).isEqualTo("function");
    assertThat(toolCall1.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall2.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall1.arguments())
        .isEqualTo("{\"arg0\": {\"location\": \"Potsdam\", \"unit\": \"C\"}}");
    assertThat(toolCall2.arguments())
        .isEqualTo("{\"arg0\": {\"location\": \"Toulouse\", \"unit\": \"C\"}}");

    try (var request1InputStream = fileLoader.apply("toolCallsRequest.json")) {
      final String request1 = new String(request1InputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request1)));
    }
  }

  @Test
  void testToolCallsWithExecution() throws IOException {
    // https://platform.openai.com/docs/guides/function-calling
    stubFor(
        post(urlPathEqualTo("/completion"))
            .inScenario("Tool Calls")
            .whenScenarioStateIs(STARTED)
            .willReturn(
                aResponse()
                    .withBodyFile("toolCallsResponse.json")
                    .withHeader("Content-Type", "application/json"))
            .willSetStateTo("Second Call"));

    stubFor(
        post(urlPathEqualTo("/completion"))
            .inScenario("Tool Calls")
            .whenScenarioStateIs("Second Call")
            .willReturn(
                aResponse()
                    .withBodyFile("toolCallsResponse2.json")
                    .withHeader("Content-Type", "application/json")));

    defaultOptions.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));
    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", defaultOptions);
    val result = client.call(prompt);

    assertThat(result.getResult().getOutput().getText())
        .isEqualTo("The current temperature in Potsdam is 30°C and in Toulouse 30°C.");

    try (var request1InputStream = fileLoader.apply("toolCallsRequest.json")) {
      try (var request2InputStream = fileLoader.apply("toolCallsRequest2.json")) {
        final String request1 = new String(request1InputStream.readAllBytes());
        final String request2 = new String(request2InputStream.readAllBytes());
        verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request1)));
        verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request2)));
      }
    }
  }

  @Test
  void testChatMemory() throws IOException {
    stubFor(
        post(urlPathEqualTo("/completion"))
            .inScenario("Chat Memory")
            .whenScenarioStateIs(STARTED)
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json") // The response is not important
                    .withHeader("Content-Type", "application/json"))
            .willSetStateTo("Second Call"));

    stubFor(
        post(urlPathEqualTo("/completion"))
            .inScenario("Chat Memory")
            .whenScenarioStateIs("Second Call")
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json") // The response is not important
                    .withHeader("Content-Type", "application/json")));

    val memory = new InMemoryChatMemory();
    val advisor = new MessageChatMemoryAdvisor(memory);
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?", defaultOptions);
    val prompt2 = new Prompt("And what is the typical food there?", defaultOptions);

    cl.prompt(prompt1).call().content();
    cl.prompt(prompt2).call().content();
    // The response is not important
    // We just want to verify that the second call remembered the first call
    try (var requestInputStream = fileLoader.apply("chatMemory.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }
}
