package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class OrchestrationConfigDelegate<T extends OrchestrationConfig<T>>
    implements OrchestrationConfig<T> {

  @Nonnull private Option<LLMModuleConfig> llmConfig = Option.none();
  @Nonnull private Option<TemplatingModuleConfig> template = Option.none();
  @Nonnull private Option<DpiMaskingConfig> maskingConfig = Option.none();
  @Nonnull private Option<FilterConfig> inputContentFilter = Option.none();
  @Nonnull private Option<FilterConfig> outputContentFilter = Option.none();

  @Nonnull private final T wrapper;

  @Nonnull
  @Override
  public T withLlmConfig(@Nonnull final LLMModuleConfig llm) {
    this.llmConfig = Option.some(llm);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withTemplate(@Nonnull final TemplatingModuleConfig template) {
    this.template = Option.some(template);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withMaskingConfig(@Nonnull final DpiMaskingConfig maskingConfig) {
    this.maskingConfig = Option.some(maskingConfig);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withInputContentFilter(@Nonnull final FilterConfig filter) {
    this.inputContentFilter = Option.some(filter);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withOutputContentFilter(@Nonnull final FilterConfig filter) {
    this.outputContentFilter = Option.some(filter);
    return wrapper;
  }

  @Nonnull
  static <T extends OrchestrationConfig<T>> OrchestrationConfig<T> fromConfigAndDefaults(
      @Nonnull final OrchestrationConfig<?> config,
      @Nonnull final OrchestrationConfig<?> defaults,
      @Nonnull final T wrapperObject) {

    // TODO: discuss, should we set null instead, since the wrapper object is only needed when
    // mutating the config?
    var copy = new OrchestrationConfigDelegate<>(wrapperObject);

    copy.llmConfig = config.getLlmConfig().orElse(defaults::getLlmConfig);
    copy.template = config.getTemplate().orElse(defaults::getTemplate);
    copy.maskingConfig = config.getMaskingConfig().orElse(defaults::getMaskingConfig);
    copy.inputContentFilter =
        config.getInputContentFilter().orElse(defaults::getInputContentFilter);
    copy.outputContentFilter =
        config.getOutputContentFilter().orElse(defaults::getOutputContentFilter);

    return copy;
  }

  @Nonnull
  static ModuleConfigs toModuleConfigDTO(
      @Nonnull final OrchestrationConfig<?> config, @Nonnull final List<ChatMessage> messages) {
    LLMModuleConfig llm =
        config
            .getLlmConfig()
            .getOrElseThrow(() -> new IllegalStateException("LLM module config is required."));

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
          "A template is required. Pass at least one message or configure the templating module.");
    }
    TemplatingModuleConfig template = TemplatingModuleConfig.create().template(messagesWithPrompt);
    maybeTemplate.map(TemplatingModuleConfig::getDefaults).forEach(template::defaults);

    ModuleConfigs dto =
        ModuleConfigs.create().llmModuleConfig(llm).templatingModuleConfig(template);

    config
        .getMaskingConfig()
        .map(DpiMaskingConfig::toMaskingModuleDTO)
        .forEach(dto::maskingModuleConfig);

    var maybeInputFilter = config.getInputContentFilter();
    var maybeOutputFilter = config.getOutputContentFilter();

    if (maybeInputFilter.isDefined() || maybeOutputFilter.isDefined()) {
      var filter = FilteringModuleConfig.create();
      maybeInputFilter.map(it -> FilteringConfig.create().filters(it)).forEach(filter::input);
      maybeOutputFilter.map(it -> FilteringConfig.create().filters(it)).forEach(filter::output);
      dto = dto.filteringModuleConfig(filter);
    }

    return dto;
  }
}
