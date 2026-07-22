package com.sap.ai.sdk.orchestration;

import com.google.common.collect.Lists;
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
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import io.vavr.collection.HashMap;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
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
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {

    final var cachingConfig = resolveCachingConfig(config, fallbackConfigs);
    final UnaryOperator<OrchestrationModuleConfig> copyWithImmutableTemplateConfig =
        c -> c.withTemplateConfig(toTemplateModuleConfig(prompt, cachingConfig, c.getTemplateConfig()));
    val configList = Lists.asList(config, fallbackConfigs);
    val configsCopy = Lists.transform(configList, copyWithImmutableTemplateConfig::apply);

    val messageHistory =
        prompt.getMessagesHistory().stream().map(Message::createChatMessage).toList();

    final OrchestrationConfigModules moduleConfigs =
        configsCopy.size() == 1
            ? toModuleConfigs(configsCopy.get(0))
            : toListOfModuleConfigs(configsCopy);

    val requestConfig =
        OrchestrationConfig.create().modules(moduleConfigs).stream(config.getGlobalStreamOptions());

    return CompletionRequestConfiguration.create()
        .config(requestConfig)
        .placeholderValues(prompt.getTemplateParameters())
        .messagesHistory(messageHistory);
  }

  @Nonnull
  static PromptTemplatingModuleConfigPrompt toTemplateModuleConfig(
          @Nonnull final OrchestrationPrompt prompt,
          @Nullable final PromptTemplatingModuleConfigPrompt config
  ) {
    return toTemplateModuleConfig(prompt, PromptCachingConfig.noCaching(), config);
  }

  @Nonnull
  static PromptTemplatingModuleConfigPrompt toTemplateModuleConfig(
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final PromptCachingConfig cachingConfig,
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

    var promptMessages = withCachingConstraintsApplied(prompt.getMessages(), cachingConfig);

    messagesWithPrompt.addAll(promptMessages.stream().map(Message::createChatMessage).toList());
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

  static PromptCachingConfig resolveCachingConfig(@Nonnull final OrchestrationModuleConfig config,
                                                  @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {

    var modelName = "unknown";
    if (config.getLlmConfig() != null) {
      modelName = config.getLlmConfig().getName();
    } else {
      for (var fallbackConfig : fallbackConfigs) {
        if (fallbackConfig.getLlmConfig() != null) {
          modelName = fallbackConfig.getLlmConfig().getName();
          break;
        }
      }
    }
    return PromptCachingConfig.forModel(modelName);
  }

  static List<Message> withCachingConstraintsApplied(@Nonnull final List<Message> promptMessages,
                                                     @Nonnull final PromptCachingConfig cachingConfig) {
    var outputMessages = new ArrayList<Message>(promptMessages.size());
    var remainingCacheableCheckpoints = cachingConfig.getMaxCheckpointsPerRequest();
    for (int i = 0; i < promptMessages.size(); i++) {
      var message = promptMessages.get(i);
      var contentItems = new ArrayList<ContentItem>(message.content().items().size());
      var messageSupportsCache = false;
      if (message instanceof UserMessage || message instanceof SystemMessage) {
        messageSupportsCache = true;
      }
      for (int j = 0; j < message.content().items().size(); j++) {
        var contentItem = message.content().items().get(j);
        if (contentItem instanceof TextItem textItem) {
          var existingCacheControl = textItem.getCacheControl();
          if (existingCacheControl != null) {
            if (remainingCacheableCheckpoints <= 0 || !messageSupportsCache) {
              // skipping text control if there is already too many cache checkpoints
              contentItem = new TextItem(textItem.text());
            } else if (cachingConfig.getMinTokensPerCheckpoint() > textItem.getText().chars().filter(c -> c == ' ').count() + 1) {
              // to few tokens to cache, skipping to respect model limitation
              contentItem = new TextItem(textItem.text());
            } else {
              remainingCacheableCheckpoints--;
              if (!cachingConfig.getSupportedTTLValues().matcher(existingCacheControl.getTtl()).matches()) {
                contentItem = new TextItem(textItem.text(), new CacheControl(cachingConfig.getDefaultTTLValue()));
              }
            }
          }
        }
        contentItems.add(contentItem);
      }
      if (message instanceof UserMessage) {
        message = new UserMessage(new MessageContent(contentItems));
      } else if (message instanceof SystemMessage) {
        message = new SystemMessage(new MessageContent(contentItems));
      }

      outputMessages.add(message);
    }
    return outputMessages;
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
                    .model(llmConfig)
                    .prompt(config.getTemplateConfig()));

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
