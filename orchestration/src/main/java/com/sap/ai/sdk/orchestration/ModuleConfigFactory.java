package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.client.model.TemplateRefByScenarioNameVersion;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.NotImplementedError;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/** Factory to create all DTOs from an orchestration configuration. */
@NoArgsConstructor(access = AccessLevel.NONE)
final class ModuleConfigFactory {
  @Nonnull
  static ModuleConfigs toModuleConfigDto(
      @Nonnull final OrchestrationConfig config, @Nonnull final List<Message> messages) {
    val llmDto =
        Option.of(config.getLlmConfig())
            .map(ModuleConfigFactory::toLlmModuleConfigDto)
            .getOrElseThrow(() -> new IllegalStateException("LLM module config is required"));

    val template =
        Option.of(config.getTemplate()).getOrElse(() -> TemplateConfig.fromMessages(List.of()));
    val templateDto = toTemplateModuleConfigDto(template, messages);

    var resultDto =
        ModuleConfigs.create().llmModuleConfig(llmDto).templatingModuleConfig(templateDto);

    Option.of(config.getMaskingConfig())
        .filter(DpiMaskingConfig.class::isInstance)
        .map(DpiMaskingConfig.class::cast)
        .map(DpiMaskingConfig::toMaskingProviderDto)
        .map(it -> MaskingModuleConfig.create().maskingProviders(it))
        .forEach(resultDto::maskingModuleConfig);

    val maybeInputFilter = Option.of(config.getInputContentFilter());
    val maybeOutputFilter = Option.of(config.getOutputContentFilter());

    if (maybeInputFilter.isDefined() || maybeOutputFilter.isDefined()) {
      val filter = FilteringModuleConfig.create();
      maybeInputFilter
          .filter(AzureContentFilter.class::isInstance)
          .map(AzureContentFilter.class::cast)
          .map(AzureContentFilter::toFilterConfigDto)
          .map(it -> InputFilteringConfig.create().filters(it))
          .forEach(filter::input);
      maybeOutputFilter
          .filter(AzureContentFilter.class::isInstance)
          .map(AzureContentFilter.class::cast)
          .map(AzureContentFilter::toFilterConfigDto)
          .map(it -> OutputFilteringConfig.create().filters(it))
          .forEach(filter::output);
      resultDto = resultDto.filteringModuleConfig(filter);
    }

    return resultDto;
  }

  @Nonnull
  static LLMModuleConfig toLlmModuleConfigDto(@Nonnull final AiModel model) {
    if (model instanceof LlmConfig llmConfig) {
      return llmConfig.toLLMModuleConfigDto();
    }
    val dto = LLMModuleConfig.create().modelName(model.name()).modelParams(Map.of());
    if (model.version() != null) {
      dto.modelVersion(model.version());
    }
    return dto;
  }

  @Nonnull
  static TemplatingModuleConfig toTemplateModuleConfigDto(
      @Nonnull final TemplateConfig templateConfig, @Nonnull final List<Message> messages) {
    if (templateConfig instanceof Template.Messages templateMessages) {
      /*
       * Currently, we have to merge the prompt into the template configuration.
       * This works around the limitation that the template config isn't optional.
       * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
       * In this case, the request will fail, since the templating module will try to resolve the parameter.
       * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
       */
      val messagesWithPrompt = new ArrayList<>(messages);
      messagesWithPrompt.addAll(templateMessages.messages());
      if (messagesWithPrompt.isEmpty()) {
        throw new IllegalStateException(
            "A prompt is required. Pass at least one message or configure a template with messages or a template reference.");
      }
      val messagesDto =
          messagesWithPrompt.stream()
              .map(msg -> ChatMessage.create().role(msg.type()).content(msg.content()))
              .toList();
      return TemplatingModuleConfig.create().template(messagesDto);
    }
    if (templateConfig instanceof Template.IdReference idReference) {
      val templateRef = TemplateRefByID.create().id(idReference.templateId());
      throw new NotImplementedError(
          "Template reference by ID is not yet implemented. Can't create DTO for object: "
              + templateRef);
    }
    if (templateConfig instanceof Template.NameReference nameReference) {
      val templateRef =
          TemplateRefByScenarioNameVersion.create()
              .scenario(nameReference.scenario())
              .name(nameReference.name())
              .version(nameReference.version());
      throw new NotImplementedError(
          "Template reference by name, version and scenario is not yet implemented. Can't create DTO for object: "
              + templateRef);
    }
    throw new NotImplementedError("Unknown template type: " + templateConfig);
  }
}
