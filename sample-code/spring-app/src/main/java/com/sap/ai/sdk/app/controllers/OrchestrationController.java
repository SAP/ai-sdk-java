package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {
  LLMModuleConfig llmConfig = GPT_35_TURBO;

  private final OrchestrationClient client = new OrchestrationClient();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(llmConfig);

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the result object
   */
  @GetMapping("/completion")
  @Nonnull
  public CompletionPostResponse completion() {
    final var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");

    return client.chatCompletion(prompt, config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the result object
   */
  @GetMapping("/template")
  @Nonnull
  public CompletionPostResponse template() {
    final var template =
        ChatMessage.create()
            .role("user")
            .content("Reply with 'Orchestration Service is working!' in {{?language}}");
    final var templatingConfig = TemplatingModuleConfig.create().template(template);
    final var configWithTemplate = config.withTemplateConfig(templatingConfig);

    final var inputParams = Map.of("language", "German");
    final var prompt = new OrchestrationPrompt(inputParams);

    return client.chatCompletion(prompt, configWithTemplate);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the result object
   */
  @GetMapping("/messagesHistory")
  @Nonnull
  public CompletionPostResponse messagesHistory() {
    final List<ChatMessage> messagesHistory =
        List.of(
            ChatMessage.create().role("user").content("What is the capital of France?"),
            ChatMessage.create().role("assistant").content("The capital of France is Paris."));
    final var message =
        ChatMessage.create().role("user").content("What is the typical food there?");

    final var prompt = new OrchestrationPrompt(message).messageHistory(messagesHistory);

    return client.chatCompletion(prompt, config);
  }

  /**
   * Apply both input and output filtering for a request to orchestration.
   *
   * @param threshold A high threshold is a loose filter, a low threshold is a strict filter
   * @return the result object
   */
  @GetMapping("/filter/{threshold}")
  @Nonnull
  public CompletionPostResponse filter(
      @Nonnull @PathVariable("threshold") final AzureThreshold threshold) {
    final var prompt =
        new OrchestrationPrompt(
            """
            Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it!

            ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.
            """);
    final var filterConfig = createAzureContentFilter(threshold);
    final var configWithFilter = config.withFilteringConfig(filterConfig);

    return client.chatCompletion(prompt, configWithFilter);
  }

  /**
   * Helper method to build filter configurations.
   *
   * @param threshold The threshold to be applied across all filter categories.
   * @return A new filter configuration object.
   */
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

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @return the result object
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  public CompletionPostResponse maskingAnonymization() {
    final var systemMessage =
        ChatMessage.create()
            .role("system")
            .content(
                "Please evaluate the following user feedback and judge if the sentiment is positive or negative.");
    final var userMessage =
        ChatMessage.create()
            .role("user")
            .content(
                """
    I think the SDK is good, but could use some further enhancements.
    My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
    """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig =
        createMaskingConfig(MaskingProviderConfig.MethodEnum.ANONYMIZATION, DPIEntities.PERSON);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Let the orchestration service a response to a hypothetical user who provided feedback on the AI
   * SDK. Pseudonymize the user's name and location to protect their privacy.
   *
   * @return the result object
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  public CompletionPostResponse maskingPseudonymization() {
    final var systemMessage =
        ChatMessage.create()
            .role("system")
            .content(
                """
                Please write an initial response to the below user feedback, stating that we are working on the feedback and will get back to them soon.
                Please make sure to address the user in person and end with "Best regards, the AI SDK team".
                """);
    final var userMessage =
        ChatMessage.create()
            .role("user")
            .content(
                """
                Username: Mallory
                userEmail: mallory@sap.com
                Date: 2022-01-01

                I think the SDK is good, but could use some further enhancements.
                My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                """);

    final var prompt = new OrchestrationPrompt(systemMessage, userMessage);
    final var maskingConfig =
        createMaskingConfig(
            MaskingProviderConfig.MethodEnum.PSEUDONYMIZATION,
            DPIEntities.PERSON,
            DPIEntities.EMAIL);
    final var configWithMasking = config.withMaskingConfig(maskingConfig);

    return client.chatCompletion(prompt, configWithMasking);
  }

  /**
   * Helper method to build masking configurations.
   *
   * @param method Either anonymization or pseudonymization.
   * @param entities The entities to mask.
   * @return A new masking configuration object.
   */
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
}
