package com.sap.ai.sdk.foundationmodels.openai;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterSeverityResult.Severity.SAFE;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionChoice;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiContentFilterPromptResults;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiEmbeddingParameters;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiPromptFilterResult;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import io.vavr.control.Try;
import java.io.IOException;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest
class OpenAiClientTest {
  private OpenAiClient client;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = OpenAiClient.withCustomDestination(destination);
  }

  @Test
  void testApiVersion() {
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

  @Test
  void testErrorHandling() {
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

    final Runnable request = () -> client.chatCompletion(new OpenAiChatCompletionParameters());
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
  void testChatCompletion() throws IOException {
    final String response =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("chatCompletionResponse.json")
                .readAllBytes());
    stubFor(post(anyUrl()).willReturn(okJson(response)));

    final var systemMessage =
        new OpenAiChatMessage.OpenAiChatSystemMessage()
            .setContent("You are a helpful, friendly and sometimes slightly snarky AI assistant!");
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
    assertThat(result.getSystemFingerprint()).isNull();

    assertThat(result.getUsage().getCompletionTokens()).isEqualTo(54);
    assertThat(result.getUsage().getPromptTokens()).isEqualTo(13);
    assertThat(result.getUsage().getTotalTokens()).isEqualTo(67);

    assertThat(result.getPromptFilterResults()).hasSize(1);
    OpenAiPromptFilterResult promptFilterResults = result.getPromptFilterResults().get(0);
    assertThat(promptFilterResults.getPromptIndex()).isEqualTo(0);
    assertThat(promptFilterResults.getContentFilterResults().getSexual().isFiltered()).isFalse();
    assertThat(promptFilterResults.getContentFilterResults().getSexual().getSeverity())
        .isEqualTo(SAFE);
    assertThat(promptFilterResults.getContentFilterResults().getViolence().isFiltered()).isFalse();
    assertThat(promptFilterResults.getContentFilterResults().getViolence().getSeverity())
        .isEqualTo(SAFE);
    assertThat(promptFilterResults.getContentFilterResults().getHate().isFiltered()).isFalse();
    assertThat(promptFilterResults.getContentFilterResults().getHate().getSeverity())
        .isEqualTo(SAFE);
    assertThat(promptFilterResults.getContentFilterResults().getSelfHarm()).isNull();
    assertThat(promptFilterResults.getContentFilterResults().getProfanity()).isNull();
    assertThat(promptFilterResults.getContentFilterResults().getError()).isNull();
    assertThat(promptFilterResults.getContentFilterResults().getJailbreak().isFiltered()).isFalse();
    assertThat(promptFilterResults.getContentFilterResults().getJailbreak().isDetected()).isFalse();

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
    assertThat(contentFilterResults.getSexual().isFiltered()).isFalse();
    assertThat(contentFilterResults.getSexual().getSeverity()).isEqualTo(SAFE);
    assertThat(contentFilterResults.getViolence().isFiltered()).isFalse();
    assertThat(contentFilterResults.getViolence().getSeverity()).isEqualTo(SAFE);
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

  @Test
  void testEmbedding() throws IOException {
    final String response =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("embeddingResponse.json")
                .readAllBytes());
    stubFor(post(anyUrl()).willReturn(okJson(response)));

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
        .hasToString(
            "[-7.0958645E-9, 2.123E-300, -0.0069813123, -3.385849E-5]"); // ensure double precision

    verify(
        postRequestedFor(urlPathEqualTo("/embeddings"))
            .withRequestBody(
                equalToJson("""
                                {"input":["Hello World"]}""")));
  }
}
