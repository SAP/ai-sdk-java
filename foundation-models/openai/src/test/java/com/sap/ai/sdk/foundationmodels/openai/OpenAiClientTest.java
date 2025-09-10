package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.*;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterSeverityResult.Severity.SAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;

import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionFunction;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class OpenAiClientTest extends BaseOpenAiClientTest {

  private static Runnable[] errorHandlingCalls() {
    return new Runnable[] {
      () -> client.chatCompletion(""),
      () ->
          client
              .streamChatCompletionDeltas(
                  new OpenAiChatCompletionParameters()
                      .addMessages(new OpenAiChatUserMessage().addText("")))
              // the stream needs to be consumed to parse the response
              .forEach(System.out::println),
    };
  }

  @ParameterizedTest
  @MethodSource("errorHandlingCalls")
  void chatCompletionErrorHandling(@Nonnull final Runnable request) {

    stubForErrorHandling();
    assertForErrorHandling(request);
  }

  private static Callable<?>[] chatCompletionCalls() {
    return new Callable[] {
      () -> {
        final var systemMessage = new OpenAiChatSystemMessage().setContent("You are a helpful AI");
        final var userMessage =
            new OpenAiChatUserMessage().addText("Hello World! Why is this phrase so famous?");
        final var request =
            new OpenAiChatCompletionParameters().addMessages(systemMessage, userMessage);
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
  void chatCompletion(@Nonnull final Callable<OpenAiChatCompletionOutput> request) {

    stubForChatCompletion();

    var result = request.call();

    assertThat(result).isNotNull();
    assertThat(result.getCreated()).isEqualTo(1727436279);
    assertThat(result.getId()).isEqualTo("chatcmpl-AC3NPPYlxem8kRBBAX9EBObMMsrnf");
    assertThat(result.getModel()).isEqualTo("gpt-35-turbo");
    assertThat(result.getObject()).isEqualTo("chat.completion");
    assertThat(result.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

    assertThat(result.getUsage()).isNotNull();
    assertThat(result.getUsage().getCompletionTokens()).isEqualTo(20);
    assertThat(result.getUsage().getPromptTokens()).isEqualTo(13);
    assertThat(result.getUsage().getTotalTokens()).isEqualTo(33);

    assertThat(result.getPromptFilterResults()).hasSize(1);
    assertThat(result.getPromptFilterResults().get(0).getPromptIndex()).isEqualTo(0);
    assertThat(result.getContent())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");

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
    assertThat(choice.getFinishReason()).isEqualTo("stop");
    assertThat(choice.getIndex()).isZero();
    assertThat(choice.getMessage().getContent())
        .isEqualTo(
            "I'm an AI and cannot answer that question as beauty is subjective and varies from person to person.");
    assertThat(choice.getMessage().getRole()).isEqualTo("assistant");
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
    assertThat(contentFilterResults.getJailbreak()).isNull();

    verify(
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withQueryParam("api-version", equalTo("2024-02-01"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                       "messages" : [ {
                         "role" : "system",
                         "content" : "You are a helpful AI"
                       }, {
                         "role" : "user",
                         "content" : [ {
                           "type" : "text",
                           "text" : "Hello World! Why is this phrase so famous?"
                         } ]
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

    client.chatCompletion("First message");

    verify(
        exactly(1),
        postRequestedFor(urlPathEqualTo("/chat/completions"))
            .withRequestBody(
                equalToJson(
                    """
                      {
                        "messages" : [ {
                          "role" : "user",
                          "content" : [ {
                            "type" : "text",
                            "text" : "First message"
                          } ]
                        } ]
                      }""")));

    var response = client.chatCompletion("Second message");

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
                         "messages" : [ {
                           "role" : "user",
                           "content" : [ {
                             "type" : "text",
                             "text" : "Second message"
                           } ]
                         } ]
                       }""")));
  }

  @Test
  void embedding() {
    stubForEmbedding();

    final var request = new OpenAiEmbeddingParameters().setInput("Hello World");
    final var result = client.embedding(request);

    assertThat(result).isNotNull();
    assertThat(result.getModel()).isEqualTo("ada");
    assertThat(result.getObject()).isEqualTo("list");

    assertThat(result.getUsage()).isNotNull();
    assertThat(result.getUsage().getCompletionTokens()).isNull();
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
        .isEqualTo(new float[] {0.0f, 3.4028235E38f, 1.4E-45f, 1.23f, -4.56f});

    verify(
        postRequestedFor(urlPathEqualTo("/embeddings"))
            .withRequestBody(
                equalToJson(
                    """
                      {"input": ["Hello World"]}""")));
  }

  @Test
  void streamChatCompletionDeltasErrorHandling() throws IOException {
    try (var inputStream = stubStreamChatCompletion("streamChatCompletionError.txt")) {

      final var request =
          new OpenAiChatCompletionParameters()
              .addMessages(
                  new OpenAiChatUserMessage()
                      .addText("Can you give me the first 100 numbers of the Fibonacci sequence?"));

      try (var stream = client.streamChatCompletionDeltas(request)) {
        assertThatThrownBy(() -> stream.forEach(System.out::println))
            .isInstanceOf(OpenAiClientException.class)
            .hasMessage("exceeded token rate limit");
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void streamChatCompletionDeltas() throws IOException {
    try (var inputStream = stubStreamChatCompletion("streamChatCompletion.txt")) {

      final var request =
          new OpenAiChatCompletionParameters()
              .addMessages(
                  new OpenAiChatUserMessage()
                      .addText("Can you give me the first 100 numbers of the Fibonacci sequence?"));

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {

        OpenAiChatCompletionOutput totalOutput = new OpenAiChatCompletionOutput();
        final List<OpenAiChatCompletionDelta> deltaList =
            stream.peek(totalOutput::addDelta).toList();

        assertThat(deltaList).hasSize(5);
        // the first two and the last delta don't have any content
        assertThat(deltaList.get(0).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(1).getDeltaContent()).isEmpty();
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");
        assertThat(deltaList.get(4).getDeltaContent()).isEmpty();

        assertThat(deltaList.get(0).getSystemFingerprint()).isNull();
        assertThat(deltaList.get(1).getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(deltaList.get(2).getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(deltaList.get(3).getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(deltaList.get(4).getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

        assertThat(deltaList.get(0).getUsage()).isNull();
        assertThat(deltaList.get(1).getUsage()).isNull();
        assertThat(deltaList.get(2).getUsage()).isNull();
        assertThat(deltaList.get(3).getUsage()).isNull();
        final var usage = deltaList.get(4).getUsage();
        assertThat(usage).isNotNull();
        assertThat(usage.getCompletionTokens()).isEqualTo(607);
        assertThat(usage.getPromptTokens()).isEqualTo(21);
        assertThat(usage.getTotalTokens()).isEqualTo(628);

        assertThat(deltaList.get(0).getChoices()).isEmpty();
        assertThat(deltaList.get(1).getChoices()).hasSize(1);
        assertThat(deltaList.get(2).getChoices()).hasSize(1);
        assertThat(deltaList.get(3).getChoices()).hasSize(1);
        assertThat(deltaList.get(4).getChoices()).hasSize(1);

        final var delta0 = deltaList.get(0);
        assertThat(delta0.getId()).isEmpty();
        assertThat(delta0.getCreated()).isZero();
        assertThat(delta0.getModel()).isEmpty();
        assertThat(delta0.getObject()).isEmpty();
        assertThat(delta0.getUsage()).isNull();
        assertThat(delta0.getChoices()).isEmpty();
        assertThat(delta0.getFinishReason()).isNull();
        // prompt filter results are only present in delta 0
        assertThat(delta0.getPromptFilterResults()).isNotNull();
        assertThat(delta0.getPromptFilterResults().get(0).getPromptIndex()).isZero();
        final var promptFilter0 = delta0.getPromptFilterResults().get(0).getContentFilterResults();
        assertThat(promptFilter0).isNotNull();
        assertFilter(promptFilter0);

        final var delta2 = deltaList.get(2);
        assertThat(delta2.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(delta2.getCreated()).isEqualTo(1724825677);
        assertThat(delta2.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(delta2.getObject()).isEqualTo("chat.completion.chunk");
        assertThat(delta2.getUsage()).isNull();
        assertThat(delta2.getPromptFilterResults()).isNull();
        assertThat(delta2.getFinishReason()).isNull();

        final var choices2 = delta2.getChoices().get(0);
        assertThat(choices2.getIndex()).isEqualTo(0);
        assertThat(choices2.getMessage()).isNotNull();
        // the role is only defined in delta 1, but it defaults to "assistant" for all deltas
        assertThat(choices2.getMessage().getRole()).isEqualTo("assistant");
        assertThat(choices2.getMessage().getContent()).isEqualTo("Sure");
        assertThat(choices2.getMessage().getToolCalls()).isNull();
        final var filter2 = choices2.getContentFilterResults();
        assertFilter(filter2);

        final var delta3 = deltaList.get(3);
        assertThat(delta3.getDeltaContent()).isEqualTo("!");

        assertThat(deltaList.get(4).getFinishReason()).isEqualTo("stop");
        final var delta4Choice = deltaList.get(4).getChoices().get(0);
        assertThat(delta4Choice.getMessage()).isNotNull();
        // the role is only defined in delta 1, but it defaults to "assistant" for all deltas
        assertThat(delta4Choice.getMessage().getRole()).isEqualTo("assistant");
        assertThat(delta4Choice.getMessage().getContent()).isNull();
        assertThat(delta4Choice.getMessage().getToolCalls()).isNull();
        assertThat(totalOutput.getChoices()).hasSize(1);
        final var choice = totalOutput.getChoices().get(0);
        assertThat(choice.getFinishReason()).isEqualTo("stop");
        assertFilter(choice.getContentFilterResults());
        assertThat(choice.getFinishReason()).isEqualTo("stop");
        assertThat(choice.getMessage()).isNotNull();
        assertThat(choice.getMessage().getRole()).isEqualTo("assistant");
        assertThat(choice.getMessage().getContent()).isEqualTo("Sure!");
        assertThat(choice.getMessage().getToolCalls()).isNull();
        assertThat(totalOutput.getId()).isEqualTo("chatcmpl-A16EvnkgEm6AdxY0NoOmGPjsJucQ1");
        assertThat(totalOutput.getCreated()).isEqualTo(1724825677);
        assertThat(totalOutput.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(totalOutput.getObject()).isEqualTo("chat.completion.chunk");
        final var totalUsage = totalOutput.getUsage();
        assertThat(totalUsage).isNotNull();
        assertThat(totalUsage.getCompletionTokens()).isEqualTo(607);
        assertThat(totalUsage.getPromptTokens()).isEqualTo(21);
        assertThat(totalUsage.getTotalTokens()).isEqualTo(628);
        assertThat(totalOutput.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");
        assertThat(totalOutput.getPromptFilterResults()).isNotNull();
        assertFilter(totalOutput.getPromptFilterResults().get(0).getContentFilterResults());
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  void assertFilter(OpenAiContentFilterPromptResults filter) {
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

    final var question =
        "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?";
    final var par = Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer")));
    final var function = new OpenAiChatCompletionFunction().setName("fibonacci").setParameters(par);
    final var tool = new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(function);
    final var request =
        new OpenAiChatCompletionParameters()
            .addMessages(new OpenAiChatUserMessage().addText(question))
            .setTools(List.of(tool))
            .setToolChoiceFunction("fibonacci");

    var response = client.chatCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getChoices()).hasSize(1);
    assertThat(response.getChoices().get(0).getFinishReason()).isEqualTo("stop");
    assertThat(response.getChoices().get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(response.getChoices().get(0).getMessage().getToolCalls()).hasSize(1);
    assertThat(response.getChoices().get(0).getMessage().getToolCalls().get(0).getId())
        .isEqualTo("call_CUYGJf2j7FRWJMHT3PN3aGxK");
    assertThat(response.getChoices().get(0).getMessage().getToolCalls().get(0).getType())
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
                          "role" : "user",
                          "content" : [ {
                            "type" : "text",
                            "text" : "A pair of rabbits is placed in a field. Each month, every pair produces one new pair, starting from the second month. How many rabbits will there be after 12 months?"
                          } ]
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
                            }
                          }
                        } ],
                        "tool_choice" : {
                          "function" : {
                            "name" : "fibonacci"
                          },
                          "type" : "function"
                        }
                      }
                      """)));
  }

  @Test
  void testCustomHeaders() {
    stubForChatCompletion();
    final var request =
        new OpenAiChatCompletionRequest("Hello World! Why is this phrase so famous?");
    final var clientWithHeader = client.withHeader("Header-For-Both", "value");

    final var result = clientWithHeader.withHeader("foo", "bar").chatCompletion(request);
    assertThat(result).isNotNull();

    var streamResult =
        clientWithHeader
            .withHeader("foot", "baz")
            .streamChatCompletion("Hello World! Why is this phrase so famous?");
    assertThat(streamResult).isNotNull();

    verify(
        postRequestedFor(anyUrl())
            .withHeader("Header-For-Both", equalTo("value"))
            .withHeader("foo", equalTo("bar")));
    verify(
        postRequestedFor(anyUrl())
            .withHeader("Header-For-Both", equalTo("value"))
            .withHeader("foot", equalTo("baz")));
  }
}
