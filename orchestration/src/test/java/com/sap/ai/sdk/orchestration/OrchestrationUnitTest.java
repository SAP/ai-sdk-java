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
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE;
import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE_LOW_MEDIUM;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.*;
import static com.sap.ai.sdk.orchestration.model.AzureThreshold.NUMBER_0;
import static com.sap.ai.sdk.orchestration.model.AzureThreshold.NUMBER_4;
import static com.sap.ai.sdk.orchestration.model.AzureThreshold.NUMBER_6;
import static com.sap.ai.sdk.orchestration.model.ResponseChatMessage.RoleEnum.ASSISTANT;
import static com.sap.ai.sdk.orchestration.model.UserChatMessage.RoleEnum.USER;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.orchestration.model.ChatDelta;
import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DPIStandardEntity;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.Embedding;
import com.sap.ai.sdk.orchestration.model.EmbeddingsInput;
import com.sap.ai.sdk.orchestration.model.EmbeddingsInputText;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelConfig;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelDetails;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModuleConfigs;
import com.sap.ai.sdk.orchestration.model.EmbeddingsOrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostRequest;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import com.sap.ai.sdk.orchestration.model.EmbeddingsResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.GroundingFilterSearchConfiguration;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfigPlaceholders;
import com.sap.ai.sdk.orchestration.model.KeyValueListPair;
import com.sap.ai.sdk.orchestration.model.LlamaGuard38b;
import com.sap.ai.sdk.orchestration.model.MaskingModuleConfigProviders;
import com.sap.ai.sdk.orchestration.model.ModuleResultsStreaming;
import com.sap.ai.sdk.orchestration.model.ResponseFormatText;
import com.sap.ai.sdk.orchestration.model.SearchDocumentKeyValueListPair;
import com.sap.ai.sdk.orchestration.model.SearchSelectOptionEnum;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.val;
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

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@WireMockTest
class OrchestrationUnitTest {
  static final OrchestrationAiModel CUSTOM_GPT_4O =
      GPT_4O
          .withParam(MAX_TOKENS, 50)
          .withParam(TEMPERATURE, 0.1)
          .withParam(FREQUENCY_PENALTY, 0)
          .withParam(PRESENCE_PENALTY, 0)
          .withParam(TOP_P, 1)
          .withParam(N, 1);

  private final Function<String, InputStream> fileLoader =
      filename -> Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename));

  private static OrchestrationClient client;
  private static OrchestrationModuleConfig config;
  private static OrchestrationPrompt prompt;

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationClient(destination);
    config = new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_4O);
    prompt = new OrchestrationPrompt("Hello World! Why is this phrase so famous?");
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
        post(urlPathEqualTo("/v2/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var result = client.chatCompletion(prompt, config);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).isNotEmpty();
  }

  @Test
  void testCompletionError() {
    stubFor(
        post(urlPathEqualTo("/v2/completion"))
            .willReturn(
                aResponse()
                    .withStatus(500)
                    .withBodyFile("error500Response.json")
                    .withHeader("Content-Type", "application/json")));

    assertThatThrownBy(() -> client.chatCompletion(prompt, config))
        .hasMessage(
            "Request failed with status 500 (Server Error): Internal Server Error located in Masking Module - Masking");
  }

  @Test
  void testGrounding() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("groundingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var documentMetadata =
        SearchDocumentKeyValueListPair.create()
            .key("document metadata")
            .value("2")
            .selectMode(List.of(SearchSelectOptionEnum.IGNORE_IF_KEY_ABSENT));
    final var databaseFilter =
        DocumentGroundingFilter.create()
            .dataRepositoryType(DataRepositoryType.VECTOR)
            .searchConfig(GroundingFilterSearchConfiguration.create().maxChunkCount(3))
            .documentMetadata(List.of(documentMetadata))
            .chunkMetadata(List.of(KeyValueListPair.create().key("chunk metadata").value("1")));
    final var groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .placeholders(
                GroundingModuleConfigConfigPlaceholders.create()
                    .input(List.of("query"))
                    .output("results"))
            .addFiltersItem(databaseFilter);
    final var groundingConfig =
        GroundingModuleConfig.create()
            .type(GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE)
            .config(groundingConfigConfig);
    val maskingConfig = // optional masking configuration
        DpiMasking.anonymization()
            .withEntities(DPIEntities.SENSITIVE_DATA)
            .withMaskGroundingInput(true)
            .withAllowList(List.of("SAP", "Joule"));
    final var configWithGrounding =
        config.withGroundingConfig(groundingConfig).withMaskingConfig(maskingConfig);

    final Map<String, String> inputParams =
        Map.of("query", "String used for similarity search in database");
    final var prompt =
        new OrchestrationPrompt(
            inputParams,
            Message.system("Context message with embedded grounding results. {{?results}}"));

    final var response = client.chatCompletion(prompt, configWithGrounding);

    assertThat(response).isNotNull();
    assertThat(response.getOriginalResponse().getRequestId())
        .isEqualTo("e5d2add4-408c-4da5-84ca-1d8b0fe350c8");

    var moduleResults = response.getOriginalResponse().getIntermediateResults();
    assertThat(moduleResults).isNotNull();

    var groundingModule = moduleResults.getGrounding();
    assertThat(groundingModule).isNotNull();
    assertThat(groundingModule.getMessage()).isEqualTo("grounding result");
    assertThat(groundingModule.getData()).isNotNull();
    var groundingData =
        Map.of(
            "grounding_query",
            "grounding call",
            "grounding_result",
            "First chunk```Second chunk```Last found chunk");
    assertThat(groundingModule.getData()).isEqualTo(groundingData);

    var inputMasking = response.getOriginalResponse().getIntermediateResults().getInputMasking();
    assertThat(inputMasking.getMessage())
        .isEqualTo("Input to LLM and Grounding is masked successfully.");
    Object data = inputMasking.getData();
    assertThat(data)
        .isEqualTo(
            Map.of(
                "masked_template",
                "[{\"role\": \"user\", \"content\": \"Context message with embedded grounding results. First chunk```MASKED_SENSITIVE_DATA```Last found chunk\"}]",
                "masked_grounding_input", // maskGroundingInput: true will make this field present
                "[\"What does Joule do?\"]"));

    try (var requestInputStream = fileLoader.apply("groundingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/completion")).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testGroundingWithHelpSapCom() throws IOException {
    stubFor(
        post(urlPathEqualTo("/v2/completion"))
            .willReturn(
                aResponse()
                    .withBodyFile("groundingHelpSapComResponse.json")
                    .withHeader("Content-Type", "application/json")));
    val groundingHelpSapCom =
        DocumentGroundingFilter.create().dataRepositoryType(DataRepositoryType.HELP_SAP_COM);
    val groundingConfig = Grounding.create().filters(groundingHelpSapCom);
    val configWithGrounding = config.withGrounding(groundingConfig);

    val prompt = groundingConfig.createGroundingPrompt("What is a fuzzy search?");
    val response = client.chatCompletion(prompt, configWithGrounding);

    assertThat(
            response
                .getOriginalResponse()
                .getIntermediateResults()
                .getGrounding()
                .getData()
                .toString())
        .contains(
            "A fuzzy search is a search technique that is designed to be fast and tolerant of errors");
    assertThat(response.getContent()).startsWith("A fuzzy search is a search technique");

    try (var requestInputStream = fileLoader.apply("groundingHelpSapComRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/completion"))
              .withRequestBody(equalToJson(request, true, true)));
    }
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
    final var messageList = result.getAllMessages();

    assertThat(((TextItem) messageList.get(0).content().items().get(0)).text())
        .isEqualTo("You are a multi language translator");
    assertThat(messageList.get(0).role()).isEqualTo("system");
    assertThat(((TextItem) messageList.get(1).content().items().get(0)).text())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(messageList.get(1).role()).isEqualTo("user");
    assertThat(((TextItem) messageList.get(2).content().items().get(0)).text())
        .isEqualTo("Orchestration Service funktioniert!");
    assertThat(messageList.get(2).role()).isEqualTo("assistant");

    var llm = response.getIntermediateResults().getLlm();
    assertThat(llm).isNotNull();
    assertThat(llm.getId()).isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(llm.getObject()).isEqualTo("chat.completion");
    assertThat(llm.getCreated()).isEqualTo(1721224505);
    assertThat(llm.getModel()).isEqualTo("gpt-35-turbo-16k");
    var choices = llm.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Le service d'orchestration fonctionne!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    var usage = result.getTokenUsage();
    assertThat(usage.getCompletionTokens()).isEqualTo(7);
    assertThat(usage.getPromptTokens()).isEqualTo(19);
    assertThat(usage.getTotalTokens()).isEqualTo(26);
    var orchestrationResult = response.getFinalResult();
    assertThat(orchestrationResult.getId()).isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(orchestrationResult.getObject()).isEqualTo("chat.completion");
    assertThat(orchestrationResult.getCreated()).isEqualTo(1721224505);
    assertThat(orchestrationResult.getModel()).isEqualTo("gpt-35-turbo-16k");
    choices = orchestrationResult.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Le service d'orchestration fonctionne!");
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo(ASSISTANT);
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
                    { "error":
                      {
                        "request_id": "51043a32-01f5-429a-b0e7-3a99432e43a4",
                        "code": 400,
                        "message": "Missing required parameters: ['input']",
                        "location": "Module: Templating",
                        "intermediate_results": {}
                      }
                    }
                    """,
                    SC_BAD_REQUEST)));

    assertThatThrownBy(() -> client.chatCompletion(prompt, config))
        .isInstanceOfSatisfying(
            OrchestrationClientException.class,
            e -> {
              assertThat(e.getMessage())
                  .isEqualTo(
                      "Request failed with status 400 (Bad Request): Missing required parameters: ['input']");
              assertThat(e.getErrorResponseStreaming()).isNull();
              assertThat(e.getErrorResponse()).isNotNull();
              assertThat(e.getErrorResponse().getError().getMessage())
                  .isEqualTo("Missing required parameters: ['input']");
              assertThat(e.getErrorResponse().getError().getCode()).isEqualTo(SC_BAD_REQUEST);
              assertThat(e.getErrorResponse().getError().getRequestId())
                  .isEqualTo("51043a32-01f5-429a-b0e7-3a99432e43a4");
              assertThat(e.getErrorResponse().getError().getLocation())
                  .isEqualTo("Module: Templating");
            });
  }

  @Test
  void filteringLoose() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("filteringLooseResponse.json")
                    .withHeader("Content-Type", "application/json")));

    final var azureFilter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE_LOW_MEDIUM)
            .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
            .sexual(ALLOW_SAFE_LOW_MEDIUM)
            .violence(ALLOW_SAFE_LOW_MEDIUM);

    final var llamaFilter = new LlamaGuardFilter().config(LlamaGuard38b.create().selfHarm(true));

    client.chatCompletion(
        prompt,
        config.withInputFiltering(azureFilter, llamaFilter).withOutputFiltering(azureFilter));
    // the result is asserted in the verify step below

    // verify that null fields are absent from the sent request
    try (var requestInputStream = fileLoader.apply("filteringLooseRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request, true, true)));
    }
  }

  @Test
  void inputFilteringStrict() {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("strictInputFilterResponse.json")
                    .withHeader("Content-Type", "application/json")
                    .withStatus(SC_BAD_REQUEST)));

    final var azureFilter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE)
            .selfHarm(ALLOW_SAFE)
            .sexual(ALLOW_SAFE)
            .violence(ALLOW_SAFE);

    final var llamaFilter =
        new LlamaGuardFilter().config(LlamaGuard38b.create().violentCrimes(true));
    final var configWithFilter = config.withInputFiltering(azureFilter, llamaFilter);

    assertThatThrownBy(() -> client.chatCompletion(prompt, configWithFilter))
        .isInstanceOfSatisfying(
            OrchestrationFilterException.Input.class,
            e -> {
              assertThat(e.getMessage())
                  .isEqualTo(
                      "Request failed with status 400 (Bad Request): 400 - Filtering Module - Input Filter: Prompt filtered due to safety violations. Please modify the prompt and try again.");
              assertThat(e.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
              assertThat(e.getFilterDetails())
                  .isEqualTo(
                      Map.of(
                          "azure_content_safety",
                              Map.of(
                                  "Hate", 6,
                                  "SelfHarm", 0,
                                  "Sexual", 0,
                                  "Violence", 6,
                                  "userPromptAnalysis", Map.of("attackDetected", false)),
                          "llama_guard_3_8b", Map.of("violent_crimes", true)));

              final var errorResponse = e.getErrorResponse();
              assertThat(errorResponse).isNotNull();
              assertThat(errorResponse).isInstanceOf(ErrorResponse.class);
              assertThat(errorResponse.getError().getCode()).isEqualTo(SC_BAD_REQUEST);
              assertThat(errorResponse.getError().getMessage())
                  .isEqualTo(
                      "400 - Filtering Module - Input Filter: Prompt filtered due to safety violations. Please modify the prompt and try again.");

              assertThat(e.getAzureContentSafetyInput()).isNotNull();
              assertThat(e.getAzureContentSafetyInput().getHate()).isEqualTo(NUMBER_6);
              assertThat(e.getAzureContentSafetyInput().getSelfHarm()).isEqualTo(NUMBER_0);
              assertThat(e.getAzureContentSafetyInput().getSexual()).isEqualTo(NUMBER_0);
              assertThat(e.getAzureContentSafetyInput().getViolence()).isEqualTo(NUMBER_6);

              assertThat(e.getLlamaGuard38b()).isNotNull();
              assertThat(e.getLlamaGuard38b().isViolentCrimes()).isTrue();
            });
  }

  @Test
  void outputFilteringStrict() {
    stubFor(post(anyUrl()).willReturn(aResponse().withBodyFile("outputFilteringStrict.json")));

    final var azureFilter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE)
            .selfHarm(ALLOW_SAFE)
            .sexual(ALLOW_SAFE)
            .violence(ALLOW_SAFE);

    final var llamaFilter =
        new LlamaGuardFilter().config(LlamaGuard38b.create().violentCrimes(true));
    final var configWithFilter = config.withOutputFiltering(azureFilter, llamaFilter);

    assertThatThrownBy(client.chatCompletion(prompt, configWithFilter)::getContent)
        .isInstanceOfSatisfying(
            OrchestrationFilterException.Output.class,
            e -> {
              assertThat(e.getMessage()).isEqualTo("Content filter filtered the output.");
              assertThat(e.getFilterDetails())
                  .isEqualTo(
                      Map.of(
                          "index",
                          0,
                          "azure_content_safety",
                          Map.of(
                              "Hate", 6,
                              "SelfHarm", 0,
                              "Sexual", 0,
                              "Violence", 6),
                          "llama_guard_3_8b",
                          Map.of("violent_crimes", true)));
              assertThat(e.getErrorResponse()).isNull();
              assertThat(e.getStatusCode()).isNull();

              assertThat(e.getAzureContentSafetyOutput()).isNotNull();
              assertThat(e.getAzureContentSafetyOutput().getHate()).isEqualTo(NUMBER_6);
              assertThat(e.getAzureContentSafetyOutput().getSelfHarm()).isEqualTo(NUMBER_0);
              assertThat(e.getAzureContentSafetyOutput().getSexual()).isEqualTo(NUMBER_0);
              assertThat(e.getAzureContentSafetyOutput().getViolence()).isEqualTo(NUMBER_6);

              assertThat(e.getLlamaGuard38b()).isNotNull();
              assertThat(e.getLlamaGuard38b().isViolentCrimes()).isTrue();
            });
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
            new AssistantMessage(
                new MessageContent(
                    List.of(
                        new TextItem("The capital of France is Paris."),
                        new TextItem("Paris is known for its art, fashion, and culture.")))));
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
    GenericModuleResult inputMasking = response.getIntermediateResults().getInputMasking();
    assertThat(inputMasking).isNotNull();
    assertThat(inputMasking.getMessage()).isEqualTo("Input to LLM is masked successfully.");
    assertThat(inputMasking.getData()).isNotNull();
    assertThat(result.getContent()).contains("Hi Mallory");

    // verify that the request is sent correctly
    try (var requestInputStream = fileLoader.apply("maskingRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request, true, true)));
    }
  }

  private static Runnable[] errorHandlingCalls() {
    return new Runnable[] {
      () -> client.chatCompletion(new OrchestrationPrompt(""), config),
      () ->
          client
              .streamChatCompletion(new OrchestrationPrompt(""), config)
              // the stream needs to be consumed to parse the response
              .forEach(System.out::println)
    };
  }

  @ParameterizedTest
  @MethodSource("errorHandlingCalls")
  void testErrorHandling(@Nonnull final Runnable request) {
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

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Server errors should be handled")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("500");

    softly
        .assertThatThrownBy(request::run)
        .describedAs("Error objects from Orchestration should be interpreted")
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("'config' is a required property");

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
        .hasMessageContaining("HTTP Response is empty");

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

  @Test
  void testThrowsOnContentFilter() {
    var mock = mock(OrchestrationClient.class);
    when(mock.streamChatCompletion(any(), any())).thenCallRealMethod();

    var deltaWithContentFilter = mock(OrchestrationChatCompletionDelta.class);
    when(deltaWithContentFilter.getFinishReason()).thenReturn("content_filter");

    var moduleResults = mock(ModuleResultsStreaming.class);
    when(deltaWithContentFilter.getIntermediateResults()).thenReturn(moduleResults);

    var outputFiltering = mock(GenericModuleResult.class);
    when(moduleResults.getOutputFiltering()).thenReturn(outputFiltering);

    var filterData =
        Map.of(
            "choices", List.of(Map.of("azure_content_safety", Map.of("hate", 0, "self_harm", 0))));
    when(outputFiltering.getData()).thenReturn(filterData);

    when(mock.streamChatCompletionDeltas(any())).thenReturn(Stream.of(deltaWithContentFilter));

    // this must not throw, since the stream is lazily evaluated
    var stream = mock.streamChatCompletion(new OrchestrationPrompt(""), config);
    assertThatThrownBy(stream::toList)
        .isInstanceOf(OrchestrationFilterException.Output.class)
        .hasMessage("Content filter filtered the output.")
        .extracting(e -> ((OrchestrationFilterException.Output) e).getFilterDetails())
        .isEqualTo(Map.of("azure_content_safety", Map.of("hate", 0, "self_harm", 0)));
  }

  @Test
  void streamChatCompletionOutputFilterErrorHandling() throws IOException {
    try (var inputStream = spy(fileLoader.apply("streamChatCompletionOutputFilter.txt"))) {

      final var httpClient = mock(HttpClient.class);
      ApacheHttpClient5Accessor.setHttpClientFactory(destination -> httpClient);

      // Create a mock response
      final var mockResponse = new BasicClassicHttpResponse(200, "OK");
      final var inputStreamEntity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);
      mockResponse.setEntity(inputStreamEntity);
      mockResponse.setHeader("Content-Type", "text/event-stream");

      // Configure the HttpClient mock to return the mock response
      doReturn(mockResponse).when(httpClient).executeOpen(any(), any(), any());

      try (Stream<String> stream = client.streamChatCompletion(prompt, config)) {
        assertThatThrownBy(() -> stream.forEach(System.out::println))
            .hasMessage("Content filter filtered the output.")
            .isInstanceOfSatisfying(
                OrchestrationFilterException.Output.class,
                e -> {
                  assertThat(e.getErrorResponse()).isNull();
                  assertThat(e.getErrorResponseStreaming()).isNull();
                  assertThat(e.getStatusCode()).isNull();

                  assertThat(e.getFilterDetails())
                      .isEqualTo(
                          Map.of(
                              "index",
                              0,
                              "azure_content_safety",
                              Map.of("Hate", 0, "SelfHarm", 0, "Sexual", 0, "Violence", 4)));

                  assertThat(e.getAzureContentSafetyOutput()).isNotNull();
                  assertThat(e.getAzureContentSafetyOutput().getHate()).isEqualTo(NUMBER_0);
                  assertThat(e.getAzureContentSafetyOutput().getSelfHarm()).isEqualTo(NUMBER_0);
                  assertThat(e.getAzureContentSafetyOutput().getSexual()).isEqualTo(NUMBER_0);
                  assertThat(e.getAzureContentSafetyOutput().getViolence()).isEqualTo(NUMBER_4);
                });
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

      var prompt =
          new OrchestrationPrompt(
              "Can you give me the first 100 numbers of the Fibonacci sequence?");
      var request = OrchestrationClient.toCompletionPostRequest(prompt, config);

      try (Stream<OrchestrationChatCompletionDelta> stream =
          client.streamChatCompletionDeltas(request)) {
        var deltaList = stream.toList();

        assertThat(deltaList).hasSize(3);
        // the first delta doesn't have any content
        assertThat(deltaList.get(0).getDeltaContent()).isEqualTo("");
        assertThat(deltaList.get(1).getDeltaContent()).isEqualTo("Sure");
        assertThat(deltaList.get(2).getDeltaContent()).isEqualTo("!");

        assertThat(deltaList.get(0).getRequestId())
            .isEqualTo("5bd87b41-6368-4c18-aaae-47ab82e9475b");
        assertThat(deltaList.get(1).getRequestId())
            .isEqualTo("5bd87b41-6368-4c18-aaae-47ab82e9475b");
        assertThat(deltaList.get(2).getRequestId())
            .isEqualTo("5bd87b41-6368-4c18-aaae-47ab82e9475b");

        assertThat(deltaList.get(0).getFinishReason()).isEqualTo("");
        assertThat(deltaList.get(1).getFinishReason()).isEqualTo("");
        assertThat(deltaList.get(2).getFinishReason()).isEqualTo("stop");

        // should be of type LLMModuleResultStreaming, will be fixed with a discriminator
        var result0 = deltaList.get(0).getFinalResult();
        var result1 = deltaList.get(1).getFinalResult();
        var result2 = deltaList.get(2).getFinalResult();

        assertThat(result0.getSystemFingerprint()).isEmpty();
        assertThat(result0.getId()).isEmpty();
        assertThat(result0.getCreated()).isEqualTo(0);
        assertThat(result0.getModel()).isEmpty();
        assertThat(result0.getObject()).isEmpty();
        // BUG: usage is absent from the request
        assertThat(result0.getUsage()).isNull();
        assertThat(result0.getChoices()).hasSize(1);
        final var choices0 = result0.getChoices().get(0);
        assertThat(choices0.getIndex()).isEqualTo(0);
        assertThat(choices0.getFinishReason()).isEmpty();
        assertThat(choices0.toMap().get("delta")).isNotNull();
        // this should be getDelta(), only when the result is of type LLMModuleResultStreaming
        final ChatDelta message0 = choices0.getDelta();
        assertThat(message0.getRole()).isEqualTo("");
        assertThat(message0.getContent()).isEqualTo("");
        final var templating = deltaList.get(0).getIntermediateResults().getTemplating();
        assertThat(templating).hasSize(1);

        final var templateItem = (UserChatMessage) templating.get(0);
        assertThat(templateItem.getRole()).isEqualTo(USER);
        assertThat(((UserChatMessageContent.InnerString) templateItem.getContent()).value())
            .isEqualTo("Hello world! Why is this phrase so famous?");

        assertThat(result1.getSystemFingerprint()).isEqualTo("fp_808245b034");
        assertThat(result1.getId()).isEqualTo("chatcmpl-AYZSQQwWv7ajJsyDBpMG4X01BBJxq");
        assertThat(result1.getCreated()).isEqualTo(1732802814);
        assertThat(result1.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(result1.getObject()).isEqualTo("chat.completion.chunk");
        assertThat(result1.getUsage()).isNull();
        assertThat(result1.getChoices()).hasSize(1);
        final var choices1 = result1.getChoices().get(0);
        assertThat(choices1.getIndex()).isEqualTo(0);
        assertThat(choices1.getFinishReason()).isEmpty();
        assertThat(choices1.toMap().get("delta")).isNotNull();
        final ChatDelta message1 = choices1.getDelta();
        assertThat(message1.getRole()).isEqualTo("assistant");
        assertThat(message1.getContent()).isEqualTo("Sure");

        assertThat(result2.getSystemFingerprint()).isEqualTo("fp_808245b034");
        assertThat(result2.getId()).isEqualTo("chatcmpl-AYZSQQwWv7ajJsyDBpMG4X01BBJxq");
        assertThat(result2.getCreated()).isEqualTo(1732802814);
        assertThat(result2.getModel()).isEqualTo("gpt-35-turbo");
        assertThat(result2.getObject()).isEqualTo("chat.completion.chunk");
        assertThat(result2.getUsage()).isNull();
        assertThat(result2.getChoices()).hasSize(1);
        final var choices2 = result2.getChoices().get(0);
        assertThat(choices2.getIndex()).isEqualTo(0);
        assertThat(choices2.getFinishReason()).isEqualTo("stop");
        // this should be getDelta(), only when the result is of type LLMModuleResultStreaming
        assertThat(choices2.toMap().get("delta")).isNotNull();
        final ChatDelta message2 = choices2.getDelta();
        assertThat(message2.getRole()).isEqualTo("assistant");
        assertThat(message2.getContent()).isEqualTo("!");
      }
      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void testMultiMessage() throws IOException {
    stubFor(
        post("/v2/completion")
            .willReturn(aResponse().withStatus(SC_OK).withBodyFile("multiMessageResponse.json")));

    var llmWithImageSupportConfig = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

    var messageWithTwoTexts =
        Message.system("Please answer in exactly two sentences.")
            .withText("Start the first sentence with the word 'Well'.");

    var messageWithImage =
        Message.user("What is in this image?")
            .withText("And what is the main color?")
            .withImage(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    var prompt =
        new OrchestrationPrompt(messageWithImage).messageHistory(List.of(messageWithTwoTexts));

    var result = client.chatCompletion(prompt, llmWithImageSupportConfig);
    var response = result.getOriginalResponse();

    assertThat(result.getContent())
        .isEqualTo(
            "Well, this image features the logo of SAP, a software company, set against a gradient blue background transitioning from light to dark. The main color in the image is blue.");
    assertThat(result.getAllMessages()).hasSize(3);
    var systemMessage = result.getAllMessages().get(0);
    assertThat(systemMessage.role()).isEqualTo("system");
    assertThat(systemMessage.content().items()).hasSize(2);
    assertThat(systemMessage.content().items().get(0)).isInstanceOf(TextItem.class);
    assertThat(((TextItem) systemMessage.content().items().get(0)).text())
        .isEqualTo("Please answer in exactly two sentences.");
    assertThat(systemMessage.content().items().get(1)).isInstanceOf(TextItem.class);
    assertThat(((TextItem) systemMessage.content().items().get(1)).text())
        .isEqualTo("Start the first sentence with the word 'Well'.");
    var userMessage = result.getAllMessages().get(1);
    assertThat(userMessage.role()).isEqualTo("user");
    assertThat(userMessage.content().items()).hasSize(3);
    assertThat(userMessage.content().items().get(0)).isInstanceOf(TextItem.class);
    assertThat(((TextItem) userMessage.content().items().get(0)).text())
        .isEqualTo("What is in this image?");
    assertThat(userMessage.content().items().get(1)).isInstanceOf(TextItem.class);
    assertThat(((TextItem) userMessage.content().items().get(1)).text())
        .isEqualTo("And what is the main color?");
    assertThat(userMessage.content().items().get(2)).isInstanceOf(ImageItem.class);
    assertThat(((ImageItem) userMessage.content().items().get(2)).imageUrl())
        .isEqualTo(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    var assistantMessage = result.getAllMessages().get(2);
    assertThat(assistantMessage.role()).isEqualTo("assistant");
    assertThat(assistantMessage.content().items()).hasSize(1);
    assertThat(assistantMessage.content().items().get(0)).isInstanceOf(TextItem.class);
    assertThat(((TextItem) assistantMessage.content().items().get(0)).text())
        .isEqualTo(
            "Well, this image features the logo of SAP, a software company, set against a gradient blue background transitioning from light to dark. The main color in the image is blue.");

    assertThat(response).isNotNull();
    var llmResults = response.getIntermediateResults().getLlm();
    assertThat(llmResults).isNotNull();
    assertThat(llmResults.getChoices()).hasSize(1);
    assertThat(llmResults.getChoices().get(0).getMessage().getContent())
        .isEqualTo(
            "Well, this image features the logo of SAP, a software company, set against a gradient blue background transitioning from light to dark. The main color in the image is blue.");
    assertThat(llmResults.getChoices().get(0).getFinishReason()).isEqualTo("stop");
    assertThat(llmResults.getChoices().get(0).getMessage().getRole()).isEqualTo(ASSISTANT);
    var orchestrationResult = response.getFinalResult();
    assertThat(orchestrationResult.getChoices()).hasSize(1);
    assertThat(orchestrationResult.getChoices().get(0).getMessage().getContent())
        .isEqualTo(
            "Well, this image features the logo of SAP, a software company, set against a gradient blue background transitioning from light to dark. The main color in the image is blue.");
    assertThat(orchestrationResult.getChoices().get(0).getFinishReason()).isEqualTo("stop");
    assertThat(orchestrationResult.getChoices().get(0).getMessage().getRole()).isEqualTo(ASSISTANT);

    try (var requestInputStream = fileLoader.apply("multiMessageRequest.json")) {
      final String requestBody = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/v2/completion"))
              .withRequestBody(equalToJson(requestBody)));
    }
  }

  //    Example class
  static class Translation {
    @JsonProperty(required = true)
    private String language;

    @JsonProperty(required = true)
    private String translation;
  }

  @Test
  void testResponseFormatJsonSchema() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("jsonSchemaResponse.json")
                    .withHeader("Content-Type", "application/json")));

    val config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

    val schema =
        ResponseJsonSchema.fromType(Translation.class)
            .withDescription("Output schema for language translation.")
            .withStrict(true);
    val configWithResponseSchema =
        config.withTemplateConfig(TemplateConfig.create().withJsonSchemaResponse(schema));

    val prompt =
        new OrchestrationPrompt(
            Message.user("Whats 'apple' in German?"),
            Message.system("You are a language translator."));

    val response = client.chatCompletion(prompt, configWithResponseSchema);
    assertThat(response.getContent())
        .isEqualTo("{\"translation\":\"Apfel\",\"language\":\"German\"}");

    // ------------- wrong use -------------
    class TranslationNotStaticNoConstructor {
      @JsonProperty(required = true)
      private String language;

      @JsonProperty(required = true)
      private String translation;
    }
    assertThatThrownBy(() -> response.asEntity(TranslationNotStaticNoConstructor.class))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "Please make sure to use the correct class and that the class has a no-args constructor or is static")
        .hasMessageContaining("JSON content: {\"translation\":\"Apfel\",\"language\":\"German\"}");

    // ------------- good use -------------
    Translation translation = response.asEntity(Translation.class);
    assertThat(translation.language).isEqualTo("German");
    assertThat(translation.translation).isEqualTo("Apfel");

    try (var requestInputStream = fileLoader.apply("jsonSchemaRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testJsonSchemaWrongConfig() {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));

    val response = client.chatCompletion(prompt, config);

    // no config and wrong response
    assertThatThrownBy(() -> response.asEntity(Translation.class))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "Please configure an OrchestrationTemplate with format set to JSON schema into your OrchestrationModuleConfig")
        .hasMessageContaining("JSON content: Le service d'orchestration fonctionne!");
  }

  @Test
  void testJsonSchemaRefusal() {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("errorJsonSchemaResponse.json")
                    .withHeader("Content-Type", "application/json")));

    val response = client.chatCompletion(prompt, config);

    assertThatThrownBy(() -> response.asEntity(Translation.class))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "The model refused to answer the question: I'm sorry, I cannot assist with that request.");
  }

  @Test
  void testResponseFormatJsonObject() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("jsonObjectResponse.json")
                    .withHeader("Content-Type", "application/json")));

    val config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

    val configWithJsonResponse =
        config.withTemplateConfig(TemplateConfig.create().withJsonResponse());

    val prompt =
        new OrchestrationPrompt(
            Message.user("What is 'apple' in German?"),
            Message.system(
                "You are a language translator. Answer using the following JSON format: {\"language\": ..., \"translation\": ...}"));

    final var message = client.chatCompletion(prompt, configWithJsonResponse).getContent();
    assertThat(message).isEqualTo("{\"language\": \"German\", \"translation\": \"Apfel\"}");

    try (var requestInputStream = fileLoader.apply("jsonObjectRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testResponseFormatText() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("responseFormatTextResponse.json")
                    .withHeader("Content-Type", "application/json")));

    val llmWithImageSupportConfig = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

    val template = Message.user("What is 'apple' in German?");
    val templatingConfig =
        Template.create()
            .template(List.of(template.createChatMessage()))
            .responseFormat(ResponseFormatText.create().type(ResponseFormatText.TypeEnum.TEXT));
    val configWithTemplate = llmWithImageSupportConfig.withTemplateConfig(templatingConfig);

    val prompt =
        new OrchestrationPrompt(
            Message.system("You are a language translator. Answer using JSON."));

    final var message = client.chatCompletion(prompt, configWithTemplate).getContent();
    assertThat(message)
        .isEqualTo(
            "```json\n{\n  \"word\": \"apple\",\n  \"translation\": \"Apfel\",\n  \"language\": \"German\"\n}\n```");

    try (var requestInputStream = fileLoader.apply("responseFormatTextRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testTemplateFromPromptRegistryById() throws IOException {
    {
      stubFor(
          post(anyUrl())
              .willReturn(
                  aResponse()
                      .withBodyFile("templateReferenceResponse.json")
                      .withHeader("Content-Type", "application/json")));

      var template = TemplateConfig.reference().byId("21cb1358-0bf1-4f43-870b-00f14d0f9f16");
      var configWithTemplate = config.withTemplateConfig(template);

      var inputParams = Map.of("language", "Italian", "input", "Cloud ERP systems");
      var prompt = new OrchestrationPrompt(inputParams);

      final var response = client.chatCompletion(prompt, configWithTemplate);
      assertThat(response.getContent()).startsWith("I sistemi ERP (Enterprise Resource Planning)");
      assertThat(response.getOriginalResponse().getIntermediateResults().getTemplating())
          .hasSize(2);

      try (var requestInputStream = fileLoader.apply("templateReferenceByIdRequest.json")) {
        final String request = new String(requestInputStream.readAllBytes());
        verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
      }
    }
  }

  @Test
  void testTemplateFromPromptRegistryByScenario() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templateReferenceResponse.json")
                    .withHeader("Content-Type", "application/json")));

    var template = TemplateConfig.reference().byScenario("test").name("test").version("0.0.1");
    var configWithTemplate = config.withTemplateConfig(template);

    var inputParams = Map.of("language", "Italian", "input", "Cloud ERP systems");
    var prompt = new OrchestrationPrompt(inputParams);

    final var response = client.chatCompletion(prompt, configWithTemplate);
    assertThat(response.getContent()).startsWith("I sistemi ERP (Enterprise Resource Planning)");
    assertThat(response.getOriginalResponse().getIntermediateResults().getTemplating()).hasSize(2);

    try (var requestInputStream = fileLoader.apply("templateReferenceByScenarioRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testTemplateFromInput() throws IOException {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templateReferenceResponse.json")
                    .withHeader("Content-Type", "application/json")));

    var promptTemplateYaml =
        Files.readString(Path.of("src/test/resources/promptTemplateExample.yaml"));

    var template = TemplateConfig.create().fromYaml(promptTemplateYaml);
    var configWithTemplate = template != null ? config.withTemplateConfig(template) : config;

    var inputParams = Map.of("language", "German");
    var prompt = new OrchestrationPrompt(inputParams);

    final var response = client.chatCompletion(prompt, configWithTemplate);

    try (var requestInputStream = fileLoader.apply("localTemplateRequest.json")) {
      final String request = new String(requestInputStream.readAllBytes());
      verify(postRequestedFor(anyUrl()).withRequestBody(equalToJson(request)));
    }
  }

  @Test
  void testTemplateFromInputThrows() {
    assertThatThrownBy(() -> TemplateConfig.create().fromYaml(": what?"))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("Failed to parse");

    prompt = new OrchestrationPrompt(Map.of());
    assertThatThrownBy(
            () ->
                TemplateConfig.create()
                    .fromYaml(
                        "name: translator\nversion: 0.0.1\nscenario: translation scenario\nspec:\n  template: what?"))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("Failed to deserialize");
  }

  @Test
  void testGetAllMessages() {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("templatingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var resultTemplating = client.chatCompletion(new OrchestrationPrompt("Hello"), config);
    final var messageListTemplating = resultTemplating.getAllMessages();
    assertThat(messageListTemplating.get(0)).isInstanceOf(SystemMessage.class);
    assertThat(messageListTemplating.get(1)).isInstanceOf(UserMessage.class);
    assertThat(messageListTemplating.get(2)).isInstanceOf(AssistantMessage.class);

    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("toolCallsResponse2.json")
                    .withHeader("Content-Type", "application/json")));
    final var resultTools = client.chatCompletion(new OrchestrationPrompt("Hello"), config);
    final var messageListTools = resultTools.getAllMessages();
    assertThat(messageListTools.get(0)).isInstanceOf(UserMessage.class);
    assertThat(messageListTools.get(1)).isInstanceOf(AssistantMessage.class);
    assertThat(messageListTools.get(2)).isInstanceOf(ToolMessage.class);
  }

  @Test
  void testEmbeddingCallWithMasking() {

    stubFor(
        post(urlEqualTo("/v2/embeddings"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody(
                        """
                        {
                          "request_id": "2ee98443-e1ee-9503-b800-e38b5b80fe45",
                          "intermediate_results": {
                            "input_masking": {
                              "message": "Embedding input is masked successfully.",
                              "data": {
                                "masked_input": "['Hello', 'MASKED_PERSON', '!']"
                              }
                            }
                          },
                          "final_result": {
                            "object": "list",
                            "data": [
                              {
                                "object": "embedding",
                                "embedding": [
                                  0.43988228,
                                  -0.82985526,
                                  -0.15936942,
                                  0.041005015,
                                  0.30127057
                                ],
                                "index": 0
                              }
                            ],
                            "model": "text-embedding-3-large",
                            "usage": {
                              "prompt_tokens": 10,
                              "total_tokens": 10
                            }
                          }
                        }
                        """)));

    val dpiConfig =
        DPIConfig.create()
            .type(DPIConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
            .method(DPIConfig.MethodEnum.ANONYMIZATION)
            .entities(List.of(DPIStandardEntity.create().type(DPIEntities.PERSON)));
    val maskingConfig = MaskingModuleConfigProviders.create().providers(List.of(dpiConfig));

    val modelParams =
        EmbeddingsModelParams.create()
            .encodingFormat(EmbeddingsModelParams.EncodingFormatEnum.FLOAT)
            .dimensions(5)
            .normalize(false);
    val modelConfig =
        EmbeddingsModelConfig.create()
            .model(
                EmbeddingsModelDetails.create().name("text-embedding-3-large").params(modelParams));

    val orchestrationConfig =
        EmbeddingsOrchestrationConfig.create()
            .modules(
                EmbeddingsModuleConfigs.create().embeddings(modelConfig).masking(maskingConfig));

    val inputText =
        EmbeddingsInput.create().text(EmbeddingsInputText.create("['Hello', 'Müller', '!']"));

    val request = EmbeddingsPostRequest.create().config(orchestrationConfig).input(inputText);

    EmbeddingsPostResponse response = client.embed(request);

    assertThat(response).isNotNull();
    assertThat(response.getRequestId()).isEqualTo("2ee98443-e1ee-9503-b800-e38b5b80fe45");

    val orchestrationResult = response.getFinalResult();
    assertThat(orchestrationResult).isNotNull();
    assertThat(orchestrationResult.getObject()).isEqualTo(EmbeddingsResponse.ObjectEnum.LIST);
    assertThat(orchestrationResult.getModel()).isEqualTo("text-embedding-3-large");

    val data = orchestrationResult.getData();
    assertThat(data).isNotEmpty();
    assertThat(data.get(0).getEmbedding())
        .isEqualTo(
            Embedding.create(
                List.of(
                    BigDecimal.valueOf(0.43988228),
                    BigDecimal.valueOf(-0.82985526),
                    BigDecimal.valueOf(-0.15936942),
                    BigDecimal.valueOf(0.041005015),
                    BigDecimal.valueOf(0.30127057))));
    assertThat(data.get(0).getIndex()).isZero();

    val usage = orchestrationResult.getUsage();
    assertThat(usage).isNotNull();
    assertThat(usage.getPromptTokens()).isEqualTo(10);
    assertThat(usage.getTotalTokens()).isEqualTo(10);

    val moduleResults = response.getIntermediateResults();
    assertThat(moduleResults).isNotNull();
    assertThat(moduleResults.getInputMasking()).isNotNull();
    assertThat(moduleResults.getInputMasking().getMessage())
        .isEqualTo("Embedding input is masked successfully.");
    assertThat(moduleResults.getInputMasking().getData()).isNotNull();
    assertThat(moduleResults.getInputMasking().getData())
        .isEqualTo(Map.of("masked_input", "['Hello', 'MASKED_PERSON', '!']"));

    verify(
        postRequestedFor(urlEqualTo("/v2/embeddings"))
            .withRequestBody(
                equalToJson(
                    """
                    {
                      "config": {
                        "modules": {
                          "embeddings": {
                            "model": {
                              "name": "text-embedding-3-large",
                              "version": "latest",
                              "timeout" : 600,
                              "max_retries" : 2,
                              "params": {
                                "encoding_format": "float",
                                "dimensions": 5,
                                "normalize": false
                              }
                            }
                          },
                          "masking": {
                            "providers": [
                              {
                                "type": "sap_data_privacy_integration",
                                "method": "anonymization",
                                "entities": [
                                  {
                                    "type": "profile-person"
                                  }
                                ],
                                "allowlist" : [ ]
                              }
                            ]
                          }
                        }
                      },
                      "input": {
                        "text": "['Hello', 'Müller', '!']"
                      }
                    }
                    """)));
  }
}
