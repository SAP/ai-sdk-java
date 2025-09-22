package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamResponseDelta.RoleEnum.ASSISTANT;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ContentFilterSeverityResult.SeverityEnum.SAFE;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse.ObjectEnum.CHAT_COMPLETION;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.STOP;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionStreamResponse.ObjectEnum.CHAT_COMPLETION_CHUNK;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionStreamResponse.ObjectEnum.UNKNOWN_DEFAULT_OPEN_API;
import static com.sap.ai.sdk.foundationmodels.openai.generated.model.ToolCallType.FUNCTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionResponseMessageRole;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionStreamResponseChoicesInner;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.PromptFilterResult;
import io.vavr.control.Try;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class OpenAiClientGeneratedTest extends BaseOpenAiClientTest {

  @Test
  void openAiModels() {
    var model = OpenAiModel.GPT_5;
    var newModel = model.withVersion("v1");

    assertThat(model.name()).isEqualTo("gpt-5");
    assertThat(model.version()).isNull();

    assertThat(newModel.name()).isEqualTo("gpt-5");
    assertThat(newModel.version()).isEqualTo("v1");

    assertThat(model).isNotSameAs(newModel);
  }

  private static Runnable[] errorHandlingCalls() {
    return new Runnable[] {
      () -> client.chatCompletion(new OpenAiChatCompletionRequest("")),
      () ->
          client
              .streamChatCompletionDeltas(new OpenAiChatCompletionRequest(""))
              // the stream needs to be consumed to parse the response
              .forEach(System.out::println)
    };
  }

  @ParameterizedTest
  @MethodSource("errorHandlingCalls")
  void chatCompletionErrorHandling(@Nonnull final Runnable request) {
    stubForErrorHandling();
    assertForErrorHandling(request);
  }

  @Test
  void apiVersion() {
    stubFor(post(anyUrl()).willReturn(okJson("{}")));
    Try.of(() -> client.chatCompletion(new OpenAiChatCompletionRequest("")));

    verify(
        exactly(1),
        postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("2024-02-01")));

    Try.of(
        () -> client.withApiVersion("fooBar").chatCompletion(new OpenAiChatCompletionRequest("")));
    verify(exactly(1), postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("fooBar")));

    assertThat(client)
        .describedAs(
            "withApiVersion should return a new object, the sut object should remain unchanged")
        .isNotSameAs(client.withApiVersion("fooBar"));
    Try.of(() -> client.chatCompletion(new OpenAiChatCompletionRequest("")));
    verify(
        exactly(2),
        postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("2024-02-01")));
  }

  @Test
  void chatCompletion() {

    stubForChatCompletion();

    final var systemMessage = OpenAiMessage.system("You are a helpful AI");
    final var userMessage = OpenAiMessage.user("Hello World! Why is this phrase so famous?");
    final var prompt = new OpenAiChatCompletionRequest(systemMessage, userMessage);
    final var result = client.chatCompletion(prompt).getOriginalResponse();

    assertThat(result).isNotNull();
    assertThat(result.getCreated()).isEqualTo(1727436279);
    assertThat(result.getId()).isEqualTo("chatcmpl-AC3NPPYlxem8kRBBAX9EBObMMsrnf");
    assertThat(result.getModel()).isEqualTo("gpt-35-turbo");
    assertThat(result.getObject()).isEqualTo(CHAT_COMPLETION);
    assertThat(result.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

    assertThat(result.getUsage()).isNotNull();
    assertThat(result.getUsage().getCompletionTokens()).isEqualTo(20);
    assertThat(result.getUsage().getPromptTokens()).isEqualTo(13);
    assertThat(result.getUsage().getTotalTokens()).isEqualTo(33);

    assertThat(result.getPromptFilterResults()).hasSize(1);
    assertThat(result.getPromptFilterResults().get(0).getPromptIndex()).isZero();

    var promptFilterResults = result.getPromptFilterResults().get(0).getContentFilterResults();
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

    var choice = result.getChoices().get(0);
    assertThat(choice.getFinishReason()).isEqualTo(STOP);
    assertThat(choice.getIndex()).isZero();
    assertThat(choice.getMessage().getContent())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");
    assertThat(choice.getMessage().getRole())
        .isEqualTo(ChatCompletionResponseMessageRole.ASSISTANT);
    assertThat(choice.getMessage().getToolCalls()).isNull();

    var contentFilterResults = choice.getContentFilterResults();
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

    verify(
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withQueryParam("api-version", equalTo("2024-02-01"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "messages" : [ {
                        "content" : "You are a helpful AI",
                        "role" : "system"
                      }, {
                        "content" : "Hello World! Why is this phrase so famous?",
                        "role" : "user"
                      } ]
                    }""")));
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

    client.chatCompletion(new OpenAiChatCompletionRequest("First message"));

    verify(
        exactly(1),
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withRequestBody(
                equalToJson(
                    """
                      {
                           "messages" : [{
                             "content" : "First message",
                             "role" : "user"
                           }]
                      }""")));

    var response = client.chatCompletion(new OpenAiChatCompletionRequest("Second message"));

    assertThat(response.getContent()).isNotNull();
    assertThat(response.getContent())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");

    verify(
        exactly(1),
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withRequestBody(
                equalToJson(
                    """
                      {
                           "messages" : [{
                             "content" : "Second message",
                             "role" : "user"
                           }]
                      }""")));
  }

  @Test
  void embedding() {
    stubForEmbedding();

    final var response =
        client.embedding(new OpenAiEmbeddingRequest(List.of("Hello World"))).getOriginalResponse();

    assertThat(response).isNotNull();
    assertThat(response.getModel()).isEqualTo("ada");
    assertThat(response.getObject()).isEqualTo("list");

    assertThat(response.getUsage()).isNotNull();
    assertThat(response.getUsage().getPromptTokens()).isEqualTo(2);
    assertThat(response.getUsage().getTotalTokens()).isEqualTo(2);

    assertThat(response.getData()).isNotNull().hasSize(1);
    var embeddingData = response.getData().get(0);
    assertThat(embeddingData).isNotNull();
    assertThat(embeddingData.getObject()).isEqualTo("embedding");
    assertThat(embeddingData.getIndex()).isZero();
    assertThat(embeddingData.getEmbedding())
        .isNotNull()
        .isNotEmpty()
        .isEqualTo(new float[] {0.0f, 3.4028235E+38f, 1.4E-45f, 1.23f, -4.56f});

    verify(
        postRequestedFor(urlPathEqualTo("/embeddings"))
            .withRequestBody(
                equalToJson(
                    """
                      {"input": "Hello World"}""")));
  }

  @Test
  void testThrowsOnContentFilter() {
    var mock = mock(OpenAiClient.class);
    when(mock.streamChatCompletion(any())).thenCallRealMethod();

    var deltaWithContentFilter = mock(OpenAiChatCompletionDelta.class);
    when(deltaWithContentFilter.getFinishReason()).thenReturn("content_filter");
    when(mock.streamChatCompletionDeltas((CreateChatCompletionRequest) any()))
        .thenReturn(Stream.of(deltaWithContentFilter));

    // this must not throw, since the stream is lazily evaluated
    var stream = mock.streamChatCompletion("");
    assertThatThrownBy(stream::toList)
        .isInstanceOf(OpenAiClientException.class)
        .hasMessageContaining("Content filter");
  }

  @Test
  void streamChatCompletionDeltasErrorHandling() throws IOException {
    try (var inputStream = stubStreamChatCompletion("streamChatCompletionError.txt")) {

      final var request =
          new OpenAiChatCompletionRequest(
              "Can you give me the first 100 numbers of the Fibonacci sequence?");

      try (var stream = client.streamChatCompletionDeltas(request)) {
        assertThatThrownBy(() -> stream.forEach(System.out::println))
            .isInstanceOf(OpenAiClientException.class)
            .hasMessage("exceeded token rate limit");
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @SneakyThrows
  @Test
  void streamChatCompletionWithString() {
    try (var inputStream = stubStreamChatCompletion("streamChatCompletion.txt")) {
      final var userMessage = "Hello World! Why is this phrase so famous?";
      client.withSystemPrompt("You are a helpful AI");
      final var result = client.streamChatCompletion(userMessage).toList();

      assertThat(result).hasSize(5);
      // the first two and the last delta don't have any content
      assertThat(result.get(0)).isEmpty();
      assertThat(result.get(1)).isEmpty();
      assertThat(result.get(2)).isEqualTo("Sure");
      assertThat(result.get(3)).isEqualTo("!");
      assertThat(result.get(4)).isEmpty();

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void streamChatCompletionDeltas() throws IOException {

    try (var inputStream = stubStreamChatCompletion("streamChatCompletion.txt")) {

      final var request =
          new OpenAiChatCompletionRequest(
              "Can you give me the first 100 numbers of the Fibonacci sequence?");

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {
        final List<OpenAiChatCompletionDelta> deltaList = stream.toList();

        assertThat(deltaList).hasSize(5);

        final var delta0 = deltaList.get(0).getOriginalResponse();
        final var delta1 = deltaList.get(1).getOriginalResponse();
        final var delta2 = deltaList.get(2).getOriginalResponse();
        final var delta3 = deltaList.get(3).getOriginalResponse();
        final var delta4 = deltaList.get(4).getOriginalResponse();

        assertThat(delta0.getCreated()).isZero();
        assertThat(delta1.getCreated()).isEqualTo(1724825677);
        assertThat(delta2.getCreated()).isEqualTo(1724825677);
        assertThat(delta3.getCreated()).isEqualTo(1724825677);
        assertThat(delta4.getCreated()).isEqualTo(1724825677);

        assertThat(delta0.getId()).isEmpty();
        assertThat(delta1.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta2.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta3.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta4.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");

        assertThat(delta0.getModel()).isEmpty();
        assertThat(delta1.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta2.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta3.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta4.getModel()).isEqualTo("gpt-35-turbo");

        assertThat(delta0.getObject()).isEqualTo(UNKNOWN_DEFAULT_OPEN_API);
        assertThat(delta1.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);
        assertThat(delta2.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);
        assertThat(delta3.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);
        assertThat(delta4.getObject()).isEqualTo(CHAT_COMPLETION_CHUNK);

        assertThat(delta0.getSystemFingerprint()).isNull();
        assertThat(delta1.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta2.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta3.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta4.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

        assertThat(delta0.getCustomFieldNames()).contains("prompt_filter_results");
        assertThat(delta1.getCustomFieldNames()).doesNotContain("prompt_filter_results");
        assertThat(delta2.getCustomFieldNames()).doesNotContain("prompt_filter_results");
        assertThat(delta3.getCustomFieldNames()).doesNotContain("prompt_filter_results");
        assertThat(delta4.getCustomFieldNames()).doesNotContain("prompt_filter_results");
        var promptFilterResults = (List<?>) delta0.getCustomField("prompt_filter_results");
        final var promptFilter0 =
            MAPPER.convertValue(promptFilterResults.get(0), PromptFilterResult.class);
        assertThat(promptFilter0).isNotNull();
        assertThat(promptFilter0.getPromptIndex()).isZero();
        assertFilter(promptFilter0.getContentFilterResults());
        assertThat(promptFilter0.getContentFilterResults()).isNotNull();
        assertFilter(promptFilter0.getContentFilterResults());

        // delta0.choices
        assertThat(delta0.getChoices()).isEmpty();

        // delta1.choices
        assertThat(delta1.getChoices()).hasSize(1);
        var choice1 = delta1.getChoices().get(0);
        assertThat(choice1.getFinishReason()).isNull();
        assertThat(choice1.getIndex()).isZero();
        assertThat(choice1.getDelta().getContent()).isEmpty();
        assertThat(choice1.getDelta().getRole()).isEqualTo(ASSISTANT);
        assertThat(choice1.getCustomField("content_filter_results")).isNotNull();
        assertThat(choice1.getCustomField("content_filter_results")).isEqualTo(Map.of());

        // delta2.choices
        assertThat(delta2.getChoices()).hasSize(1);
        var choice2 = delta2.getChoices().get(0);
        assertThat(choice2.getFinishReason()).isNull();
        assertThat(choice2.getIndex()).isZero();
        assertThat(choice2.getDelta().getContent()).isEqualTo("Sure");
        assertThat(choice2.getDelta().getRole()).isNull();
        assertThat(choice2.getCustomField("content_filter_results")).isNotNull();
        final var contentFilter2 =
            MAPPER.convertValue(
                choice2.getCustomField("content_filter_results"), ContentFilterPromptResults.class);
        assertThat(contentFilter2).isNotNull();
        assertFilter(contentFilter2);

        // delta3.choices
        assertThat(delta3.getChoices()).hasSize(1);
        var choice3 = delta3.getChoices().get(0);
        assertThat(choice3.getFinishReason()).isNull();
        assertThat(choice3.getIndex()).isZero();
        assertThat(choice3.getDelta().getContent()).isEqualTo("!");
        assertThat(choice3.getDelta().getRole()).isNull();
        assertThat(choice3.getCustomField("content_filter_results")).isNotNull();
        var contentFilter3 =
            MAPPER.convertValue(
                choice3.getCustomField("content_filter_results"), ContentFilterPromptResults.class);
        assertThat(contentFilter3).isNotNull();
        assertFilter(contentFilter3);

        // delta4.choices
        assertThat(delta4.getChoices()).hasSize(1);
        var choice4 = delta4.getChoices().get(0);
        assertThat(choice4.getFinishReason())
            .isEqualTo(CreateChatCompletionStreamResponseChoicesInner.FinishReasonEnum.STOP);
        assertThat(choice4.getIndex()).isZero();
        assertThat(choice4.getDelta().getContent()).isNull();
        assertThat(choice4.getDelta().getRole()).isNull();
        assertThat(choice4.getCustomField("content_filter_results")).isEqualTo(Map.of());
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void streamCompletionDeltasResponseConvenience() throws IOException {
    try (var inputStream = stubStreamChatCompletion("streamChatCompletion.txt")) {

      final var request =
          new OpenAiChatCompletionRequest(
              "Can you give me the first 100 numbers of the Fibonacci sequence?");

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {
        final List<OpenAiChatCompletionDelta> deltaList = stream.toList();

        assertThat(deltaList).hasSize(5);

        assertThat(deltaList.get(0).getFinishReason()).isNull();
        assertThat(deltaList.get(1).getFinishReason()).isNull();
        assertThat(deltaList.get(2).getFinishReason()).isNull();
        assertThat(deltaList.get(3).getFinishReason()).isNull();
        assertThat(deltaList.get(3).getFinishReason()).isNull();
        assertThat(deltaList.get(4).getFinishReason()).isEqualTo("stop");

        assertThat(deltaList.get(0).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(1).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");
        assertThat(deltaList.get(4).getDeltaContent()).isEmpty();

        assertThat(deltaList.get(0).getCompletionUsage()).isNull();
        assertThat(deltaList.get(1).getCompletionUsage()).isNull();
        assertThat(deltaList.get(2).getCompletionUsage()).isNull();
        assertThat(deltaList.get(3).getCompletionUsage()).isNull();
        assertThat(deltaList.get(4).getCompletionUsage()).isNotNull();
        assertThat(deltaList.get(4).getCompletionUsage().getCompletionTokens()).isEqualTo(607);
        assertThat(deltaList.get(4).getCompletionUsage().getPromptTokens()).isEqualTo(21);
        assertThat(deltaList.get(4).getCompletionUsage().getTotalTokens()).isEqualTo(628);
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

  @Test
  void chatCompletionTool() {
    stubForChatCompletionTool();

    final var function =
        new FunctionObject()
            .name("fibonacci")
            .parameters(
                Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer"))));

    final var tool =
        new ChatCompletionTool().type(ChatCompletionTool.TypeEnum.FUNCTION).function(function);

    final var request =
        new OpenAiChatCompletionRequest(
                "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?")
            .withTools(List.of(tool))
            .withToolChoice(OpenAiToolChoice.function("fibonacci"));

    var response = client.chatCompletion(request).getOriginalResponse();

    assertThat(response).isNotNull();
    assertThat(response.getChoices()).hasSize(1);
    var choice = response.getChoices().get(0);
    assertThat(choice.getFinishReason()).isEqualTo(STOP);
    var message = choice.getMessage();
    assertThat(message.getRole()).isEqualTo(ChatCompletionResponseMessageRole.ASSISTANT);
    assertThat(message.getToolCalls()).hasSize(1);
    var toolCall = message.getToolCalls().get(0);
    assertThat(toolCall.getId()).isEqualTo("call_CUYGJf2j7FRWJMHT3PN3aGxK");
    assertThat(toolCall.getType()).isEqualTo(FUNCTION);
    assertThat(toolCall.getFunction().getName()).isEqualTo("fibonacci");
    assertThat(toolCall.getFunction().getArguments()).isEqualTo("{\"N\":12}");

    verify(
        postRequestedFor(anyUrl())
            .withRequestBody(
                equalToJson(
                    """
                  {
                    "messages" : [ {
                      "content" : "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?",
                      "role" : "user"
                    } ],
                    "tools" : [ {
                      "type" : "function",
                      "function" : {
                        "name" : "fibonacci",
                        "parameters" : {
                          "type" : "object",
                          "properties" : {
                            "N" : {
                              "type" : "integer"
                            }
                          }
                        },
                        "strict" : false
                      }
                    } ],
                    "tool_choice" : {
                      "type" : "function",
                      "function" : {
                        "name" : "fibonacci"
                      }
                    }
                  }
                  """)));
  }

  @Test
  void chatCompletionGetMessage() {
    stubForChatCompletion();
    final var textRequest = new OpenAiChatCompletionRequest("Some text");

    final var textResponse = client.chatCompletion(textRequest);
    final var message = textResponse.getMessage();

    assertThat(message).isNotNull();
    assertThat(message.toolCalls()).isEmpty();
    assertThat(message.content().items()).hasSize(1);

    final var textItem = message.content().items().get(0);
    assertThat(textItem).isInstanceOf(OpenAiTextItem.class);
    assertThat(((OpenAiTextItem) textItem).text())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");
  }

  @Test
  void chatCompletionToolCallGetMessage() {
    stubForChatCompletionTool();
    final var toolRequest = new OpenAiChatCompletionRequest("Some tool request");
    final var toolResponse = client.chatCompletion(toolRequest);
    final var message = toolResponse.getMessage();

    assertThat(message).isNotNull();
    assertThat(message.content().items()).isEmpty();
    final var toolCall = (OpenAiFunctionCall) message.toolCalls().get(0);
    assertThat(toolCall.getId()).isEqualTo("call_CUYGJf2j7FRWJMHT3PN3aGxK");
    assertThat(toolCall.getName()).isEqualTo("fibonacci");
    assertThat(toolCall.getArguments()).isEqualTo("{\"N\":12}");
  }
}
