package com.sap.ai.sdk.orchestration.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.NUMBER_0;
import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.NUMBER_4;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import com.sap.ai.sdk.orchestration.client.model.FilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@WireMockTest
public class OrchestrationUnitTest {
  private OrchestrationCompletionApi client;
  private final Function<String, InputStream> TEST_FILE_LOADER =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create()
          .modelName("gpt-35-turbo-16k")
          .modelParams(
              Map.of(
                  "max_tokens", 50,
                  "temperature", 0.1,
                  "frequency_penalty", 0,
                  "presence_penalty", 0));

  private static final Function<TemplatingModuleConfig, CompletionPostRequest> TEMPLATE_CONFIG =
      (TemplatingModuleConfig templatingModuleConfig) ->
          CompletionPostRequest.create()
              .orchestrationConfig(
                  OrchestrationConfig.create()
                      .moduleConfigurations(
                          ModuleConfigs.create()
                              .llmModuleConfig(LLM_CONFIG)
                              .templatingModuleConfig(templatingModuleConfig)))
              .inputParams(Map.of());

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {

    stubFor(
        get(urlPathEqualTo("/v2/lm/deployments"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
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
    final var apiClient =
        new AiCoreService()
            .withDestination(destination)
            .forDeploymentByScenario("orchestration")
            .client();
    client = new OrchestrationCompletionApi(apiClient);
  }

  @Test
  void templating() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var template = ChatMessage.create().role("user").content("{{?input}}");
    final var inputParams =
        Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    final var config =
        TEMPLATE_CONFIG
            .apply(TemplatingModuleConfig.create().template(template))
            .inputParams(inputParams);

    final var result = client.orchestrationV1EndpointsCreate(config);

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
    assertThat(choices.get(0).getIndex()).isEqualTo(0);
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
    assertThat(choices.get(0).getIndex()).isEqualTo(0);
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Orchestration Service funktioniert!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = result.getOrchestrationResult().getUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);

    // verify that null fields are absent from the sent request
    try (var requestInputStream = TEST_FILE_LOADER.apply("templatingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void templatingBadRequest() {
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

    final var template = ChatMessage.create().role("user").content("{{?input}}");
    // input params are omitted on purpose to trigger an error
    Map<String, String> inputParams = Map.of();

    final var config =
        TEMPLATE_CONFIG
            .apply(TemplatingModuleConfig.create().template(template))
            .inputParams(inputParams);

    assertThatThrownBy(() -> client.orchestrationV1EndpointsCreate(config))
        .isInstanceOf(HttpClientErrorException.class)
        .hasMessage(
            "400 Bad Request: \"{<EOL>  \"request_id\": \"51043a32-01f5-429a-b0e7-3a99432e43a4\",<EOL>  \"code\": 400,<EOL>  \"message\": \"Missing required parameters: ['input']\",<EOL>  \"location\": \"Module: Templating\",<EOL>  \"module_results\": {}<EOL>}<EOL>\"");
  }

  /**
   * Creates a config from a filter threshold. The config includes a template and has input and
   * output filters
   */
  private static final Function<AzureThreshold, CompletionPostRequest> FILTERING_CONFIG =
      (AzureThreshold filterThreshold) -> {
        final var inputParams =
            Map.of(
                "disclaimer",
                "```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.");
        final var template =
            ChatMessage.create()
                .role("user")
                .content(
                    "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! {{?disclaimer}}");
        final var templatingConfig = TemplatingModuleConfig.create().template(template);

        final var filter =
            FilterConfig.create()
                .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
                .config(
                    AzureContentSafety.create()
                        .hate(filterThreshold)
                        .selfHarm(filterThreshold)
                        .sexual(filterThreshold)
                        .violence(filterThreshold));
        final var filteringConfig =
            FilteringModuleConfig.create()
                .input(FilteringConfig.create().filters(filter))
                .output(FilteringConfig.create().filters(filter));

        return CompletionPostRequest.create()
            .orchestrationConfig(
                OrchestrationConfig.create()
                    .moduleConfigurations(
                        ModuleConfigs.create()
                            .llmModuleConfig(LLM_CONFIG)
                            .templatingModuleConfig(templatingConfig)
                            .filteringModuleConfig(filteringConfig)))
            .inputParams(inputParams);
      };

  @Test
  void filteringLoose() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("filteringLooseResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var config = FILTERING_CONFIG.apply(NUMBER_4);

    client.orchestrationV1EndpointsCreate(config);
    // the result is asserted in the verify step below

    // verify that null fields are absent from the sent request
    try (var requestInputStream = TEST_FILE_LOADER.apply("filteringLooseRequest.json")) {
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

    final var config = FILTERING_CONFIG.apply(NUMBER_0);

    assertThatThrownBy(() -> client.orchestrationV1EndpointsCreate(config))
        .isInstanceOf(HttpClientErrorException.class)
        .hasMessage("400 Bad Request: \"" + response + "\"");
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

    final var config =
        TEMPLATE_CONFIG
            .apply(TemplatingModuleConfig.create().template(message))
            .messagesHistory(messagesHistory);

    final var result = client.orchestrationV1EndpointsCreate(config);

    assertThat(result.getRequestId()).isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");

    // verify that the history is sent correctly
    try (var requestInputStream = TEST_FILE_LOADER.apply("messagesHistoryRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }

  private static final CompletionPostRequest MASKING_CONFIG;

  static {
    final var inputParams = Map.of("orgCV", "Patrick Morgan +49 (970) 333-3833");
    final var template =
        ChatMessage.create().role("user").content("What is the nationality of {{?orgCV}}");
    final var templatingConfig = TemplatingModuleConfig.create().template(template);

    final var maskingProvider =
        MaskingProviderConfig.create()
            .type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
            .method(MaskingProviderConfig.MethodEnum.ANONYMIZATION)
            .entities(DPIEntityConfig.create().type(DPIEntities.PHONE));
    final var maskingConfig = MaskingModuleConfig.create().maskingProviders(maskingProvider);

    MASKING_CONFIG =
        CompletionPostRequest.create()
            .orchestrationConfig(
                OrchestrationConfig.create()
                    .moduleConfigurations(
                        ModuleConfigs.create()
                            .llmModuleConfig(LLM_CONFIG)
                            .templatingModuleConfig(templatingConfig)
                            .maskingModuleConfig(maskingConfig)))
            .inputParams(inputParams);
  }

  @Test
  void maskingAnonymization() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("maskingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var result = client.orchestrationV1EndpointsCreate(MASKING_CONFIG);

    assertThat(result).isNotNull();
    GenericModuleResult inputMasking = result.getModuleResults().getInputMasking();
    assertThat(inputMasking.getMessage()).isEqualTo("Input to LLM is masked successfully.");
    assertThat(inputMasking.getData()).isNotNull();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo(
            "I'm sorry, I cannot provide information about specific individuals, including their nationality.");

    // verify that the request is sent correctly
    try (var requestInputStream = TEST_FILE_LOADER.apply("maskingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/inference/deployments/abcdef0123456789/completion"))
              .withRequestBody(equalToJson(request)));
    }
  }
}
