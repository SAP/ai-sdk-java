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
import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse.ObjectEnum.CHAT_COMPLETION_CHUNK;
import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponseChoicesInner.FinishReasonEnum.STOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionNamedToolChoice;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionNamedToolChoiceFunction;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.model2.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.FunctionObject;
import com.sap.ai.sdk.foundationmodels.openai.model2.PromptFilterResult;
import io.vavr.control.Try;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class NewOpenAiClientTest extends BaseOpenAiClientTest {

  @Test
  void openAiModels() {
    var model = OpenAiModel.GPT_4;
    var newModel = model.withVersion("v1");

    assertThat(model.name()).isEqualTo("gpt-4");
    assertThat(model.version()).isNull();

    assertThat(newModel.name()).isEqualTo("gpt-4");
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
    assertThat(result.getObject().getValue()).isEqualTo("chat.completion");
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
    assertThat(promptFilterResults.getSexual().getSeverity().getValue()).isEqualTo("safe");
    assertThat(promptFilterResults.getViolence()).isNotNull();
    assertThat(promptFilterResults.getViolence().isFiltered()).isFalse();
    assertThat(promptFilterResults.getViolence().getSeverity().getValue()).isEqualTo("safe");
    assertThat(promptFilterResults.getHate()).isNotNull();
    assertThat(promptFilterResults.getHate().isFiltered()).isFalse();
    assertThat(promptFilterResults.getHate().getSeverity().getValue()).isEqualTo("safe");
    assertThat(promptFilterResults.getSelfHarm()).isNotNull();
    assertThat(promptFilterResults.getSelfHarm().getSeverity().getValue()).isEqualTo("safe");
    assertThat(promptFilterResults.getSelfHarm().isFiltered()).isFalse();
    assertThat(promptFilterResults.getProfanity()).isNull();
    assertThat(promptFilterResults.getError()).isNull();
    assertThat(promptFilterResults.getJailbreak()).isNull();

    assertThat(result.getChoices()).hasSize(1);

    var choice = result.getChoices().get(0);
    assertThat(choice.getFinishReason().getValue()).isEqualTo("stop");
    assertThat(choice.getIndex()).isZero();
    assertThat(choice.getMessage().getContent())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");
    assertThat(choice.getMessage().getRole().getValue()).isEqualTo("assistant");
    assertThat(choice.getMessage().getToolCalls()).isNull();

    var contentFilterResults = choice.getContentFilterResults();
    assertThat(contentFilterResults).isNotNull();
    assertThat(contentFilterResults.getSexual()).isNotNull();
    assertThat(contentFilterResults.getSexual().isFiltered()).isFalse();
    assertThat(contentFilterResults.getSexual().getSeverity().getValue()).isEqualTo("safe");
    assertThat(contentFilterResults.getViolence()).isNotNull();
    assertThat(contentFilterResults.getViolence().isFiltered()).isFalse();
    assertThat(contentFilterResults.getViolence().getSeverity().getValue()).isEqualTo("safe");
    assertThat(contentFilterResults.getHate()).isNotNull();
    assertThat(contentFilterResults.getHate().isFiltered()).isFalse();
    assertThat(contentFilterResults.getHate().getSeverity().getValue()).isEqualTo("safe");
    assertThat(contentFilterResults.getSelfHarm()).isNotNull();
    assertThat(contentFilterResults.getSelfHarm().getSeverity().getValue()).isEqualTo("safe");
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
                           } ]
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
                           } ]
                      }""")));
  }

  @Test
  void embedding() {
    stubForEmbedding();

    final var result = client.embedding("Hello World");

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
    assertThat(embeddingData.getIndex()).isZero();
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
            .hasMessage("Failed to parse response and error message: 'exceeded token rate limit'");
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
        // the first two and the last delta don't have any content
        assertThat(deltaList.get(0).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(1).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");
        assertThat(deltaList.get(4).getDeltaContent()).isEmpty();

        assertThat(deltaList.get(3).getFinishReason()).isNull();
        assertThat(deltaList.get(4).getFinishReason()).isEqualTo(STOP.getValue());

        final var delta0 = deltaList.get(0).getOriginalResponse();
        final var delta1 = deltaList.get(1).getOriginalResponse();
        final var delta2 = deltaList.get(2).getOriginalResponse();
        final var delta3 = deltaList.get(3).getOriginalResponse();
        final var delta4 = deltaList.get(4).getOriginalResponse();

        assertThat(delta0.getSystemFingerprint()).isNull();
        assertThat(delta1.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta2.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta3.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(delta4.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

        assertThatThrownBy(() -> delta0.getCustomField("usage"))
            .isInstanceOf(NoSuchElementException.class);
        assertThat(delta1.getCustomField("usage")).isNull();
        assertThat(delta1.getCustomField("usage"))
            .isEqualTo(deltaList.get(1).getCompletionUsage(MAPPER));
        assertThat(delta2.getCustomField("usage")).isNull();
        assertThat(delta3.getCustomField("usage")).isNull();
        final Map<?, ?> delta4UsageRaw = (Map<?, ?>) delta4.getCustomField("usage");
        final var delta4Usage = MAPPER.convertValue(delta4UsageRaw, CompletionUsage.class);
        assertThat(delta4Usage).isNotNull();
        assertThat(delta4Usage.getCompletionTokens()).isEqualTo(607);
        assertThat(delta4Usage.getPromptTokens()).isEqualTo(21);
        assertThat(delta4Usage.getTotalTokens()).isEqualTo(628);
        assertThat(delta4Usage).isEqualTo(deltaList.get(4).getCompletionUsage(MAPPER));

        // delta 0

        assertThat(delta0.getId()).isEmpty();
        assertThat(delta0.getCreated()).isZero();
        assertThat(delta0.getModel()).isEmpty();
        assertThat(delta0.getObject())
            .isEqualTo(CreateChatCompletionStreamResponse.ObjectEnum.UNKNOWN_DEFAULT_OPEN_API);

        assertThat(delta0.getChoices()).isEmpty();

        var promptFilterResults = (List<?>) delta0.getCustomField("prompt_filter_results");
        var promptFilter =
            MAPPER.convertValue(promptFilterResults.get(0), PromptFilterResult.class);

        assertThat(promptFilter).isNotNull();
        assertThat(promptFilter.getPromptIndex()).isZero();
        final var contentFilterResults = promptFilter.getContentFilterResults();
        assertThat(contentFilterResults).isNotNull();
        assertFilter(contentFilterResults);

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
        assertThat(choices2.getIndex()).isZero();
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
        assertThat(delta4Choice.getFinishReason()).isEqualTo(STOP);
        assertThat(delta4Choice.getDelta().getContent()).isNull();
        // the role is only defined in delta 1
        assertThat(delta4Choice.getDelta().getRole()).isNull();
        assertThat(delta4Choice.getDelta().getContent()).isNull();
        assertThat(delta4Choice.getDelta().getToolCalls()).isEmpty();
        assertThat(delta4.getChoices()).hasSize(1);
        final var choice = delta4.getChoices().get(0);
        assertThat(choice.getFinishReason()).isEqualTo(STOP);
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
    assertThat(filter.getHate().getSeverity().getValue()).isEqualTo("safe");
    assertThat(filter.getSelfHarm()).isNotNull();
    assertThat(filter.getSelfHarm().isFiltered()).isFalse();
    assertThat(filter.getSelfHarm().getSeverity().getValue()).isEqualTo("safe");
    assertThat(filter.getSexual()).isNotNull();
    assertThat(filter.getSexual().isFiltered()).isFalse();
    assertThat(filter.getSexual().getSeverity().getValue()).isEqualTo("safe");
    assertThat(filter.getViolence()).isNotNull();
    assertThat(filter.getViolence().isFiltered()).isFalse();
    assertThat(filter.getViolence().getSeverity().getValue()).isEqualTo("safe");
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

    final var toolChoice =
        ChatCompletionToolChoiceOption.create(
            new ChatCompletionNamedToolChoice()
                .type(ChatCompletionNamedToolChoice.TypeEnum.FUNCTION)
                .function(new ChatCompletionNamedToolChoiceFunction().name("fibonacci")));

    final var request =
        new OpenAiChatCompletionRequest(
                "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?")
            .tools(List.of(tool))
            .toolChoice(toolChoice);

    var response = client.chatCompletion(request).getOriginalResponse();

    assertThat(response).isNotNull();
    assertThat(response.getChoices()).hasSize(1);
    assertThat(response.getChoices().get(0).getFinishReason().getValue()).isEqualTo("stop");
    assertThat(response.getChoices().get(0).getMessage().getRole().getValue())
        .isEqualTo("assistant");
    assertThat(response.getChoices().get(0).getMessage().getToolCalls()).hasSize(1);
    assertThat(response.getChoices().get(0).getMessage().getToolCalls().get(0).getId())
        .isEqualTo("call_CUYGJf2j7FRWJMHT3PN3aGxK");
    assertThat(response.getChoices().get(0).getMessage().getToolCalls().get(0).getType().getValue())
        .isEqualTo("function");
    assertThat(
            response.getChoices().get(0).getMessage().getToolCalls().get(0).getFunction().getName())
        .isEqualTo("fibonacci");
    assertThat(
            response
                .getChoices()
                .get(0)
                .getMessage()
                .getToolCalls()
                .get(0)
                .getFunction()
                .getArguments())
        .isEqualTo("{\"N\":12}");

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
}
