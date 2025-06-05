package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.model.ResponseChatMessage.RoleEnum.ASSISTANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.app.services.OrchestrationService;
import com.sap.ai.sdk.app.services.OrchestrationService.Translation;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.TemplateConfig;
import com.sap.ai.sdk.orchestration.TextItem;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

@Slf4j
class OrchestrationTest {
  private final OrchestrationClient client = new OrchestrationClient();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GEMINI_1_5_FLASH.withParam(TEMPERATURE, 0.0));
  OrchestrationService service;

  @BeforeEach
  void setUp() {
    service = new OrchestrationService();
  }

  @Test
  void testCompletion() {
    val result = service.completion("HelloWorld!");

    assertThat(result).isNotNull();
    assertThat(result.getContent()).isNotEmpty();
  }

  @Test
  void testStreamChatCompletion() {
    val prompt = new OrchestrationPrompt("Who is the prettiest?");
    val stream = new OrchestrationClient().streamChatCompletion(prompt, service.getConfig());

    val filledDeltaCount = new AtomicInteger(0);
    stream
        // foreach consumes all elements, closing the stream at the end
        .forEach(
        delta -> {
          log.info("delta: {}", delta);
          if (!delta.isEmpty()) {
            filledDeltaCount.incrementAndGet();
          }
        });

    // the first two and the last delta don't have any content
    // see OpenAiChatCompletionDelta#getDeltaContent
    assertThat(filledDeltaCount.get()).isGreaterThan(0);
  }

  @Test
  void testTemplate() {
    assertThat(service.getConfig().getLlmConfig()).isNotNull();
    val modelName = service.getConfig().getLlmConfig().getModelName();

    val result = service.template("German");
    val response = result.getOriginalResponse();

    assertThat(response.getRequestId()).isNotEmpty();
    assertThat(((TextItem) result.getAllMessages().get(0).content().items().get(0)).text())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(result.getAllMessages().get(0).role()).isEqualTo("user");
    var llm = (LLMModuleResultSynchronous) response.getModuleResults().getLlm();
    assertThat(llm.getId()).isEmpty();
    assertThat(llm.getObject()).isEqualTo("chat.completion");
    assertThat(llm.getCreated()).isGreaterThan(1);
    assertThat(llm.getModel()).isEqualTo(modelName);
    var choices = llm.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    var usage = result.getTokenUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);

    var orchestrationResult = ((LLMModuleResultSynchronous) response.getOrchestrationResult());
    assertThat(orchestrationResult.getObject()).isEqualTo("chat.completion");
    assertThat(orchestrationResult.getCreated()).isGreaterThan(1);
    assertThat(orchestrationResult.getModel()).isEqualTo(modelName);
    choices = orchestrationResult.getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo(ASSISTANT);
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    assertThat(result.getChoice()).isSameAs(choices.get(0));
    usage = result.getTokenUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);
  }

  @Test
  void testMessagesHistory() {
    CompletionPostResponse result =
        service.messagesHistory("What is the capital of France?").getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @SuppressWarnings("unchecked")
  @Test
  void testMaskingAnonymization() {
    var response = service.maskingAnonymization(DPIEntities.PERSON);
    var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");

    var maskingResult = result.getModuleResults().getInputMasking();
    assertThat(maskingResult.getMessage()).isNotEmpty();
    var data = (Map<String, Object>) maskingResult.getData();
    var maskedMessage = (String) data.get("masked_template");
    assertThat(maskedMessage)
        .describedAs("The masked input should not contain any user names")
        .doesNotContain("Alice", "Bob");

    assertThat(result.getModuleResults().getOutputUnmasking()).isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Test
  void testMaskingPseudonymization() {
    var response = service.maskingPseudonymization(DPIEntities.PERSON);
    var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(llmChoice.getMessage().getContent())
        .describedAs("The final response should contain the original user name")
        .contains("Mallory");

    var maskingResult = result.getModuleResults().getInputMasking();
    assertThat(maskingResult.getMessage()).isNotEmpty();
    var data = (Map<String, Object>) maskingResult.getData();
    var maskedMessage = (String) data.get("masked_template");
    assertThat(maskedMessage)
        .describedAs("The masked input should not contain any user names but only pseudonyms")
        .doesNotContain("Mallory", "Alice", "Bob")
        .contains("MASKED_PERSON");

    var unmaskingResult = result.getModuleResults().getOutputUnmasking();
    assertThat(unmaskingResult).isNotEmpty();
    assertThat(((LLMChoice) unmaskingResult.get(0)).getMessage().getContent())
        .describedAs("The unmasking step should replace the pseudonyms used by the LLM")
        .doesNotContain("MASKED_PERSON")
        .contains("Mallory");
  }

  @Test
  @DisabledIfSystemProperty(named = "aicore.landscape", matches = "production")
  void testGrounding() {
    assertThat(System.getProperty("aicore.landscape")).isNotEqualTo("production");
    var response = service.grounding("What does Joule do?", true);
    var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);
    assertThat(response).isNotNull();
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(result.getModuleResults().getGrounding()).isNotNull();
    assertThat(result.getModuleResults().getGrounding().getData()).isNotNull();
    assertThat(result.getModuleResults().getGrounding().getMessage()).isEqualTo("grounding result");

    var maskingResult = result.getModuleResults().getInputMasking();
    assertThat(maskingResult.getMessage()).isNotEmpty();
  }

  @Test
  @DisabledIfSystemProperty(named = "aicore.landscape", matches = "production")
  void testGroundingSharepoint() {
    assertThat(System.getProperty("aicore.landscape")).isNotEqualTo("production");
    var response = service.groundingSharepoint("What is the secret for the AI SDK e2e test?");
    assertThat(response).isNotNull();
    var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);
    assertThat(llmChoice.getMessage().getContent()).contains("&)UPnkL_izT)&1u%?2Kg*Y.@qFqR@/");
  }

  @Test
  void testCompletionWithResourceGroup() {
    var response = service.completionWithResourceGroup("ai-sdk-java-e2e", "Hello world!");
    var result = response.getOriginalResponse();
    var llmChoice =
        ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(llmChoice.getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testInputFilteringStrict() {
    var policy = AzureFilterThreshold.ALLOW_SAFE;

    assertThatThrownBy(() -> service.inputFiltering(policy))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "Prompt filtered due to safety violations. Please modify the prompt and try again.")
        .hasMessageContaining("400 Bad Request");
  }

  @Test
  void testInputFilteringLenient() {
    var policy = AzureFilterThreshold.ALLOW_ALL;

    var response = service.inputFiltering(policy);

    assertThat(response.getChoice().getFinishReason()).isEqualTo("stop");
    assertThat(response.getContent()).isNotEmpty();

    var filterResult = response.getOriginalResponse().getModuleResults().getInputFiltering();
    assertThat(filterResult.getMessage()).contains("passed");
  }

  @Test
  void testOutputFilteringStrict() {
    var policy = AzureFilterThreshold.ALLOW_SAFE;
    var response = service.outputFiltering(policy);

    assertThatThrownBy(response::getContent)
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("Content filter filtered the output.");
  }

  @Test
  void testOutputFilteringLenient() {
    var policy = AzureFilterThreshold.ALLOW_ALL;

    var response = service.outputFiltering(policy);

    assertThat(response.getChoice().getFinishReason()).isEqualTo("stop");
    assertThat(response.getContent()).isNotEmpty();

    var filterResult = response.getOriginalResponse().getModuleResults().getOutputFiltering();
    assertThat(filterResult.getMessage()).containsPattern("0 of \\d+ choices failed");
  }

  @Test
  void testLlamaGuardEnabled() {
    assertThatThrownBy(() -> service.llamaGuardInputFilter(true))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "Prompt filtered due to safety violations. Please modify the prompt and try again.")
        .hasMessageContaining("400 Bad Request");
  }

  @Test
  void testLlamaGuardDisabled() {
    var response = service.llamaGuardInputFilter(false);

    assertThat(response.getChoice().getFinishReason()).isEqualTo("stop");
    assertThat(response.getContent()).isNotEmpty();

    var filterResult = response.getOriginalResponse().getModuleResults().getInputFiltering();
    assertThat(filterResult.getMessage()).contains("passed");
  }

  @Test
  void testImageInput() {
    val result =
        service
            .imageInput(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png")
            .getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testImageInputBase64() {
    String dataUrl = "";
    try {
      URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/c/c9/Sap-logo-700x700.jpg");
      try (InputStream inputStream = url.openStream()) {
        byte[] imageBytes = inputStream.readAllBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(imageBytes);
        String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        dataUrl = "data:image/jpeg;base64," + encodedString;
      }
    } catch (Exception e) {
      System.out.println("Error fetching or reading the image from URL: " + e.getMessage());
    }
    val result = service.imageInput(dataUrl).getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testMultiStringInput() {
    val result =
        service
            .multiStringInput(
                List.of("What is the capital of France?", "What is Chess about?", "What is 2+2?"))
            .getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testResponseFormatJsonSchema() {
    Translation translation = service.responseFormatJsonSchema("apple");
    assertThat(translation.translation()).isNotEmpty();
    assertThat(translation.language()).isNotEmpty();
  }

  @Test
  void testResponseFormatJsonObject() {
    val result = service.responseFormatJsonObject("apple").getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getContent()).contains("\"language\":");
    assertThat(choices.get(0).getMessage().getContent()).contains("\"translation\":");
  }

  @Test
  void testResponseFormatText() {
    val result = service.responseFormatText("apple").getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testTemplateFromPromptRegistryById() {
    val result = service.templateFromPromptRegistryById("Cloud ERP systems").getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testTemplateFromPromptRegistryByScenario() {
    val result =
        service.templateFromPromptRegistryByScenario("Cloud ERP systems").getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testLocalPromptTemplate() throws IOException {
    val result =
        service
            .localPromptTemplate(
                Files.readString(Path.of("src/main/resources/promptTemplateExample.yaml")))
            .getOriginalResponse();
    val choices = ((LLMModuleResultSynchronous) result.getOrchestrationResult()).getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void testStreamingErrorHandlingTemplate() {
    val template = Message.user("Bad template: {{?language!@#$}}");
    val templatingConfig =
        TemplateConfig.create().withTemplate(List.of(template.createChatMessage()));
    val configWithTemplate = config.withTemplateConfig(templatingConfig);
    val inputParams = Map.of("language", "German");
    val prompt = new OrchestrationPrompt(inputParams);

    assertThatThrownBy(() -> client.streamChatCompletion(prompt, configWithTemplate))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("status 400 Bad Request")
        .hasMessageContaining("Error processing template:");
  }

  @Test
  void testStreamingErrorHandlingInputFilter() {
    val prompt = new OrchestrationPrompt("Create 5 paraphrases of 'I hate you'.");
    val filterConfig = new AzureContentFilter().hate(AzureFilterThreshold.ALLOW_SAFE);
    val configWithFilter = config.withInputFiltering(filterConfig);

    assertThatThrownBy(() -> client.streamChatCompletion(prompt, configWithFilter))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("status 400 Bad Request")
        .hasMessageContaining("Filtering Module - Input Filter");
  }

  @Test
  void testStreamingErrorHandlingMasking() {
    val prompt = new OrchestrationPrompt("Some message.");
    val maskingConfig =
        DpiMasking.anonymization().withEntities(DPIEntities.UNKNOWN_DEFAULT_OPEN_API);
    val configWithMasking = config.withMaskingConfig(maskingConfig);

    assertThatThrownBy(() -> client.streamChatCompletion(prompt, configWithMasking))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("status 400 Bad Request")
        .hasMessageContaining("'type': 'sap_data_privacy_integration', 'method': 'anonymization'");
  }

  @Test
  void testTranslation() {
    val result = service.translation();
    val content = result.getContent();
    // English translated to German
    assertThat(content).contains("Englisch");
    assertThat(content).contains("Der", "ist");

    GenericModuleResult inputTranslation =
        result.getOriginalResponse().getModuleResults().getInputTranslation();
    GenericModuleResult outputTranslation =
        result.getOriginalResponse().getModuleResults().getOutputTranslation();
    assertThat(inputTranslation).isNotNull();
    assertThat(outputTranslation).isNotNull();
    assertThat(inputTranslation.getMessage()).isEqualTo("Input to LLM is translated successfully.");
    assertThat(outputTranslation.getMessage()).isEqualTo("Output Translation successful");
  }
}
