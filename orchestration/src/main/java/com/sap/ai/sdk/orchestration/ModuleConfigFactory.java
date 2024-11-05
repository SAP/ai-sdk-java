package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/** Factory to create all DTOs from an orchestration configuration. */
@NoArgsConstructor(access = AccessLevel.NONE)
final class ModuleConfigFactory {
  @Nonnull
  static CompletionPostRequest toCompletionPostRequestDto(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final ModuleConfigs config) {
    // copying is required because we have to merge the prompt into the template config
    // also, users may modify the object before request execution
    val configCopy = copyModuleConfigs(config);
    configCopy.setTemplatingModuleConfig(
        toTemplateModuleConfigDto(prompt, config.getTemplatingModuleConfig()));

    return CompletionPostRequest.create()
        .orchestrationConfig(
            com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig.create()
                .moduleConfigurations(configCopy))
        .inputParams(prompt.getTemplateParameters());
  }

  @Nonnull
  static TemplatingModuleConfig toTemplateModuleConfigDto(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final TemplatingModuleConfig template) {
    /*
     * Currently, we have to merge the prompt into the template configuration.
     * This works around the limitation that the template config isn't optional.
     * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
     * In this case, the request will fail, since the templating module will try to resolve the parameter.
     * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
     */
    val messagesWithPrompt = new ArrayList<>(template.getTemplate());
    messagesWithPrompt.addAll(prompt.getMessages());
    if (messagesWithPrompt.isEmpty()) {
      throw new IllegalStateException(
          "A prompt is required. Pass at least one message or configure a template with messages or a template reference.");
    }
    return TemplatingModuleConfig.create().template(messagesWithPrompt);
  }

  static ModuleConfigs copyModuleConfigs(@Nonnull final ModuleConfigs configs) {
    return ModuleConfigs.create()
        .llmModuleConfig(configs.getLlmModuleConfig())
        .templatingModuleConfig(configs.getTemplatingModuleConfig())
        .maskingModuleConfig(configs.getMaskingModuleConfig())
        .filteringModuleConfig(configs.getFilteringModuleConfig());
  }
}
