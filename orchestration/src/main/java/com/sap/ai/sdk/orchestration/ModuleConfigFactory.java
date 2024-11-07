package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/** Factory to create all DTOs from an orchestration configuration. */
@NoArgsConstructor(access = AccessLevel.NONE)
final class ModuleConfigFactory {
  @Nonnull
  static CompletionPostRequest toCompletionPostRequestDto(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config) {
    val template = toTemplateModuleConfigDto(prompt, config.getTemplate());
    // note that the config is immutable and implicitly copied here
    // copying is required here, to not alter the original config object, which might be reused for
    // subsequent requests
    val configCopy = config.withTemplate(template);

    return CompletionPostRequest.create()
        .orchestrationConfig(
            com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig.create()
                .moduleConfigurations(toModuleConfigsDto(configCopy)))
        .inputParams(prompt.getTemplateParameters())
        .messagesHistory(prompt.getMessagesHistory());
  }

  @Nonnull
  static TemplatingModuleConfig toTemplateModuleConfigDto(
      @Nonnull final OrchestrationPrompt prompt, @Nullable final TemplatingModuleConfig template) {
    /*
     * Currently, we have to merge the prompt into the template configuration.
     * This works around the limitation that the template config is required.
     * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
     * In this case, the request will fail, since the templating module will try to resolve the parameter.
     * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
     */
    val messages = Option.of(template).map(TemplatingModuleConfig::getTemplate).getOrElse(List::of);
    val messagesWithPrompt = new ArrayList<>(messages);
    messagesWithPrompt.addAll(prompt.getMessages());
    if (messagesWithPrompt.isEmpty()) {
      throw new IllegalStateException(
          "A prompt is required. Pass at least one message or configure a template with messages or a template reference.");
    }
    return TemplatingModuleConfig.create().template(messagesWithPrompt);
  }

  @Nonnull
  static ModuleConfigs toModuleConfigsDto(@Nonnull final OrchestrationModuleConfig config) {
    val llmConfig =
        Option.of(config.getLlmConfig())
            .getOrElseThrow(() -> new IllegalStateException("LLM config is required."));

    //noinspection DataFlowIssue the template is always non-null here
    val moduleConfig =
        ModuleConfigs.create()
            .llmModuleConfig(llmConfig)
            .templatingModuleConfig(config.getTemplate());

    Option.of(config.getFilteringConfig()).forEach(moduleConfig::filteringModuleConfig);
    Option.of(config.getMaskingConfig()).forEach(moduleConfig::maskingModuleConfig);

    return moduleConfig;
  }
}
