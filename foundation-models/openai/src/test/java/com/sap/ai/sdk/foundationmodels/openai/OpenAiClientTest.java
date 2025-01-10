package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionResponseMessageRole.ASSISTANT;
import static com.sap.ai.sdk.foundationmodels.openai.model2.ContentFilterSeverityResult.SeverityEnum.SAFE;
import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponse.ObjectEnum.UNKNOWN_DEFAULT_OPEN_API;
import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.STOP;
import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse.ObjectEnum.CHAT_COMPLETION_CHUNK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestSystemMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.model2.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ContentFilterChoiceResults;
import com.sap.ai.sdk.foundationmodels.openai.model2.ContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponseChoicesInner;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponseChoicesInner;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequestInput;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import io.vavr.control.Try;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

@WireMockTest
class OpenAiClientTest {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static OpenAiClient client;
  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = OpenAiClient.withCustomDestination(destination);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @AfterEach
  void reset() {
    ApacheHttpClient5Accessor.setHttpClientCache(null);
    ApacheHttpClient5Accessor.setHttpClientFactory(null);
  }

  @Test
  void apiVersion() {
    stubFor(post(anyUrl()).willReturn(okJson("{}")));
    Try.of(() -> client.chatCompletion(new CreateChatCompletionRequest()));

    verify(
        exactly(1),
        postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("2024-02-01")));

    Try.of(() -> client.withApiVersion("fooBar").chatCompletion(new CreateChatCompletionRequest()));
    verify(exactly(1), postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("fooBar")));

    assertThat(client)
        .describedAs(
            "withApiVersion should return a new object, the sut object should remain unchanged")
        .isNotSameAs(client.withApiVersion("fooBar"));
    Try.of(() -> client.chatCompletion(new CreateChatCompletionRequest()));
    verify(
        exactly(2),
        postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("2024-02-01")));
  }

  private static Runnable[] errorHandlingCalls() {
    return new Runnable[] {
      () -> client.chatCompletion(new CreateChatCompletionRequest()),
      () ->
          client
              .streamChatCompletionDeltas(new CreateChatCompletionRequest())
              // the stream needs to be consumed to parse the response
              .forEach(System.out::println)
    };
  }

  @ParameterizedTest
  @MethodSource("errorHandlingCalls")
  void chatCompletionErrorHandling(@Nonnull final Runnable request) {
    final var errorJson =
        """
            { "error": { "code": null, "message": "foo", "type": "invalid stuff" } }
            """;
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(serverError())
            .willSetStateTo("1"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("1")
            .willReturn(
                badRequest().withBody(errorJson).withHeader("Content-type", "application/json"))
            .willSetStateTo("2"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("2")
            .willReturn(
                badRequest()
                    .withBody("{ broken json")
                    .withHeader("Content-type", "application/json"))
            .willSetStateTo("3"));
    stubFor(
        post(anyUrl())
            .inScenario("Errors")
            .whenScenarioStateIs("3")
            .willReturn(okXml("<xml></xml>"))
            .willSetStateTo("4"));
    stubFor(post(anyUrl()).inScenario("Errors").whenScenarioStateIs("4").willReturn(noContent()));

    final var softly = new SoftAssertions();

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Server errors should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("500");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Error objects from OpenAI should be interpreted")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("error message: 'foo'");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Failures while parsing error message should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("400")
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(JsonParseException.class);

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Non-JSON responses should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("Failed to parse");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Empty responses should be handled")
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("was empty");

    softly.assertAll();
  }

  private static Callable<?>[] chatCompletionCalls() {
    return new Callable[] {
      () -> {
        final var systemMessage =
            new ChatCompletionRequestSystemMessage()
                .role(ChatCompletionRequestSystemMessage.RoleEnum.SYSTEM)
                .content(ChatCompletionRequestSystemMessageContent.create("You are a helpful AI"));
        final var userMessage =
            new ChatCompletionRequestUserMessage()
                .role(ChatCompletionRequestUserMessage.RoleEnum.USER)
                .content(
                    ChatCompletionRequestUserMessageContent.create(
                        "Hello World! Why is this phrase so famous?"));
        final var request =
            new CreateChatCompletionRequest()
                .addMessagesItem(systemMessage)
                .addMessagesItem(userMessage);
        return client.chatCompletion(request);
      },
      () ->
          client
              .withSystemPrompt("You are a helpful AI")
              .chatCompletion("Hello World! Why is this phrase so famous?")
    };
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource("chatCompletionCalls")
  void chatCompletion(@Nonnull final Callable<CreateChatCompletionResponse> request) {
    try (var inputStream = fileLoader.apply("__files/chatCompletionResponse.json")) {

      final String response = new String(inputStream.readAllBytes());
      // with query parameter api-version=2024-02-01
      stubFor(
          post(urlPathEqualTo("/chat/completions"))
              .withQueryParam("api-version", equalTo("2024-02-01"))
              .willReturn(okJson(response)));

      final CreateChatCompletionResponse result = request.call();

      assertThat(result).isNotNull();
      assertThat(result.getCreated()).isEqualTo(1727436279);
      assertThat(result.getId()).isEqualTo("chatcmpl-AC3NPPYlxem8kRBBAX9EBObMMsrnf");
      assertThat(result.getModel()).isEqualTo("gpt-35-turbo");
      assertThat(result.getObject().getValue()).isEqualTo("chat.completion");
      assertThat(result.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

      assertThat(result.getUsage()).isNotNull();
      assertThat(result.getUsage().getCompletionTokens()).isEqualTo(20);
      assertThat(result.getUsage().getPromptTokens()).isEqualTo(13);
      assertThat(result.getUsage().getTotalTokens()).isEqualTo(33);

      assertThat(result.getPromptFilterResults()).hasSize(1);
      assertThat(result.getPromptFilterResults().get(0).getPromptIndex()).isEqualTo(0);
      ContentFilterPromptResults promptFilterResults =
          result.getPromptFilterResults().get(0).getContentFilterResults();
      assertThat(promptFilterResults).isNotNull();
      assertThat(promptFilterResults.getSexual()).isNotNull();
      assertThat(promptFilterResults.getSexual().isFiltered()).isFalse();
      assertThat(promptFilterResults.getSexual().getSeverity()).isEqualTo(SAFE);
      assertThat(promptFilterResults.getViolence()).isNotNull();
      assertThat(promptFilterResults.getViolence().isFiltered()).isFalse();
      assertThat(promptFilterResults.getViolence().getSeverity()).isEqualTo(SAFE);
      assertThat(promptFilterResults.getHate()).isNotNull();
      assertThat(promptFilterResults.getHate().isFiltered()).isFalse();
      assertThat(promptFilterResults.getHate().getSeverity()).isEqualTo(SAFE);
      assertThat(promptFilterResults.getSelfHarm()).isNotNull();
      assertThat(promptFilterResults.getSelfHarm().getSeverity()).isEqualTo(SAFE);
      assertThat(promptFilterResults.getSelfHarm().isFiltered()).isFalse();
      assertThat(promptFilterResults.getProfanity()).isNull();
      assertThat(promptFilterResults.getError()).isNull();
      assertThat(promptFilterResults.getJailbreak()).isNull();

      assertThat(result.getChoices()).hasSize(1);
      CreateChatCompletionResponseChoicesInner choice = result.getChoices().get(0);
      assertThat(choice.getFinishReason()).isEqualTo(STOP);
      assertThat(choice.getIndex()).isEqualTo(0);
      assertThat(choice.getMessage().getContent())
          .isEqualTo(
              "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");
      assertThat(choice.getMessage().getRole()).isEqualTo(ASSISTANT);
      assertThat(choice.getMessage().getToolCalls()).isNull();

      ContentFilterChoiceResults contentFilterResults = choice.getContentFilterResults();
      assertThat(contentFilterResults).isNotNull();
      assertThat(contentFilterResults.getSexual()).isNotNull();
      assertThat(contentFilterResults.getSexual().isFiltered()).isFalse();
      assertThat(contentFilterResults.getSexual().getSeverity()).isEqualTo(SAFE);
      assertThat(contentFilterResults.getViolence()).isNotNull();
      assertThat(contentFilterResults.getViolence().isFiltered()).isFalse();
      assertThat(contentFilterResults.getViolence().getSeverity()).isEqualTo(SAFE);
      assertThat(contentFilterResults.getHate()).isNotNull();
      assertThat(contentFilterResults.getHate().isFiltered()).isFalse();
      assertThat(contentFilterResults.getHate().getSeverity()).isEqualTo(SAFE);
      assertThat(contentFilterResults.getSelfHarm()).isNotNull();
      assertThat(contentFilterResults.getSelfHarm().getSeverity()).isEqualTo(SAFE);
      assertThat(contentFilterResults.getSelfHarm().isFiltered()).isFalse();
      assertThat(contentFilterResults.getProfanity()).isNull();
      assertThat(contentFilterResults.getError()).isNull();
      // assertThat(contentFilterResults.getJailbreak()).isNull();

      verify(
          postRequestedFor(urlPathEqualTo("/chat/completions"))
              .withQueryParam("api-version", equalTo("2024-02-01"))
              .withRequestBody(
                  equalToJson(
                      """
                      {
                             "temperature" : 1,
                             "top_p" : 1,
                             "stream" : false,
                             "presence_penalty" : 0,
                             "frequency_penalty" : 0,
                             "messages" : [ {
                               "content" : "You are a helpful AI",
                               "role" : "system"
                             }, {
                               "content" : "Hello World! Why is this phrase so famous?",
                               "role" : "user"
                             } ],
                             "logprobs" : false,
                             "n" : 1,
                             "parallel_tool_calls" : true,
                             "tools" : [ ],
                             "functions" : [ ]
                      }""")));
    }
  }

  @Test
  @DisplayName("Chat history is not implemented yet")
  void history() {
    stubFor(
        post(urlPathEqualTo("/chat/completions"))
            .willReturn(
                aResponse()
                    .withBodyFile("chatCompletionResponse.json")
                    .withHeader("Content-Type", "application/json")));

    client.withSystemPrompt("system prompt").chatCompletion("chat completion 1");

    verify(
        exactly(1),
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withRequestBody(
                equalToJson(
                    """
                      {
                           "temperature" : 1,
                           "top_p" : 1,
                           "stream" : false,
                           "presence_penalty" : 0,
                           "frequency_penalty" : 0,
                           "messages" : [ {
                             "content" : "system prompt",
                             "role" : "system"
                           }, {
                             "content" : "chat completion 1",
                             "role" : "user"
                           } ],
                           "logprobs" : false,
                           "n" : 1,
                           "parallel_tool_calls" : true,
                           "tools" : [ ],
                           "functions" : [ ]
                      }""")));

    client.withSystemPrompt("system prompt").chatCompletion("chat completion 2");

    verify(
        exactly(1),
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withRequestBody(
                equalToJson(
                    """
                      {
                           "temperature" : 1,
                           "top_p" : 1,
                           "stream" : false,
                           "presence_penalty" : 0,
                           "frequency_penalty" : 0,
                           "messages" : [ {
                             "content" : "system prompt",
                             "role" : "system"
                           }, {
                             "content" : "chat completion 2",
                             "role" : "user"
                           } ],
                           "logprobs" : false,
                           "n" : 1,
                           "parallel_tool_calls" : true,
                           "tools" : [ ],
                           "functions" : [ ]
                      }""")));
  }

  @Test
  void embedding() {
    stubFor(
        post(urlPathEqualTo("/embeddings"))
            .willReturn(
                aResponse()
                    .withBodyFile("embeddingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var request =
        new EmbeddingsCreateRequest().input(EmbeddingsCreateRequestInput.create("Hello World"));
    final var result = client.embedding(request);

    assertThat(result).isNotNull();
    assertThat(result.getModel()).isEqualTo("ada");
    assertThat(result.getObject()).isEqualTo("list");

    assertThat(result.getUsage()).isNotNull();
    assertThat(result.getUsage().getCustomFieldNames()).doesNotContain("completion_tokens");
    assertThat(result.getUsage().getPromptTokens()).isEqualTo(2);
    assertThat(result.getUsage().getTotalTokens()).isEqualTo(2);

    assertThat(result.getData()).isNotNull().hasSize(1);
    var embeddingData = result.getData().get(0);
    assertThat(embeddingData).isNotNull();
    assertThat(embeddingData.getObject()).isEqualTo("embedding");
    assertThat(embeddingData.getIndex()).isEqualTo(0);
    assertThat(embeddingData.getEmbedding())
        .isNotNull()
        .isNotEmpty()
        .containsExactly(
            new BigDecimal("0.0"),
            new BigDecimal("3.4028235E+38"),
            new BigDecimal("1.4E-45"),
            new BigDecimal("1.23"),
            new BigDecimal("-4.56"));

    verify(
        postRequestedFor(urlPathEqualTo("/embeddings"))
            .withRequestBody(equalToJson("""
                      {"input": "Hello World" }""")));
  }

  @Test
  void testThrowsOnContentFilter() {
    var mock = mock(OpenAiClient.class);
    when(mock.streamChatCompletion(any())).thenCallRealMethod();

    var deltaWithContentFilter =
        mock(com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta.class);
    when(deltaWithContentFilter.getFinishReason()).thenReturn("content_filter");
    when(mock.streamChatCompletionDeltas(any())).thenReturn(Stream.of(deltaWithContentFilter));

    // this must not throw, since the stream is lazily evaluated
    var stream = mock.streamChatCompletion("");
    assertThatThrownBy(stream::toList)
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("Content filter");
  }

  @Test
  void streamChatCompletionDeltasErrorHandling() throws IOException {
    try (var inputStream = spy(fileLoader.apply("streamChatCompletionError.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      final var userMessage =
          new ChatCompletionRequestUserMessage()
              .role(ChatCompletionRequestUserMessage.RoleEnum.USER)
              .content(
                  ChatCompletionRequestUserMessageContent.create(
                      "Can you give me the first 100 numbers of the Fibonacci sequence?"));
      final var request = new CreateChatCompletionRequest().addMessagesItem(userMessage);

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {
        assertThatThrownBy(() -> stream.forEach(System.out::println))
            .isInstanceOf(OpenAiClientException.class)
            .hasMessage("Failed to parse response and error message: 'exceeded token rate limit'");
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void streamChatCompletionDeltas() throws IOException {
    try (var inputStream = spy(fileLoader.apply("streamChatCompletion.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      final var userMessage =
          new ChatCompletionRequestUserMessage()
              .role(ChatCompletionRequestUserMessage.RoleEnum.USER)
              .content(
                  ChatCompletionRequestUserMessageContent.create(
                      "Can you give me the first 100 numbers of the Fibonacci sequence?"));
      final var request = new CreateChatCompletionRequest().addMessagesItem(userMessage);

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {

        final List<OpenAiChatCompletionDelta> deltaList = stream.toList();
        final var delta0 = (CreateChatCompletionResponse) deltaList.get(0).getOriginalResponse();
        final var delta1 =
            (CreateChatCompletionStreamResponse) deltaList.get(1).getOriginalResponse();
        final var delta2 =
            (CreateChatCompletionStreamResponse) deltaList.get(2).getOriginalResponse();
        final var delta3 =
            (CreateChatCompletionStreamResponse) deltaList.get(3).getOriginalResponse();
        final var delta4 =
            (CreateChatCompletionStreamResponse) deltaList.get(4).getOriginalResponse();

        assertThat(deltaList).hasSize(5);
        // the first two and the last delta don't have any content
        assertThat(deltaList.get(0).getDeltaContent()).isEqualTo("");
        assertThat(deltaList.get(1).getDeltaContent()).isEqualTo("");
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");
        assertThat(deltaList.get(4).getDeltaContent()).isEqualTo("");

        assertThat(delta0.getSystemFingerprint()).isNull();
        assertThat(delta1.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta2.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta3.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta4.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

        assertThat(delta0.getUsage()).isNull();
        assertThat(delta1.getCustomField("usage")).isNull();
        assertThat(delta2.getCustomField("usage")).isNull();
        assertThat(delta3.getCustomField("usage")).isNull();
        final Map<?, ?> delta4UsageRaw = (Map<?, ?>) delta4.getCustomField("usage");
        final var delta4Usage = MAPPER.convertValue(delta4UsageRaw, CompletionUsage.class);
        assertThat(delta4Usage).isNotNull();
        assertThat(delta4Usage.getCompletionTokens()).isEqualTo(607);
        assertThat(delta4Usage.getPromptTokens()).isEqualTo(21);
        assertThat(delta4Usage.getTotalTokens()).isEqualTo(628);

        // delta 0

        assertThat(delta0.getId()).isEqualTo("");
        assertThat(delta0.getCreated()).isEqualTo(0);
        assertThat(delta0.getModel()).isEqualTo("");
        assertThat(delta0.getObject()).isEqualTo(UNKNOWN_DEFAULT_OPEN_API);
        assertThat(delta0.getUsage()).isNull();
        assertThat(delta0.getChoices()).isEmpty();
        // prompt filter results are only present in delta 0
        assertThat(delta0.getPromptFilterResults()).isNotNull();
        assertThat(delta0.getPromptFilterResults().get(0).getPromptIndex()).isEqualTo(0);
        final var promptFilter0 = delta0.getPromptFilterResults().get(0).getContentFilterResults();
        assertThat(promptFilter0).isNotNull();
        assertFilter(promptFilter0);

        // delta 1
        assertThat(delta1.getChoices()).hasSize(1);

        // delta 2
        assertThat(delta2.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta2.getCreated()).isEqualTo(1724825677);
        assertThat(delta2.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta2.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);
        assertThat(delta2.getCustomField("usage")).isNull();
        assertThat(delta2.getCustomFieldNames()).doesNotContain("prompt_filter_results");
        assertThat(delta2.getChoices()).hasSize(1);
        final var choices2 = delta2.getChoices().get(0);
        assertThat(choices2.getIndex()).isEqualTo(0);
        assertThat(choices2.getFinishReason()).isNull();
        assertThat(choices2.getDelta()).isNotNull();
        // the role is only defined in delta 1
        assertThat(choices2.getDelta().getRole()).isNull();
        assertThat(choices2.getDelta().getContent()).isEqualTo("Sure");
        assertThat(choices2.getDelta().getToolCalls()).isNotNull().isEmpty();
        final Map<?, ?> filter2raw = (Map<?, ?>) choices2.getCustomField("content_filter_results");
        final var filter2 = MAPPER.convertValue(filter2raw, ContentFilterPromptResults.class);
        assertFilter(filter2);

        // delta 3

        assertThat(delta3.getChoices()).hasSize(1);
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");

        // delta 4

        assertThat(delta4.getChoices()).hasSize(1);
        final var delta4Choice = delta4.getChoices().get(0);
        assertThat(delta4Choice.getFinishReason())
            .isEqualTo(CreateChatCompletionStreamResponseChoicesInner.FinishReasonEnum.STOP);
        assertThat(delta4Choice.getDelta().getContent()).isNull();
        // the role is only defined in delta 1
        assertThat(delta4Choice.getDelta().getRole()).isNull();
        assertThat(delta4Choice.getDelta().getContent()).isNull();
        assertThat(delta4Choice.getDelta().getToolCalls()).isEmpty();
        assertThat(delta4.getChoices()).hasSize(1);
        final var choice = delta4.getChoices().get(0);
        assertThat(choice.getFinishReason())
            .isEqualTo(CreateChatCompletionStreamResponseChoicesInner.FinishReasonEnum.STOP);
        final Map<?, ?> filterRaw = (Map<?, ?>) choice.getCustomField("content_filter_results");
        assertThat(filterRaw).isEmpty();
        assertThat(choice.getDelta()).isNotNull();
        assertThat(choice.getDelta().getRole()).isNull();
        assertThat(choice.getDelta().getContent()).isNull();
        assertThat(choice.getDelta().getToolCalls()).isNotNull().isEmpty();
        assertThat(delta4.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta4.getCreated()).isEqualTo(1724825677);
        assertThat(delta4.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta4.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);
        assertThat(choice.getCustomField("content_filter_results")).asInstanceOf(MAP).isEmpty();
        assertThat(delta4.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta4.getCustomFieldNames()).doesNotContain("prompt_filter_results");
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  void assertFilter(ContentFilterPromptResults filter) {
    assertThat(filter).isNotNull();
    assertThat(filter.getHate()).isNotNull();
    assertThat(filter.getHate().isFiltered()).isFalse();
    assertThat(filter.getHate().getSeverity()).isEqualTo(SAFE);
    assertThat(filter.getSelfHarm()).isNotNull();
    assertThat(filter.getSelfHarm().isFiltered()).isFalse();
    assertThat(filter.getSelfHarm().getSeverity()).isEqualTo(SAFE);
    assertThat(filter.getSexual()).isNotNull();
    assertThat(filter.getSexual().isFiltered()).isFalse();
    assertThat(filter.getSexual().getSeverity()).isEqualTo(SAFE);
    assertThat(filter.getViolence()).isNotNull();
    assertThat(filter.getViolence().isFiltered()).isFalse();
    assertThat(filter.getViolence().getSeverity()).isEqualTo(SAFE);
    assertThat(filter.getJailbreak()).isNull();
    assertThat(filter.getProfanity()).isNull();
    assertThat(filter.getError()).isNull();
  }
}
