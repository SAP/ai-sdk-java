package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfiguration;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceById;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByIdConfigRef;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByNameScenarioVersion;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByNameScenarioVersionConfigRef;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfigModules;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfigModules.InnerModuleConfigs;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfig;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TranslationModuleConfig;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/** Factory to create all data objects from an orchestration configuration. */
@Slf4j
@NoArgsConstructor(access = AccessLevel.NONE)
final class ConfigToRequestTransformer {
  static CompletionRequestConfiguration toCompletionPostRequest(
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final OrchestrationModuleConfig... configs) {

    val templates =
        Arrays.stream(configs)
            .map(config -> toTemplateModuleConfig(prompt, config.getTemplateConfig()))
            .toList();

    // note that the config is immutable and implicitly copied here
    // copying is required here, to not alter the original config object, which might be reused for
    // subsequent requests
    val configsCopy =
        IntStream.range(0, configs.length)
            .mapToObj(i -> configs[i].withTemplateConfig(templates.get(i)))
            .toList();

    val messageHistory =
        prompt.getMessagesHistory().stream().map(Message::createChatMessage).toList();

    final OrchestrationConfigModules moduleConfigs =
        configsCopy.size() == 1
            ? toModuleConfigs(configsCopy.get(0))
            : toListOfModuleConfigs(configsCopy);

    // JONAS: I am just using the stream options of the first config here. So I assume they don't
    // change between the configs.
    val requestConfig =
        OrchestrationConfig.create().modules(moduleConfigs).stream(
            configs[0].getGlobalStreamOptions());

    return CompletionRequestConfiguration.create()
        .config(requestConfig)
        .placeholderValues(prompt.getTemplateParameters())
        .messagesHistory(messageHistory);
  }

  @Nonnull
  static PromptTemplatingModuleConfigPrompt toTemplateModuleConfig(
      @Nonnull final OrchestrationPrompt prompt,
      @Nullable final PromptTemplatingModuleConfigPrompt config) {
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
      result.setCustomField(customFieldName, template.toMap().get(customFieldName));
    }
    return result;
  }

  @Nonnull
  static InnerModuleConfigs toModuleConfigs(@Nonnull final OrchestrationModuleConfig config) {
    return OrchestrationConfigModules.createInnerModuleConfigs(setupModuleConfigs(config));
  }

  @Nonnull
  static OrchestrationConfigModules.ListOfModuleConfigss toListOfModuleConfigs(
      @Nonnull final List<OrchestrationModuleConfig> configs) {
    return OrchestrationConfigModules.createListOfModuleConfigss(
        configs.stream().map(ConfigToRequestTransformer::setupModuleConfigs).toList());
  }

  @Nonnull
  private static ModuleConfigs setupModuleConfigs(@Nonnull final OrchestrationModuleConfig config) {
    val llmConfig =
        Option.of(config.getLlmConfig())
            .getOrElseThrow(() -> new IllegalStateException("LLM config is required."));

    //noinspection DataFlowIssue the template is always non-null here
    val moduleConfig =
        ModuleConfigs.create()
            .promptTemplating(
                PromptTemplatingModuleConfig.create()
                    .prompt(config.getTemplateConfig())
                    .model(llmConfig));

    Option.of(config.getFilteringConfig()).forEach(moduleConfig::filtering);
    Option.of(config.getMaskingConfig()).forEach(moduleConfig::masking);
    Option.of(config.getGroundingConfig()).forEach(moduleConfig::grounding);

    val outputTranslation = Option.of(config.getOutputTranslationConfig());
    val inputTranslation = Option.of(config.getInputTranslationConfig());

    if (inputTranslation.isDefined() || outputTranslation.isDefined()) {
      moduleConfig.setTranslation(TranslationModuleConfig.create());
      inputTranslation.forEach(moduleConfig.getTranslation()::input);
      outputTranslation.forEach(moduleConfig.getTranslation()::output);
    }
    return moduleConfig;
  }

  @Nonnull
  static CompletionPostRequest fromReferenceToCompletionPostRequest(
      @Nonnull final OrchestrationConfigReference reference) {
    final OrchestrationPrompt prompt = reference.getPrompt();
    final var messageHistory =
        prompt.getMessagesHistory().stream().map(Message::createChatMessage).toList();
    final var placeholders = prompt.getTemplateParameters();

    if (reference.getId() != null) {
      final CompletionRequestConfigurationReferenceById request =
          CompletionRequestConfigurationReferenceById.create()
              .configRef(
                  CompletionRequestConfigurationReferenceByIdConfigRef.create()
                      .id(reference.getId()));
      request.setMessagesHistory(messageHistory);
      request.setPlaceholderValues(placeholders);
      return request;
    } else {
      final CompletionRequestConfigurationReferenceByNameScenarioVersion request =
          CompletionRequestConfigurationReferenceByNameScenarioVersion.create()
              .configRef(
                  CompletionRequestConfigurationReferenceByNameScenarioVersionConfigRef.create()
                      .scenario(reference.getScenario())
                      .name(reference.getName())
                      .version(reference.getVersion()));
      request.setMessagesHistory(messageHistory);
      request.setPlaceholderValues(placeholders);
      return request;
    }
  }
}
