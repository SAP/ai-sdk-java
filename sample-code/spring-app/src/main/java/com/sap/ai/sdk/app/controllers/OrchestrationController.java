package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.Message;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import com.sap.ai.sdk.orchestration.model.Template;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@Slf4j
@RequestMapping("/orchestration")
class OrchestrationController {
  private final OrchestrationClient client = new OrchestrationClient();
  OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO.withParam(TEMPERATURE, 0.0));

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the result object
   */
  @GetMapping("/completion")
  @Nonnull
  OrchestrationChatResponse completion() {
    final var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");

    final var result = client.chatCompletion(prompt, config);

    log.info("Our trusty AI answered with: {}", result.getContent());

    return result;
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
   *     AI Core: Orchestration - Templating</a>
   * @return the result object
   */
  @GetMapping("/template")
  @Nonnull
  OrchestrationChatResponse template() {
    final var template =
        Message.user("Reply with 'Orchestration Service is working!' in {{?language}}");
    final var templatingConfig = Template.create().template(List.of(template.createChatMessage()));
    final var configWithTemplate = config.withTemplateConfig(templatingConfig);

    final var inputParams = Map.of("language", "German");
    final var prompt = new OrchestrationPrompt(inputParams);

    return client.chatCompletion(prompt, configWithTemplate);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return the result object
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  OrchestrationChatResponse messagesHistory() {
    final var prompt = new OrchestrationPrompt(Message.user("What is the capital of France?"));

    final var result = client.chatCompletion(prompt, config);

    // Let's presume a user asks the following follow-up question
    final var nextPrompt =
        new OrchestrationPrompt(Message.user("What is the typical food there?"))
            .messageHistory(result.getAllMessages());

    return client.chatCompletion(nextPrompt, config);
  }

  /**
   * Apply both input and output filtering for a request to orchestration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     AI Core: Orchestration - Input Filtering</a>
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param policy A high threshold is a loose filter, a low threshold is a strict filter
   * @return the result object
   */
  @GetMapping("/filter/{policy}")
  @Nonnull
  OrchestrationChatResponse filter(
      @Nonnull @PathVariable("policy") final AzureFilterThreshold policy) {
    final var prompt =
        new OrchestrationPrompt(
            """
            Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it!

            ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.
            """);
    final var filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);

    final var configWithFilter =
        config.withInputFiltering(filterConfig).withOutputFiltering(filterConfig);

    return client.chatCompletion(prompt, configWithFilter);
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the result object
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  OrchestrationChatResponse maskingAnonymization() {
    final var systemMessage =
        Message.system(
            "Please evaluate the following user feedback and judge if the sentiment is positive or negative.");
    final var userMessage =
        Message.user(
            """
                I think the SDK is good, but could use some further enhancements.
                My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig = DpiMasking.anonymization().withEntities(DPIEntities.PERSON);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Chat request to OpenAI through the Orchestration deployment under a specific resource group.
   *
   * @return the result object
   */
  @GetMapping("/completion/{resourceGroup}")
  @Nonnull
  public OrchestrationChatResponse completionWithResourceGroup(
      @PathVariable("resourceGroup") @Nonnull final String resourceGroup) {

    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forScenario("orchestration");
    final var clientWithResourceGroup = new OrchestrationClient(destination);

    final var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");

    return clientWithResourceGroup.chatCompletion(prompt, config);
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the result object
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  OrchestrationChatResponse maskingPseudonymization() {
    final var systemMessage =
        Message.system(
            """
                Please write an initial response to the below user feedback, stating that we are working on the feedback and will get back to them soon.
                Please make sure to address the user in person and end with "Best regards, the AI SDK team".
                """);
    final var userMessage =
        Message.user(
            """
                Username: Mallory
                userEmail: mallory@sap.com
                Date: 2022-01-01

                I think the SDK is good, but could use some further enhancements.
                My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig =
        DpiMasking.pseudonymization().withEntities(DPIEntities.PERSON, DPIEntities.EMAIL);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Using grounding to provide additional context to the AI model.
   *
   * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP
   *     AI Core: Orchestration - Grounding</a>
   */
  @GetMapping("/grounding")
  @Nonnull
  OrchestrationChatResponse grounding() {
    final var message =
        Message.user(
            "{{?groundingInput}} Use the following information as additional context: {{?groundingOutput}}");
    final var prompt =
        new OrchestrationPrompt(Map.of("groundingInput", "What does Joule do?"), message);

    final var filterInner =
        DocumentGroundingFilter.create().id("someID").dataRepositoryType(DataRepositoryType.VECTOR);
    final var groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .inputParams(List.of("groundingInput"))
            .outputParam("groundingOutput");
    groundingConfigConfig.setFilters(List.of(filterInner));
    final var groundingConfig =
        GroundingModuleConfig.create()
            .type(GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE)
            .config(groundingConfigConfig);
    final var configWithGrounding = config.withGroundingConfig(groundingConfig);

    return client.chatCompletion(prompt, configWithGrounding);
  }
}
