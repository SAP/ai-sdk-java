package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
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
  Option<AiModel> getLlmConfig();

  @Nonnull
  Option<TemplatingModuleConfig> getTemplate();

  @Nonnull
  Option<MaskingConfig> getMaskingConfig();

  @Nonnull
  Option<ContentFilter> getInputContentFilter();

  @Nonnull
  Option<ContentFilter> getOutputContentFilter();

  @Nonnull
  T withLlmConfig(@Nonnull final AiModel llm);

  @Nonnull
  T withTemplate(@Nonnull final TemplatingModuleConfig template);

  @Nonnull
  T withMaskingConfig(@Nonnull final MaskingConfig maskingConfig);

  @Nonnull
  T withInputContentFilter(@Nonnull final ContentFilter filter);

  @Nonnull
  T withOutputContentFilter(@Nonnull final ContentFilter filter);
}
