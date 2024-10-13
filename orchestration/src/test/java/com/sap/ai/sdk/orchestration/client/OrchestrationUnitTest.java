package com.sap.ai.sdk.orchestration.client;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.NUMBER_0;
import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
@WireMockTest
public class OrchestrationUnitTest {
  private OrchestrationClient client;

  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create()
          .modelName("gpt-35-turbo-16k")
          .modelParams(
              Map.of(
                  "max_tokens", 50,
                  "temperature", 0.1,
                  "frequency_penalty", 0,
                  "presence_penalty", 0));

  private static final TemplatingModuleConfig TEMPLATE_CONFIG =
      TemplatingModuleConfig.create()
          .template(List.of(ChatMessage.create().role("user").content("{{?input}}")));

  @BeforeEach
  void setup(WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = new OrchestrationClient(destination).withLlmConfig(LLM_CONFIG);
  }

  @Test
  void testTemplating() throws IOException {
    final String response =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("templatingResponse.json")
                .readAllBytes());
    stubFor(post(urlPathEqualTo("/completion")).willReturn(okJson(response)));

    final var inputParams =
        Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    var prompt = new OrchestrationPrompt(inputParams);

    final var result = client.chatCompletion(prompt);

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
    final String request =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("templatingRequest.json")
                .readAllBytes());
    verify(postRequestedFor(urlPathEqualTo("/completion")).withRequestBody(equalToJson(request)));
  }

  @Test
  void testTemplatingBadRequest() {
    stubFor(
        post(urlPathEqualTo("/completion"))
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

    Map<String, String> inputParams = Map.of();

    assertThatThrownBy(() -> client.chatCompletion(new OrchestrationPrompt(inputParams)))
        .isInstanceOf(HttpClientErrorException.class)
        .hasMessage(
            "400 Bad Request: \"{<EOL>  \"request_id\": \"51043a32-01f5-429a-b0e7-3a99432e43a4\",<EOL>  \"code\": 400,<EOL>  \"message\": \"Missing required parameters: ['input']\",<EOL>  \"location\": \"Module: Templating\",<EOL>  \"module_results\": {}<EOL>}<EOL>\"");
  }

  @Test
  void testFilteringLoose() throws IOException {
    final String response =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("filteringLooseResponse.json")
                .readAllBytes());
    stubFor(post(urlPathEqualTo("/completion")).willReturn(okJson(response)));

    var config =
        FilterConfig.create()
            .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
            .config(
                AzureContentSafety.create()
                    .hate(AzureThreshold.NUMBER_6)
                    .selfHarm(AzureThreshold.NUMBER_4));

    var prompt = new OrchestrationPrompt("Hello World!").withOutputContentFilter(config);

    client.chatCompletion(prompt);
    // the result is asserted in the verify step below

    // verify that null fields are absent from the sent request
    final String request =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("filteringLooseRequest.json")
                .readAllBytes());
    verify(postRequestedFor(urlPathEqualTo("/completion")).withRequestBody(equalToJson(request)));
  }

  @Test
  void testFilteringStrict() {
    final String response =
        """
            {"request_id": "bf6d6792-7adf-4d3c-9368-a73615af8c5a", "code": 400, "message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "location": "Input Filter", "module_results": {"templating": [{"role": "user", "content": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}], "input_filtering": {"message": "Content filtered due to Safety violations. Please modify the prompt and try again.", "data": {"original_service_response": {"Hate": 0, "SelfHarm": 0, "Sexual": 0, "Violence": 2}, "checked_text": "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent."}}}}""";
    stubFor(post(urlPathEqualTo("/completion")).willReturn(jsonResponse(response, SC_BAD_REQUEST)));

    var config =
        FilterConfig.create()
            .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
            .config(AzureContentSafety.create().hate(NUMBER_0).violence(AzureThreshold.NUMBER_2));

    var prompt = new OrchestrationPrompt("Hello World!").withInputContentFilter(config);

    client.chatCompletion(prompt);

    assertThatThrownBy(() -> client.chatCompletion(prompt))
        .isInstanceOf(HttpClientErrorException.class)
        .hasMessage("400 Bad Request: \"" + response + "\"");
  }

  @Test
  void testMessagesHistory() throws IOException {
    final String response =
        new String(
            getClass()
                .getClassLoader()
                // the response is not asserted in this test
                .getResourceAsStream("templatingResponse.json")
                .readAllBytes());
    stubFor(post(urlPathEqualTo("/completion")).willReturn(okJson(response)));

    final List<ChatMessage> messagesHistory =
        List.of(
            ChatMessage.create().role("user").content("What is the capital of France?"),
            ChatMessage.create().role("assistant").content("The capital of France is Paris."));

    final var result =
        client.chatCompletion(
            new OrchestrationPrompt(
                messagesHistory, Map.of("input", "What is the typical food there?")));

    assertThat(result.getRequestId()).isEqualTo("26ea36b5-c196-4806-a9a6-a686f0c6ad91");

    // verify that the history is sent correctly
    final String request =
        new String(
            getClass()
                .getClassLoader()
                .getResourceAsStream("messagesHistoryRequest.json")
                .readAllBytes());
    verify(postRequestedFor(urlPathEqualTo("/completion")).withRequestBody(equalToJson(request)));
  }
}
