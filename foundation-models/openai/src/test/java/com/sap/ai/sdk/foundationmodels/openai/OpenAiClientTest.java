package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterSeverityResult.Severity.SAFE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.core.DeploymentCache;
import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionChoice;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionOutput;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import io.vavr.control.Try;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

@WireMockTest
class OpenAiClientTest {
  private static OpenAiClient client;
  private final Function<String, InputStream> TEST_FILE_LOADER =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = OpenAiClient.withCustomDestination(destination);
    DeploymentCache.lazyLoaded(new DeploymentApi(destination));
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
    Try.of(() -> client.chatCompletion(new OpenAiChatCompletionParameters()));

    verify(exactly(1), postRequestedFor(anyUrl()).withoutQueryParam("api-version"));

    Try.of(
        () -> client.withApiVersion("fooBar").chatCompletion(new OpenAiChatCompletionParameters()));
    verify(exactly(1), postRequestedFor(anyUrl()).withQueryParam("api-version", equalTo("fooBar")));

    assertThat(client)
        .describedAs(
            "withApiVersion should return a new object, the sut object should remain unchanged")
        .isNotSameAs(client.withApiVersion("fooBar"));
    Try.of(() -> client.chatCompletion(new OpenAiChatCompletionParameters()));
    verify(exactly(2), postRequestedFor(anyUrl()).withoutQueryParam("api-version"));
  }

  private static Runnable[] chatCompletionCalls() {
    return new Runnable[] {
      () -> client.chatCompletion(new OpenAiChatCompletionParameters()),
      () ->
          client
              .streamChatCompletionDeltas(new OpenAiChatCompletionParameters())
              // the stream needs to be consumed to parse the response
              .forEach(System.out::println)
    };
  }

  @ParameterizedTest
  @MethodSource("chatCompletionCalls")
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

  @Test
  void chatCompletion() throws IOException {
    try (var inputStream = TEST_FILE_LOADER.apply("chatCompletionResponse.json")) {

      final String response = new String(inputStream.readAllBytes());
      stubFor(post("/chat/completions").willReturn(okJson(response)));

      final var systemMessage =
          new OpenAiChatMessage.OpenAiChatSystemMessage()
              .setContent(
                  "You are a helpful, friendly and sometimes slightly snarky AI assistant!");
      final var userMessage =
          new OpenAiChatUserMessage().addText("Hello World! Why is this phrase so famous?");
      final var request =
          new OpenAiChatCompletionParameters().setMessages(List.of(systemMessage, userMessage));

      final var result = client.chatCompletion(request);

      assertThat(result).isNotNull();
      assertThat(result.getCreated()).isEqualTo(1719300073);
      assertThat(result.getId()).isEqualTo("chatcmpl-9dumHtDEyysGFnknk17n4Lt37tg7T");
      assertThat(result.getModel()).isEqualTo("gpt-4-32k");
      assertThat(result.getObject()).isEqualTo("chat.completion");
      assertThat(result.getSystemFingerprint()).isEqualTo("fp_e49e4201a9");

      assertThat(result.getUsage()).isNotNull();
      assertThat(result.getUsage().getCompletionTokens()).isEqualTo(54);
      assertThat(result.getUsage().getPromptTokens()).isEqualTo(13);
      assertThat(result.getUsage().getTotalTokens()).isEqualTo(67);

      assertThat(result.getPromptFilterResults()).hasSize(1);
      assertThat(result.getPromptFilterResults().get(0).getPromptIndex()).isEqualTo(0);
      OpenAiContentFilterPromptResults promptFilterResults =
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
      // TODO: update the JSON response and those assertions
      assertThat(promptFilterResults.getSelfHarm()).isNull();
      assertThat(promptFilterResults.getProfanity()).isNull();
      assertThat(promptFilterResults.getError()).isNull();
      assertThat(promptFilterResults.getJailbreak()).isNotNull();
      assertThat(promptFilterResults.getJailbreak().isFiltered()).isFalse();
      assertThat(promptFilterResults.getJailbreak().isDetected()).isFalse();

      assertThat(result.getChoices()).hasSize(1);
      OpenAiChatCompletionChoice choice = result.getChoices().get(0);
      assertThat(choice.getFinishReason()).isEqualTo("stop");
      assertThat(choice.getIndex()).isEqualTo(0);
      assertThat(choice.getMessage().getContent())
          .isEqualTo(
              """
                This is a highly subjective question as the concept of beauty differs from one person to another. It's based on personal preferences and cultural standards. There are attractive people in all walks of life and industries, making it impossible to universally determine who is the "prettiest".""");
      assertThat(choice.getMessage().getRole()).isEqualTo("assistant");

      OpenAiContentFilterPromptResults contentFilterResults = choice.getContentFilterResults();
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
      assertThat(contentFilterResults.getSelfHarm()).isNull();
      assertThat(contentFilterResults.getProfanity()).isNull();
      assertThat(contentFilterResults.getError()).isNull();
      assertThat(contentFilterResults.getJailbreak()).isNull();

      verify(
          postRequestedFor(urlPathEqualTo("/chat/completions"))
              .withRequestBody(
                  equalToJson(
                      """
                          {"messages":[{"role":"system","content":"You are a helpful, friendly and sometimes slightly snarky AI assistant!"},{"role":"user","content":[{"type":"text","text":"Hello World! Why is this phrase so famous?"}]}]}""")));
    }
  }

  @Test
  void embedding() throws IOException {
    try (var inputStream = TEST_FILE_LOADER.apply("embeddingResponse.json")) {

      final String response = new String(inputStream.readAllBytes());
      stubFor(post("/embeddings").willReturn(okJson(response)));

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
      assertThat(embeddingData.getIndex()).isEqualTo(0);
      assertThat(embeddingData.getEmbedding())
          .isNotNull()
          .isNotEmpty()
          .containsExactly(-0.0000000070958645d, 2.123e-300d, -0.0069813123d, -3.385849e-05d)
          // ensure double precision
          .hasToString("[-7.0958645E-9, 2.123E-300, -0.0069813123, -3.385849E-5]");

      verify(
          postRequestedFor(urlPathEqualTo("/embeddings"))
              .withRequestBody(
                  equalToJson("""
                      {"input":["Hello World"]}""")));
    }
  }

  @Test
  void streamChatCompletionDeltasErrorHandling() throws IOException {
    try (var inputStream = spy(TEST_FILE_LOADER.apply("streamChatCompletionError.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      final var request =
          new OpenAiChatCompletionParameters()
              .setMessages(
                  List.of(
                      new OpenAiChatUserMessage()
                          .addText(
                              "Can you give me the first 100 numbers of the Fibonacci sequence?")));

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {
        assertThatThrownBy(() -> stream.forEach(System.out::println))
            .isInstanceOf(OpenAiClientException.class)
            .hasMessage(
                "Failed to parse response from OpenAI model and error message: 'exceeded token rate limit'");
      }

      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void streamChatCompletionDeltas() throws IOException {
    try (var inputStream = spy(TEST_FILE_LOADER.apply("streamChatCompletion.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      final var request =
          new OpenAiChatCompletionParameters()
              .setMessages(
                  List.of(
                      new OpenAiChatUserMessage()
                          .addText(
                              "Can you give me the first 100 numbers of the Fibonacci sequence?")));

      try (Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request)) {

        OpenAiChatCompletionOutput totalOutput = new OpenAiChatCompletionOutput();
        final List<OpenAiChatCompletionDelta> deltaList =
            stream.peek(totalOutput::addDelta).toList();

        assertThat(deltaList).hasSize(5);
        // the first two and the last delta don't have any content
        assertThat(deltaList.get(0).getDeltaContent()).isEqualTo("");
        assertThat(deltaList.get(1).getDeltaContent()).isEqualTo("");
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(3).getDeltaContent()).isEqualTo("!");
        assertThat(deltaList.get(4).getDeltaContent()).isEqualTo("");

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
        assertThat(delta0.getId()).isEqualTo("");
        assertThat(delta0.getCreated()).isEqualTo(0);
        assertThat(delta0.getModel()).isEqualTo("");
        assertThat(delta0.getObject()).isEqualTo("");
        assertThat(delta0.getUsage()).isNull();
        assertThat(delta0.getChoices()).isEmpty();
        // prompt filter results are only present in delta 0
        assertThat(delta0.getPromptFilterResults()).isNotNull();
        assertThat(delta0.getPromptFilterResults().get(0).getPromptIndex()).isEqualTo(0);
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
        final var choices2 = delta2.getChoices().get(0);
        assertThat(choices2.getIndex()).isEqualTo(0);
        assertThat(choices2.getFinishReason()).isNull();
        assertThat(choices2.getMessage()).isNotNull();
        // the role is only defined in delta 1, but it defaults to "assistant" for all deltas
        assertThat(choices2.getMessage().getRole()).isEqualTo("assistant");
        assertThat(choices2.getMessage().getContent()).isEqualTo("Sure");
        assertThat(choices2.getMessage().getTool_calls()).isNull();
        final var filter2 = choices2.getContentFilterResults();
        assertFilter(filter2);

        final var delta3 = deltaList.get(3);
        assertThat(delta3.getDeltaContent()).isEqualTo("!");

        final var delta4Choice = deltaList.get(4).getChoices().get(0);
        assertThat(delta4Choice.getFinishReason()).isEqualTo("stop");
        assertThat(delta4Choice.getMessage()).isNotNull();
        // the role is only defined in delta 1, but it defaults to "assistant" for all deltas
        assertThat(delta4Choice.getMessage().getRole()).isEqualTo("assistant");
        assertThat(delta4Choice.getMessage().getContent()).isNull();
        assertThat(delta4Choice.getMessage().getTool_calls()).isNull();
        assertThat(totalOutput.getChoices()).hasSize(1);
        final var choice = totalOutput.getChoices().get(0);
        assertThat(choice.getFinishReason()).isEqualTo("stop");
        assertFilter(choice.getContentFilterResults());
        assertThat(choice.getFinishReason()).isEqualTo("stop");
        assertThat(choice.getMessage()).isNotNull();
        assertThat(choice.getMessage().getRole()).isEqualTo("assistant");
        assertThat(choice.getMessage().getContent()).isEqualTo("Sure!");
        assertThat(choice.getMessage().getTool_calls()).isNull();
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
}
