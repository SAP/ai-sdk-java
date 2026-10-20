package com.sap.ai.sdk.orchestration;

import com.google.common.collect.Lists;
import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfiguration;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceById;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByIdConfigRef;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByNameScenarioVersion;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByNameScenarioVersionConfigRef;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfigModules;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfig;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import com.sap.ai.sdk.orchestration.model.TranslationModuleConfig;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import io.vavr.control.Option;
import java.util.function.UnaryOperator;
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

  @Nonnull
  static CompletionRequestConfiguration toCompletionPostRequest(
      @Nonnull final PromptTemplate template,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {

    val modules =
        Lists.asList(config, fallbackConfigs).stream()
            .map(
                c ->
                    setupModuleConfigs(
                        c,
                        toTemplateModuleConfig(
                            template,
                            ModelPromptCachingSupport.forModel(c.getLlmConfig().getName()))))
            .toList();
    val messageHistory =
        template.getMessageHistory().stream().map(Message::createChatMessage).toList();

    final OrchestrationConfigModules moduleConfigs =
        modules.size() == 1
            ? OrchestrationConfigModules.createInnerModuleConfigs(modules.get(0))
            : OrchestrationConfigModules.createListOfModuleConfigss(modules);

    val requestConfig =
        OrchestrationConfig.create().modules(moduleConfigs).stream(config.getGlobalStreamOptions());

    return CompletionRequestConfiguration.create()
        .config(requestConfig)
        .placeholderValues(template.getInputParameters())
        .messagesHistory(messageHistory);
  }

  @Nonnull
  static PromptTemplatingModuleConfigPrompt toTemplateModuleConfig(
      @Nonnull final PromptTemplate prompt) {
    return toTemplateModuleConfig(prompt, ModelPromptCachingSupport.noCaching());
  }

  @Nonnull
  static PromptTemplatingModuleConfigPrompt toTemplateModuleConfig(
      @Nonnull final PromptTemplate prompt,
      @Nonnull final ModelPromptCachingSupport cachingConfig) {
    val templateModuleConfig = prompt.toLowLevel();
    if (templateModuleConfig instanceof TemplateRef) {
      return templateModuleConfig;
    }
    if (!(templateModuleConfig instanceof Template template)) {
      throw new IllegalArgumentException(
          "Unsupported prompt template: " + templateModuleConfig.getClass());
    }
    // Copy the entire template, including custom fields, before adjusting cache checkpoints.
    val result =
        OrchestrationJacksonConfiguration.getOrchestrationObjectMapper()
            .convertValue(template, Template.class);
    val constraints = cachingConstraints(cachingConfig);
    for (val message : result.getTemplate()) {
      if (message instanceof UserChatMessage user
          && user.getContent()
              instanceof UserChatMessageContent.ListOfUserChatMessageContentItems items) {
        for (val item : items.values()) {
          item.setCacheControl(
              adjustCacheControl(item.getText(), item.getCacheControl(), constraints));
        }
      } else {
        final ChatMessageContent content =
            message instanceof SystemChatMessage system
                ? system.getContent()
                : message instanceof ToolChatMessage tool
                    ? tool.getContent()
                    : message instanceof AssistantChatMessage assistant
                        ? assistant.getContent()
                        : null;
        if (content instanceof ChatMessageContent.ListOfTextContents items) {
          for (val item : items.values()) {
            item.setCacheControl(
                message instanceof AssistantChatMessage
                    ? null
                    : adjustCacheControl(item.getText(), item.getCacheControl(), constraints));
          }
        }
      }
    }
    return result;
  }

  @Nullable
  private static com.sap.ai.sdk.orchestration.model.CacheControl adjustCacheControl(
      final String text,
      final com.sap.ai.sdk.orchestration.model.CacheControl cache,
      final UnaryOperator<TextItem> constraints) {
    if (cache == null) {
      return null;
    }
    val adjusted =
        constraints
            .apply(
                new TextItem(
                    text,
                    new CacheControl(cache.getTtl() == null ? "5m" : cache.getTtl().toString())))
            .getCacheControl();
    return adjusted == null
        ? null
        : com.sap.ai.sdk.orchestration.model.CacheControl.create()
            .type(cache.getType())
            .ttl(
                com.sap.ai.sdk.orchestration.model.CacheControl.TtlEnum.fromValue(
                    adjusted.getTtl()));
  }

  private static UnaryOperator<TextItem> cachingConstraints(
      final ModelPromptCachingSupport config) {
    final int[] remaining = {config.getMaxCheckpointsPerRequest()};
    return item -> {
      val cache = item.getCacheControl();
      if (cache == null) {
        return item;
      }
      if (remaining[0] <= 0
          || config.getMinTokensPerCheckpoint()
              > item.getText().chars().filter(c -> c == ' ').count() + 1) {
        return new TextItem(item.text());
      }
      remaining[0]--;
      return config.supportsTTLValue(cache.getTtl())
          ? item
          : new TextItem(item.text(), new CacheControl(config.getDefaultTTLValue()));
    };
  }

  @Nonnull
  private static ModuleConfigs setupModuleConfigs(
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final PromptTemplatingModuleConfigPrompt template) {
    val llmConfig = config.getLlmConfig();

    val moduleConfig =
        ModuleConfigs.create()
            .promptTemplating(
                PromptTemplatingModuleConfig.create().model(llmConfig).prompt(template));

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
    final var messageHistory =
        reference.getMessageHistory().stream().map(Message::createChatMessage).toList();
    final var placeholders = reference.getInputParameters();

    if (reference.getId() != null) {
      return CompletionRequestConfigurationReferenceById.create()
          .configRef(
              CompletionRequestConfigurationReferenceByIdConfigRef.create().id(reference.getId()))
          .messagesHistory(messageHistory)
          .placeholderValues(placeholders);
    } else {
      return CompletionRequestConfigurationReferenceByNameScenarioVersion.create()
          .configRef(
              CompletionRequestConfigurationReferenceByNameScenarioVersionConfigRef.create()
                  .scenario(reference.getScenario())
                  .name(reference.getName())
                  .version(reference.getVersion()))
          .messagesHistory(messageHistory)
          .placeholderValues(placeholders);
    }
  }
}
