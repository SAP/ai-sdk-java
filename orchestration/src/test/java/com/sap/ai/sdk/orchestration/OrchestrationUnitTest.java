package com.sap.ai.sdk.orchestration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.badRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.NUMBER_0;
import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.NUMBER_4;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@WireMockTest
class OrchestrationUnitTest {
  static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create()
          .modelName("gpt-35-turbo-16k")
          .modelParams(
              Map.of(
                  "max_tokens", 50,
                  "temperature", 0.1,
                  "frequency_penalty", 0,
                  "presence_penalty", 0));
  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private OrchestrationClient client;
  private OrchestrationModuleConfig config;
  private OrchestrationPrompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("my-resource-group"))
            .withHeader("AI-Client-Type", equalTo("AI SDK Java"))
            .willReturn(
                okJson(
                    """
                        {
                          "resources": [
                            {
                              "id": "abcdef0123456789",
                              "scenarioId": "orchestration"
                            }
                          ]
                        }
                        """)));

    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();

    final var deployment =
        new AiCoreService()
            .withDestination(destination)
            .forDeploymentByScenario("orchestration")
            .withResourceGroup("my-resource-group");
    client = new OrchestrationClient(deployment);
    config = new OrchestrationModuleConfig().withLlmConfig(LLM_CONFIG);
    prompt = new OrchestrationPrompt("Hello World! Why is this phrase so famous?");
  }

  @Test
  void testCompletion() {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var result = client.chatCompletion(prompt, config);

    assertThat(result).isNotNull();
    assertThat(result.getOrchestrationResult().getChoices().get(0).getMessage().getContent())
        .isNotEmpty();
  }

  @Test
  void testTemplating() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var template = ChatMessage.create().role("user").content("{{?input}}");
    final var inputParams =
        Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    final var result =
        client.chatCompletion(new OrchestrationPrompt(inputParams, template), config);

    assertThat(result.getRequestId()).isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");
    assertThat(result.getModuleResults().getTemplating().get(0).getContent())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(result.getModuleResults().getTemplating().get(0).getRole()).isEqualTo("user");
    var llm = result.getModuleResults().getLlm();
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
    var usage = llm.getUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);
    assertThat(result.getOrchestrationResult().getId())
        .isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(result.getOrchestrationResult().getObject()).isEqualTo("chat.completion");
    assertThat(result.getOrchestrationResult().getCreated()).isEqualTo(1721224505);
    assertThat(result.getOrchestrationResult().getModel()).isEqualTo("gpt-35-turbo-16k");
    choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Orchestration Service funktioniert!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = result.getOrchestrationResult().getUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);

    // verify that null fields are absent from the sent request
    try (var requestInputStream = fileLoader.apply("templatingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testBadRequest() {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
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
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("filteringLooseResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var filter = createAzureContentFilter(NUMBER_4);

    client.chatCompletion(prompt, config.withFilteringConfig(filter));
    // the result is asserted in the verify step below

    // verify that null fields are absent from the sent request
    try (var requestInputStream = fileLoader.apply("filteringLooseRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void filteringStrict() {
    final String response =
        """
            {"request_id": "bf6d6792-7adf-4d3c-9368-a73615af8c5a", "code": 400, "message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "location": "Input Filter", "module_results": {"templating": [{"role": "user", "content": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}], "input_filtering": {"message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "data": {"original_service_response": {"Hate": 0, "SelfHarm": 0, "Sexual": 0, "Violence": 2}, "checked_text": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}}}}""";
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(jsonResponse(response, SC_BAD_REQUEST)));

    final var filter = createAzureContentFilter(NUMBER_0);

    final var configWithFilter = config.withFilteringConfig(filter);

    assertThatThrownBy(() -> client.chatCompletion(prompt, configWithFilter))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessage(
            "Request to orchestration service failed with status 400 Bad Request and error message: 'Content filtered due to Safety violations. Please modify the prompt and try again.'");
  }

  private static FilteringModuleConfig createAzureContentFilter(
      @Nonnull final AzureThreshold threshold) {
    final var filter =
        FilterConfig.create()
            .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
            .config(
                AzureContentSafety.create()
                    .hate(threshold)
                    .selfHarm(threshold)
                    .sexual(threshold)
                    .violence(threshold));

    return FilteringModuleConfig.create()
        .input(InputFilteringConfig.create().filters(filter))
        .output(OutputFilteringConfig.create().filters(filter));
  }

  @Test
  void messagesHistory() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final List<ChatMessage> messagesHistory =
        List.of(
            ChatMessage.create().role("user").content("What is the capital of France?"),
            ChatMessage.create().role("assistant").content("The capital of France is Paris."));
    final var message =
        ChatMessage.create().role("user").content("What is the typical food there?");

    prompt = new OrchestrationPrompt(message).messageHistory(messagesHistory);

    final var result = client.chatCompletion(prompt, config);

    assertThat(result.getRequestId()).isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");

    // verify that the history is sent correctly
    try (var requestInputStream = fileLoader.apply("messagesHistoryRequest.json")) {
      final String requestBody = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(requestBody)));
    }
  }

  @Test
  void maskingAnonymization() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("maskingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var maskingConfig =
        createMaskingConfig(MaskingProviderConfig.MethodEnum.ANONYMIZATION, DPIEntities.PHONE);

    final var result = client.chatCompletion(prompt, config.withMaskingConfig(maskingConfig));

    assertThat(result).isNotNull();
    GenericModuleResult inputMasking = result.getModuleResults().getInputMasking();
    assertThat(inputMasking.getMessage()).isEqualTo("Input to LLM is masked successfully.");
    assertThat(inputMasking.getData()).isNotNull();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo(
            "I'm sorry, I cannot provide information about specific individuals, including their nationality.");

    // verify that the request is sent correctly
    try (var requestInputStream = fileLoader.apply("maskingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }

  private static MaskingModuleConfig createMaskingConfig(
      @Nonnull final MaskingProviderConfig.MethodEnum method,
      @Nonnull final DPIEntities... entities) {

    final var entityConfigs =
        Arrays.stream(entities).map(it -> DPIEntityConfig.create().type(it)).toList();
    return MaskingModuleConfig.create()
        .maskingProviders(
            MaskingProviderConfig.create()
                .type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
                .method(method)
                .entities(entityConfigs));
  }

  @Test
  void testGenericErrorHandling() {
    stubFor(post(anyUrl()).willReturn(serverError()));

    assertThatThrownBy(() -> client.executeRequest(mock(CompletionPostRequest.class)))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("500 Server Error");
  }

  @Test
  void testOrchestrationErrorParsing() {
    stubFor(
        post(anyUrl())
            .willReturn(
                badRequest()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("errorResponse.json")));

    assertThatThrownBy(() -> client.executeRequest(mock(CompletionPostRequest.class)))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request")
        .hasMessageContaining("'orchestration_config' is a required property");
  }
}
