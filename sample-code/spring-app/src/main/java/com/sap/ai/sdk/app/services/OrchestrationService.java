package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_1_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

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
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service class for the Demo app */
@Service
@Slf4j
public class OrchestrationService {

  @Nonnull
  public String processInput(@Nonnull final String userInput) {
    var client = new OrchestrationClient();
    var config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO.withParam(TEMPERATURE, 0));
    var prompt = new OrchestrationPrompt(userInput);
    var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.LOCATION);
    var response = client.chatCompletion(prompt, config.withMaskingConfig(maskingConfig));
    return response.getContent();
  }













































  /**
   * For Demo app. Uses grounding to be able to answer question about AI SDK features.
   *
   * @return the assistant response
   */
  @Nonnull
  public Stream<String> processInputStream(@Nonnull final String userInput) {
    final var config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O.withParam(TEMPERATURE, 0));
    final var client = new OrchestrationClient();
    final var systemMessage = Message.system("Please respond with a short, structured overview using Markdown.");
    final var message =
        Message.user(
            """
                {{?groundingInput}}

                Use the following information as additional context:
                
                {{?groundingOutput}}
                """);
    final var prompt = new OrchestrationPrompt(Map.of("groundingInput", userInput), message, systemMessage);

    final var filterInner =
        DocumentGroundingFilter.create()
            .id("someID")
            .dataRepositoryType(DataRepositoryType.VECTOR)
            .dataRepositories(List.of("cfbe985a-5023-4785-bda8-df71c7616a66"));
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

    return client.streamChatCompletion(prompt, configWithGrounding);
  }
}
