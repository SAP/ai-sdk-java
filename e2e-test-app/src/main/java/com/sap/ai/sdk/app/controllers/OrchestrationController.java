package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.Core;

import static com.sap.ai.sdk.core.ApiClientResolver.Service.ORCHESTRATION;

import com.sap.ai.sdk.orchestration.client.OrchestrationCompletionApi;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for the Orchestration service */
@RestController
@RequestMapping("/orchestration")
class OrchestrationController {

  private static final OrchestrationCompletionApi API =
      new OrchestrationCompletionApi(Core.getInstance().forService(ORCHESTRATION).resourceGroup("default").getClient());

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

  /**
   * Chat request to OpenAI through the Orchestration service with a template
   *
   * @return the assistant message response
   */
  @GetMapping("/template")
  @Nullable
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
  @Nullable
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
  @Nullable
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
}
