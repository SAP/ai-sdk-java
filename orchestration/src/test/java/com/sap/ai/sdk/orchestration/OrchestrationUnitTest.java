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
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.*;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TextContent;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
    config = new OrchestrationModuleConfig().withLlmConfig(CUSTOM_GPT_35);
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
  void testGrounding() {
    stubFor(
        post(anyUrl())
            .willReturn(
                aResponse()
                    .withBodyFile("groundingResponse.json")
                    .withHeader("Content-Type", "application/json")));
    final var response = client.chatCompletion(prompt, config);
    final var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);

    final var groundingData =
        (Map<String, Object>) result.getModuleResults().getGrounding().getData();
    assertThat(groundingData.get("grounding_query")).isEqualTo("grounding call");
    assertThat(groundingData.get("grounding_result").toString())
        .startsWith("Joule is the AI copilot that truly understands your business.");
    assertThat(result.getModuleResults().getGrounding().getMessage()).isEqualTo("grounding result");
    assertThat(((ChatMessage) result.getModuleResults().getTemplating().get(0)).getContent())
        .startsWith(
            "What does Joule do? Use the following information as additional context: Joule is the AI copilot that truly understands your business.");
    assertThat(llmChoice.getMessage().getContent())
        .startsWith(
            "Joule is an AI copilot that revolutionizes how users interact with their SAP business systems.");
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(llmChoice.getMessage().getContent())
        .startsWith(
            "Joule is an AI copilot that revolutionizes how users interact with their SAP business systems.");
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

    assertThat(messageList.get(0).content()).isEqualTo("You are a multi language translator");
    assertThat(messageList.get(0).role()).isEqualTo("system");
    assertThat(messageList.get(1).content())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(messageList.get(1).role()).isEqualTo("user");
    assertThat(messageList.get(2).content()).isEqualTo("Orchestration Service funktioniert!");
    assertThat(messageList.get(2).role()).isEqualTo("assistant");

    var llm = (LLMModuleResultSynchronous) response.getModuleResults().getLlm();
    assertThat(llm).isNotNull();
    assertThat(llm.getId()).isEqualTo("chatcmpl-9lzPV4kLrXjFckOp2yY454wksWBoj");
    assertThat(llm.getObject()).isEqualTo("chat.completion");
    assertThat(llm.getCreated()).isEqualTo(1721224505);
    assertThat(llm.getModel()).isEqualTo("gpt-35-turbo-16k");
    var choices = llm.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent())
        .isEqualTo("Le service d'orchestration fonctionne!");
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
        .isEqualTo("Le service d'orchestration fonctionne!");
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
            "Request failed with status 400 Bad Request and error message: 'Missing required parameters: ['input']'");
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
            "Request failed with status 400 Bad Request and error message: 'Content filtered due to Safety violations. Please modify the prompt and try again.'");
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

  @Test
  void testThrowsOnContentFilter() {
    var mock = mock(OrchestrationClient.class);
    when(mock.streamChatCompletion(any(), any())).thenCallRealMethod();

    var deltaWithContentFilter = mock(OrchestrationChatCompletionDelta.class);
    when(deltaWithContentFilter.getFinishReason()).thenReturn("content_filter");
    when(mock.streamChatCompletionDeltas(any())).thenReturn(Stream.of(deltaWithContentFilter));

    // this must not throw, since the stream is lazily evaluated
    var stream = mock.streamChatCompletion(new OrchestrationPrompt(""), config);
    assertThatThrownBy(stream::toList)
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("Content filter");
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
            .isInstanceOf(OrchestrationClientException.class)
            .hasMessage("Content filter filtered the output.");
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
        var result0 = (LLMModuleResultSynchronous) deltaList.get(0).getOrchestrationResult();
        var result1 = (LLMModuleResultSynchronous) deltaList.get(1).getOrchestrationResult();
        var result2 = (LLMModuleResultSynchronous) deltaList.get(2).getOrchestrationResult();

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
        assertThat(choices0.getCustomField("delta")).isNotNull();
        // this should be getDelta(), only when the result is of type LLMModuleResultStreaming
        final var message0 = (Map<String, Object>) choices0.getCustomField("delta");
        assertThat(message0.get("role")).isEqualTo("");
        assertThat(message0.get("content")).isEqualTo("");
        final var templating = deltaList.get(0).getModuleResults().getTemplating();
        assertThat(templating).hasSize(1);

        final var templateItem = (ChatMessage) templating.get(0);
        assertThat(templateItem.getRole()).isEqualTo("user");
        assertThat(templateItem.getContent())
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
        assertThat(choices1.getCustomField("delta")).isNotNull();
        final var message1 = (Map<String, Object>) choices1.getCustomField("delta");
        assertThat(message1.get("role")).isEqualTo("assistant");
        assertThat(message1.get("content")).isEqualTo("Sure");

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
        assertThat(choices2.getCustomField("delta")).isNotNull();
        final var message2 = (Map<String, Object>) choices2.getCustomField("delta");
        assertThat(message2.get("role")).isEqualTo("assistant");
        assertThat(message2.get("content")).isEqualTo("!");
      }
      Mockito.verify(inputStream, times(1)).close();
    }
  }

  @Test
  void testMultiChatMessageRequest() throws IOException {

    stubFor(
        post("/completion")
            .willReturn(
                aResponse().withStatus(SC_OK).withBodyFile("multiChatMessageResponse.json")));

    var multiChatMessage =
        MultiChatMessage.create()
            .role("user")
            .content(
                List.of(
                    TextContent.create()
                        .type(TextContent.TypeEnum.TEXT)
                        .text("Can you solve this captcha? Please help me prove my humanity!"),
                    ImageContent.create()
                        .type(ImageContent.TypeEnum.IMAGE_URL)
                        .imageUrl(
                            ImageContentImageUrl.create()
                                .url(
                                    "https://images.unsplash.com/photo-1468971050039-be99497410af?q=80&w=3432&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))));

    var llmWithImageSupportConfig =
        LLMModuleConfig.create()
            .modelName(GPT_4O_MINI.getName())
            .modelParams(Map.of())
            .modelVersion(GPT_4O_MINI.getVersion());

    var templatingModuleConfig = Template.create().template(List.of(multiChatMessage));

    CompletionPostRequest completionPostRequest =
        CompletionPostRequest.create()
            .orchestrationConfig(
                OrchestrationConfig.create()
                    .moduleConfigurations(
                        ModuleConfigs.create()
                            .llmModuleConfig(llmWithImageSupportConfig)
                            .templatingModuleConfig(templatingModuleConfig)));

    var response = client.executeRequest(completionPostRequest);

    assertThat(response).isNotNull();
    assertThat(response.getRequestId()).isEqualTo("2547cb86-a143-4064-bf40-45461c6a7ed9");

    assertThat(response.getModuleResults()).isNotNull();
    assertThat(response.getModuleResults().getTemplating()).hasSize(1);

    var multiChatMessageResponse =
        (MultiChatMessage) response.getModuleResults().getTemplating().get(0);
    assertThat(((TextContent) multiChatMessageResponse.getContent().get(0)).getText())
        .isEqualTo("Can you solve this captcha? Please help me prove my humanity!");
    assertThat(((TextContent) multiChatMessageResponse.getContent().get(0)).getType())
        .isEqualTo(TextContent.TypeEnum.TEXT);
    assertThat(((ImageContent) multiChatMessageResponse.getContent().get(1)).getType())
        .isEqualTo(ImageContent.TypeEnum.IMAGE_URL);
    assertThat(((ImageContent) multiChatMessageResponse.getContent().get(1)).getImageUrl().getUrl())
        .isEqualTo(
            "https://images.unsplash.com/photo-1468971050039-be99497410af?q=80&w=3432&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

    var llmResults = (LLMModuleResultSynchronous) response.getModuleResults().getLlm();
    assertThat(llmResults).isNotNull();
    assertThat(llmResults.getId()).isEqualTo("chatcmpl-Annjjf8T5LfLh7PRJPbaUlcC48DdE");
    assertThat(llmResults.getObject()).isEqualTo("chat.completion");
    assertThat(llmResults.getCreated()).isEqualTo(1736432623);
    assertThat(llmResults.getModel()).isEqualTo("gpt-4o-mini-2024-07-18");
    assertThat(llmResults.getSystemFingerprint()).isEqualTo("fp_5154047bf2");

    assertThat(llmResults.getChoices()).hasSize(1);
    assertThat(llmResults.getChoices().get(0).getMessage().getContent())
        .isEqualTo(
            "The text in the image says \"WORK HARDER\". If you need to enter this as part of a captcha verification, that's what you should type in!");
    assertThat(llmResults.getChoices().get(0).getFinishReason()).isEqualTo("stop");
    assertThat(llmResults.getChoices().get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(llmResults.getChoices().get(0).getIndex()).isZero();

    assertThat(llmResults.getUsage().getCompletionTokens()).isEqualTo(31);
    assertThat(llmResults.getUsage().getPromptTokens()).isEqualTo(928);
    assertThat(llmResults.getUsage().getTotalTokens()).isEqualTo(959);

    var orchestrationResult = (LLMModuleResultSynchronous) response.getOrchestrationResult();
    assertThat(orchestrationResult).isNotNull();
    assertThat(orchestrationResult.getId()).isEqualTo("chatcmpl-Annjjf8T5LfLh7PRJPbaUlcC48DdE");
    assertThat(orchestrationResult.getObject()).isEqualTo("chat.completion");
    assertThat(orchestrationResult.getCreated()).isEqualTo(1736432623);
    assertThat(orchestrationResult.getModel()).isEqualTo("gpt-4o-mini-2024-07-18");
    assertThat(orchestrationResult.getSystemFingerprint()).isEqualTo("fp_5154047bf2");
    assertThat(orchestrationResult.getChoices()).hasSize(1);
    assertThat(orchestrationResult.getChoices().get(0).getMessage().getContent())
        .isEqualTo(
            "The text in the image says \"WORK HARDER\". If you need to enter this as part of a captcha verification, that's what you should type in!");
    assertThat(orchestrationResult.getChoices().get(0).getFinishReason()).isEqualTo("stop");
    assertThat(orchestrationResult.getChoices().get(0).getMessage().getRole())
        .isEqualTo("assistant");
    assertThat(orchestrationResult.getChoices().get(0).getIndex()).isZero();
    assertThat(orchestrationResult.getUsage().getCompletionTokens()).isEqualTo(31);
    assertThat(orchestrationResult.getUsage().getPromptTokens()).isEqualTo(928);
    assertThat(orchestrationResult.getUsage().getTotalTokens()).isEqualTo(959);

    try (var requestInputStream = fileLoader.apply("multiChatMessageRequest.json")) {
      final String requestBody = new String(requestInputStream.readAllBytes());
      verify(
          postRequestedFor(urlPathEqualTo("/completion"))
              .withRequestBody(equalToJson(requestBody)));
    }
  }
}
