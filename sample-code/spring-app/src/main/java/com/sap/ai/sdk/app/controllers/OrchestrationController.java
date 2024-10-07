package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.client.OrchestrationCompletionApi;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig.MethodEnum;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {

  private static final OrchestrationCompletionApi API =
      new OrchestrationCompletionApi(
          new AiCoreService().forDeploymentByScenario("orchestration").client());

  static final String MODEL = "gpt-35-turbo";

  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create().modelName(MODEL).modelParams(Map.of());

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
                .input(FilteringConfig.create().filters(List.of(filter)))
                .output(FilteringConfig.create().filters(List.of(filter)));

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

  private static final List<DPIEntityConfig> ALL_DPI_ENTITIES =
      Stream.of(DPIEntities.values())
          .filter(entity -> entity != DPIEntities.UNKNOWN_DEFAULT_OPEN_API)
          .map(entity -> DPIEntityConfig.create().type(entity))
          .toList();

  /**
   * Creates a config from a masking type (anonymization or pseudonymization). The config includes a
   * template.
   */
  private static final Function<MaskingProviderConfig.MethodEnum, CompletionPostRequest>
      MASKING_CONFIG =
          (MaskingProviderConfig.MethodEnum maskingType) -> {
            final var inputParams =
                Map.of(
                    "orgCV",
                    """
            Patrick Morgan
             +49 (970) 333-3833
             patric.morgan@hotmail.com

             Highlights
             - Strategic and financial planning expert
             - Accurate forecasting
             - Process implementation
             - Staff leadership and development
             - Business performance improvement
             - Proficient in SAP,  Excel VBA

             Education
             Master of Science: Finance - 2014
             Harvard University, Boston

             Bachelor of Science: Finance - 2011
             Harvard University, Boston


             Certifications
             Certified Management Accountant


             Summary
             Skilled Financial Manager adept at increasing work process efficiency and profitability through functional and technical analysis. Successful at advising large corporations, small businesses, and individual clients. Areas of expertise include asset allocation, investment strategy, and risk management.


             Experience
             Finance Manager - 09/2016 to 05/2018
             M&K Group, York
             - Manage the modelling, planning, and execution of all financial processes.
             - Carry short and long-term custom comprehensive financial strategies to reach company goals.
             - Recommended innovative alternatives to generate revenue and reduce unnecessary costs.
             - Employed advanced deal analysis, including hands-on negotiations with potential investors.
             - Research market trends and surveys and use information to stimulate business.

             Finance Manager - 09/2013 to 05/2016
             Ago Group, Chicago
             - Drafted executive analysis reports highlighting business issues, potential risks, and profit opportunities.
             - Recommended innovative alternatives to generate revenue and reduce unnecessary costs.
             - Employed advanced deal analysis, including hands-on negotiations with potential investors.
             - Analysed market trends and surveys and used information to revenue growth.""");
            final var template =
                ChatMessage.create()
                    .role("user")
                    .content("Summarize the following CV in 10 sentences: {{?orgCV}}");
            final var templatingConfig = TemplatingModuleConfig.create().template(template);

            final var maskingProvider =
                MaskingProviderConfig.create()
                    .type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
                    .method(MaskingProviderConfig.MethodEnum.ANONYMIZATION)
                    .entities(ALL_DPI_ENTITIES);
            final var maskingConfig =
                MaskingModuleConfig.create().maskingProviders(maskingProvider);

            return CompletionPostRequest.create()
                .orchestrationConfig(
                    OrchestrationConfig.create()
                        .moduleConfigurations(
                            ModuleConfigs.create()
                                .llmModuleConfig(LLM_CONFIG)
                                .templatingModuleConfig(templatingConfig)
                                .maskingModuleConfig(maskingConfig)))
                .inputParams(inputParams);
          };

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nonnull
  public CompletionPostResponse template() {

    final var template = ChatMessage.create().role("user").content("{{?input}}");
    final var inputParams =
        Map.of("input", "Reply with 'Orchestration Service is working!' in German");

    final var config =
        TEMPLATE_CONFIG
            .apply(TemplatingModuleConfig.create().template(template))
            .inputParams(inputParams);

    return API.orchestrationV1EndpointsCreate(config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a violent template and both input
   * and output filters.
   *
   * @param threshold A high threshold is a loose filter, a low threshold is a strict filter
   * @return the assistant message response
   */
  @GetMapping("/filter/{threshold}")
  @Nonnull
  public CompletionPostResponse filter(@Nonnull @PathVariable("threshold") final String threshold) {

    final var config =
        FILTERING_CONFIG.apply(AzureThreshold.fromValue(Integer.parseInt(threshold)));

    return API.orchestrationV1EndpointsCreate(config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
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

    final var config =
        TEMPLATE_CONFIG
            .apply(TemplatingModuleConfig.create().template(message))
            .messagesHistory(messagesHistory);

    return API.orchestrationV1EndpointsCreate(config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with an anonymization masking template
   *
   * @return the assistant message response
   */
  @GetMapping("/maskingAnonymization")
  @Nonnull
  public CompletionPostResponse maskingAnonymization() {
    final var config = MASKING_CONFIG.apply(MaskingProviderConfig.MethodEnum.ANONYMIZATION);

    return API.orchestrationV1EndpointsCreate(config);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a pseudonymization masking
   * template
   *
   * @return the assistant message response
   */
  @GetMapping("/maskingPseudonymization")
  @Nonnull
  public CompletionPostResponse maskingPseudonymization() {
    final var config = MASKING_CONFIG.apply(MethodEnum.PSEUDONYMIZATION);

    return API.orchestrationV1EndpointsCreate(config);
  }
}
