package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/** Factory to create all data objects from an orchestration configuration. */
@NoArgsConstructor(access = AccessLevel.NONE)
final class ConfigToRequestTransformer {
  @Nonnull
  static CompletionPostRequest toCompletionPostRequest(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config) {
    val template = toTemplateModuleConfig(prompt, config.getTemplateConfig());

    // note that the config is immutable and implicitly copied here
    // copying is required here, to not alter the original config object, which might be reused for
    // subsequent requests
    val configCopy = config.withTemplateConfig(template);

    val messageHistory =
        prompt.getMessagesHistory().stream()
            .map(Message::createChatMessage)
            .map(ChatMessage.class::cast)
            .toList();

    val moduleConfigs = toModuleConfigs(configCopy);

    return CompletionPostRequest.create()
        .orchestrationConfig(OrchestrationConfig.create().moduleConfigurations(moduleConfigs))
        .inputParams(prompt.getTemplateParameters())
        .messagesHistory(messageHistory);
  }

  @Nonnull
  static TemplatingModuleConfig toTemplateModuleConfig(
      @Nonnull final OrchestrationPrompt prompt, @Nullable final TemplatingModuleConfig config) {
    /*
     * Currently, we have to merge the prompt into the template configuration.
     * This works around the limitation that the template config is required.
     * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
     * In this case, the request will fail, since the templating module will try to resolve the parameter.
     * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
     */
    if (config instanceof TemplateRef) {
      return config;
    }

    val template = config instanceof Template t ? t : Template.create().template();
    val messages = template.getTemplate();
    val messagesWithPrompt = new ArrayList<>(messages);

    messagesWithPrompt.addAll(
        prompt.getMessages().stream().map(Message::createChatMessage).toList());
    if (messagesWithPrompt.isEmpty()) {
      throw new IllegalStateException(
          "A prompt is required. Pass at least one message or configure a template with messages or a template reference.");
    }

    val result =
        Template.create()
            .template(messagesWithPrompt)
            .tools(template.getTools())
            .defaults(template.getDefaults())
            .responseFormat(template.getResponseFormat());

    for (val customFieldName : template.getCustomFieldNames()) {
      result.setCustomField(customFieldName, template.getCustomField(customFieldName));
    }
    return result;
  }

  @Nonnull
  static ModuleConfigs toModuleConfigs(@Nonnull final OrchestrationModuleConfig config) {
    val llmConfig =
        Option.of(config.getLlmConfig())
            .getOrElseThrow(() -> new IllegalStateException("LLM config is required."));

    //noinspection DataFlowIssue the template is always non-null here
    val moduleConfig =
        ModuleConfigs.create()
            .llmModuleConfig(llmConfig)
            .templatingModuleConfig(config.getTemplateConfig());

    Option.of(config.getFilteringConfig()).forEach(moduleConfig::filteringModuleConfig);
    Option.of(config.getMaskingConfig()).forEach(moduleConfig::maskingModuleConfig);
    Option.of(config.getGroundingConfig()).forEach(moduleConfig::groundingModuleConfig);

    return moduleConfig;
  }
}
