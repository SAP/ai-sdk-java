package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;

import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationAiModel;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for the Orchestration service */
@Service
@Slf4j
public class OrchestrationService {
  /**
   * For Demo app.
   * Uses grounding to be able to answer question about AI SDK features.
   *
   * @return the assistant response
   */
  @Nonnull
  public String processInputGrounding(@Nonnull final String userInput) {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GEMINI_1_5_FLASH);
    final var client = new OrchestrationClient();
    final var message =
        Message.user(
            "{{?groundingInput}} Use the following information as additional context: {{?groundingOutput}}");
    final var prompt = new OrchestrationPrompt(Map.of("groundingInput", userInput), message);

    final var filterInner =
        DocumentGroundingFilter.create().id("someID").dataRepositoryType(DataRepositoryType.VECTOR);
    final var groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .inputParams(List.of("groundingInput"))
            .outputParam("groundingOutput")
            .addFiltersItem(filterInner);
    final var groundingConfig =
        GroundingModuleConfig.create()
            .type(GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE)
            .config(groundingConfigConfig);
    final var configWithGrounding = config.withGroundingConfig(groundingConfig);

    return client.chatCompletion(prompt, configWithGrounding).getContent();
  }

  /**
   * For Demo app.
   * Starting point for demo app.
   *
   * @return the assistant response
   */
  @Nonnull
  public String processInput00(@Nonnull final String userInput) {
    return userInput;
  }

  /**
   * For Demo app.
   * First step for demo app, using the Orchestration service without features.
   *
   * @return the assistant response
   */
  @Nonnull
  public String processInput01(@Nonnull final String userInput) {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GEMINI_1_5_FLASH);
    final var client = new OrchestrationClient();
    final var prompt = new OrchestrationPrompt(userInput);
    return client.chatCompletion(prompt, config).getContent();
  }

  /**
   * For Demo app.
   * Second step for demo app, using the Orchestration service with masking.
   *
   * @return the assistant response
   */
  @Nonnull
  public String processInput02(@Nonnull final String userInput) {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GEMINI_1_5_FLASH);
    final var client = new OrchestrationClient();
    final var prompt = new OrchestrationPrompt(userInput);
    final var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.LOCATION);
    return client.chatCompletion(prompt, config.withMaskingConfig(maskingConfig)).getContent();
  }

  @Nonnull
  public String processInput(@Nonnull final String userInput) {
//    temp auf 0
//    zoom ausprobieren und auf Bildschrimgröße anpassen
//    Schrift art ändern und vergrößern in App
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO);
    var prompt = new OrchestrationPrompt(userInput);
    var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.LOCATION);
    var response = client.chatCompletion(prompt, config.withMaskingConfig(maskingConfig));
    return response.getContent();
  }
}
