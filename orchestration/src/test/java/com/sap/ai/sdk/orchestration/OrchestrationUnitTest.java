package com.sap.ai.sdk.orchestration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.okXml;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE;
import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE_LOW_MEDIUM;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO_16K;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.*;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@WireMockTest
class OrchestrationUnitTest {
  static final OrchestrationAiModel CUSTOM_GPT_35 =
      GPT_35_TURBO_16K
          .withParam(MAX_TOKENS, 50)
          .withParam(TEMPERATURE, 0.1)
          .withParam(FREQUENCY_PENALTY, 0)
          .withParam(PRESENCE_PENALTY, 0);

  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private OrchestrationClient client;
  private OrchestrationModuleConfig config;
  private OrchestrationPrompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationClient(destination);
    config = new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_35);
    prompt = new OrchestrationPrompt("Hello World! Why is this phrase so famous?");
  }

  @Test
  void testCompletion() {
    stubFor(
        post(urlPathEqualTo("/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var result = client.chatCompletion(prompt, config);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).isNotEmpty();
  }

  @Test
  void testTemplating() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var template = new UserMessage("{{?input}}");
    final var inputParams =
        Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    final var result =
        client.chatCompletion(new OrchestrationPrompt(inputParams, template), config);

    final var response = result.getOriginalResponse();
    assertThat(response.getRequestId()).isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");
    assertThat(result.getAllMessages().get(0).content())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(result.getAllMessages().get(0).role()).isEqualTo("user");
    var llm = (LLMModuleResultSynchronous) response.getModuleResults().getLlm();
    assertThat(llm).isNotNull();
    assertThat(llm.getId()).isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(llm.getObject()).isEqualTo("chat.completion");
    assertThat(llm.getCreated()).isEqualTo(1721224505);
    assertThat(llm.getModel()).isEqualTo("gpt-35-turbo-16k");
    var choices = llm.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Orchestration Service funktioniert!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    var usage = result.getTokenUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);
    var orchestrationResult = (LLMModuleResultSynchronous) response.getOrchestrationResult();
    assertThat(orchestrationResult.getId()).isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(orchestrationResult.getObject()).isEqualTo("chat.completion");
    assertThat(orchestrationResult.getCreated()).isEqualTo(1721224505);
    assertThat(orchestrationResult.getModel()).isEqualTo("gpt-35-turbo-16k");
    choices = orchestrationResult.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Orchestration Service funktioniert!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = result.getTokenUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);

    // verify that null fields are absent from the sent request
    try (var requestInputStream = fileLoader.apply("templatingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testBadRequest() {
    stubFor(
        post(anyUrl())
            .willReturn(
                jsonResponse(
                    """
                    {
                      "request_id": "51043a32-01f5-429a-b0e7-3a99432e43a4",
                      "code": 400,
                      "message": "Missing required parameters: ['input']",
                      "location": "Module: Templating",
                      "module_results": {}
                    }
                    """,
                    SC_BAD_REQUEST)));

    assertThatThrownBy(() -> client.chatCompletion(prompt, config))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessage(
            "Request to orchestration service failed with status 400 Bad Request and error message: 'Missing required parameters: ['input']'");
  }

  @Test
  void filteringLoose() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("filteringLooseResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var filter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE_LOW_MEDIUM)
            .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
            .sexual(ALLOW_SAFE_LOW_MEDIUM)
            .violence(ALLOW_SAFE_LOW_MEDIUM);

    client.chatCompletion(prompt, config.withInputFiltering(filter).withOutputFiltering(filter));
    // the result is asserted in the verify step below

    // verify that null fields are absent from the sent request
    try (var requestInputStream = fileLoader.apply("filteringLooseRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void filteringStrict() {
    final String response =
        """
            {"request_id": "bf6d6792-7adf-4d3c-9368-a73615af8c5a", "code": 400, "message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "location": "Input Filter", "module_results": {"templating": [{"role": "user", "content": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}], "input_filtering": {"message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "data": {"original_service_response": {"Hate": 0, "SelfHarm": 0, "Sexual": 0, "Violence": 2}, "checked_text": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}}}}""";
    stubFor(post(anyUrl()).willReturn(jsonResponse(response, SC_BAD_REQUEST)));

    final var filter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE)
            .selfHarm(ALLOW_SAFE)
            .sexual(ALLOW_SAFE)
            .violence(ALLOW_SAFE);

    final var configWithFilter = config.withInputFiltering(filter).withOutputFiltering(filter);

    assertThatThrownBy(() -> client.chatCompletion(prompt, configWithFilter))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessage(
            "Request to orchestration service failed with status 400 Bad Request and error message: 'Content filtered due to Safety violations. Please modify the prompt and try again.'");
  }

  @Test
  void messagesHistory() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final List<Message> messagesHistory =
        List.of(
            new UserMessage("What is the capital of France?"),
            new AssistantMessage("The capital of France is Paris."));
    final var message = new UserMessage("What is the typical food there?");

    prompt = new OrchestrationPrompt(message).messageHistory(messagesHistory);

    final var result = client.chatCompletion(prompt, config);

    assertThat(result.getOriginalResponse().getRequestId())
        .isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");

    // verify that the history is sent correctly
    try (var requestInputStream = fileLoader.apply("messagesHistoryRequest.json")) {
      final String requestBody = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(requestBody)));
    }
  }

  @Test
  void maskingPseudonymization() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("maskingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var maskingConfig = DpiMasking.pseudonymization().withEntities(DPIEntities.PHONE);

    final var result = client.chatCompletion(prompt, config.withMaskingConfig(maskingConfig));
    final var response = result.getOriginalResponse();

    assertThat(response).isNotNull();
    GenericModuleResult inputMasking = response.getModuleResults().getInputMasking();
    assertThat(inputMasking).isNotNull();
    assertThat(inputMasking.getMessage()).isEqualTo("Input to LLM is masked successfully.");
    assertThat(inputMasking.getData()).isNotNull();
    assertThat(result.getContent()).contains("Hi Mallory");

    // verify that the request is sent correctly
    try (var requestInputStream = fileLoader.apply("maskingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testErrorHandling() {
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
                badRequest()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("errorResponse.json"))
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
    final Runnable request = () -> client.executeRequest(mock(CompletionPostRequest.class));

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Server errors should be handled")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("500");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Error objects from Orchestration should be interpreted")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("'orchestration_config' is a required property");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Failures while parsing error message should be handled")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400")
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(JsonParseException.class);

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Non-JSON responses should be handled")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("Failed to parse");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Empty responses should be handled")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("was empty");

    softly.assertAll();
  }

  @Test
  void testExecuteRequestFromJson() {
    stubFor(post(anyUrl()).willReturn(okJson("{}")));

    prompt =
        new OrchestrationPrompt(Map.of("foo", "bar"))
            .messageHistory(List.of(new UserMessage("Hello World!")));
    final var configJson =
        """
        {
          "module_configurations": {
            "llm_module_config": {
              "model_name": "mistralai--mistral-large-instruct",
              "model_params": {}
            }
          }
        }
        """;

    final var expectedJson =
        """
        {
          "messages_history": [{
            "role" : "user",
            "content" : "Hello World!"
          }],
          "input_params": {
            "foo" : "bar"
          },
          "orchestration_config": {
            "module_configurations": {
              "llm_module_config": {
                "model_name": "mistralai--mistral-large-instruct",
                "model_params": {}
              }
            }
          }
        }
        """;

    var result = client.executeRequestFromJsonModuleConfig(prompt, configJson);
    assertThat(result).isNotNull();

    verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(expectedJson)));
  }

  @Test
  void testExecuteRequestFromJsonThrows() {
    assertThatThrownBy(() -> client.executeRequestFromJsonModuleConfig(prompt, "{}"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("messages");

    prompt = new OrchestrationPrompt(Map.of());
    assertThatThrownBy(() -> client.executeRequestFromJsonModuleConfig(prompt, "{ foo"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("not valid JSON");
  }
}
