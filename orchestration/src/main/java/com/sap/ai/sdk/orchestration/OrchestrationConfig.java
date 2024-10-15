package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import javax.annotation.Nonnull;

/**
 * Represents the configuration for the orchestration service. Allows for configuring the different
 * modules of the orchestration service via a fluent API.
 *
 * @param <T> Type of the specific implementation to make a fluent API possible.
 */
public interface OrchestrationConfig<T extends OrchestrationConfig<T>> {
  @Nonnull
  T instance();

  @Nonnull
  Option<LLMModuleConfig> getLlmConfig();

  @Nonnull
  Option<TemplatingModuleConfig> getTemplate();

  @Nonnull
  Option<DpiMaskingConfig> getMaskingConfig();

  @Nonnull
  Option<FilterConfig> getInputContentFilter();

  @Nonnull
  Option<FilterConfig> getOutputContentFilter();

  @Nonnull
  T withLlmConfig(@Nonnull final LLMModuleConfig llm);

  @Nonnull
  T withTemplate(@Nonnull final TemplatingModuleConfig template);

  @Nonnull
  T withMaskingConfig(@Nonnull final DpiMaskingConfig maskingConfig);

  @Nonnull
  T withInputContentFilter(@Nonnull final FilterConfig filter);

  @Nonnull
  T withOutputContentFilter(@Nonnull final FilterConfig filter);

  /**
   * Copy the configuration into the given target configuration. The copy is
   * <strong>shallow</strong> and does <strong>not override</strong> any existing configuration.
   *
   * <p>This has two main use cases:
   *
   * <ol>
   *   <li>Duplicating a config
   *   <li>Applying defaults to a config
   * </ol>
   *
   * @param source The source configuration to copy from.
   */
  default T copyFrom(@Nonnull final OrchestrationConfig<?> source) {
    getLlmConfig().orElse(source::getLlmConfig).forEach(this::withLlmConfig);
    getTemplate().orElse(source::getTemplate).forEach(this::withTemplate);
    getMaskingConfig().orElse(source::getMaskingConfig).forEach(this::withMaskingConfig);
    getInputContentFilter()
        .orElse(source::getInputContentFilter)
        .forEach(this::withInputContentFilter);
    getOutputContentFilter()
        .orElse(source::getOutputContentFilter)
        .forEach(this::withOutputContentFilter);
    return instance();
  }
}
