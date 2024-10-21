package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
final class ModuleConfigFactory {
  @Nonnull
  static ModuleConfigs toModuleConfigDTO(
      @Nonnull final OrchestrationConfig<?> config, @Nonnull final List<ChatMessage> messages) {
    final var llm =
        config
            .getLlmConfig()
            .map(ModuleConfigFactory::toLlmModuleConfigDTO)
            .getOrElseThrow(() -> new IllegalStateException("LLM module config is required"));

    /*
     * Currently, we have to merge the prompt into the template configuration.
     * This works around the limitation that the template config isn't optional.
     * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
     * In this case, the request will fail, since the templating module will try to resolve the parameter.
     * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
     */
    Option<TemplatingModuleConfig> maybeTemplate = config.getTemplate();
    final var messagesWithPrompt = new ArrayList<>(messages);
    maybeTemplate.map(TemplatingModuleConfig::getTemplate).forEach(messagesWithPrompt::addAll);

    if (messagesWithPrompt.isEmpty()) {
      throw new IllegalStateException(
          "A prompt is required. Pass at least one message or configure the templating module.");
    }
    TemplatingModuleConfig template = TemplatingModuleConfig.create().template(messagesWithPrompt);
    maybeTemplate.map(TemplatingModuleConfig::getDefaults).forEach(template::defaults);

    ModuleConfigs dto =
        ModuleConfigs.create().llmModuleConfig(llm).templatingModuleConfig(template);

    config
        .getMaskingConfig()
        .filter(DpiMaskingConfig.class::isInstance)
        .map(DpiMaskingConfig.class::cast)
        .map(DpiMaskingConfig::toMaskingProviderDTO)
        .map(it -> MaskingModuleConfig.create().maskingProviders(it))
        .forEach(dto::maskingModuleConfig);

    var maybeInputFilter = config.getInputContentFilter();
    var maybeOutputFilter = config.getOutputContentFilter();

    if (maybeInputFilter.isDefined() || maybeOutputFilter.isDefined()) {
      var filter = FilteringModuleConfig.create();
      maybeInputFilter
          .filter(AzureContentFilter.class::isInstance)
          .map(AzureContentFilter.class::cast)
          .map(AzureContentFilter::toFilterConfigDTO)
          .map(it -> InputFilteringConfig.create().filters(it))
          .forEach(filter::input);
      maybeOutputFilter
          .filter(AzureContentFilter.class::isInstance)
          .map(AzureContentFilter.class::cast)
          .map(AzureContentFilter::toFilterConfigDTO)
          .map(it -> OutputFilteringConfig.create().filters(it))
          .forEach(filter::output);
      dto = dto.filteringModuleConfig(filter);
    }

    return dto;
  }

  @Nonnull
  static LLMModuleConfig toLlmModuleConfigDTO(@Nonnull final AiModel model) {
    if (model instanceof LlmConfig llmConfig) {
      return llmConfig.toLLMModuleConfigDTO();
    }
    final var result = LLMModuleConfig.create().modelName(model.name()).modelParams(Map.of());
    if (model.version() != null) {
      result.modelVersion(model.version());
    }
    return result;
  }
}
