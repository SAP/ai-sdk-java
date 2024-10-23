package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import io.vavr.control.Option;
import javax.annotation.Nonnull;

/**
 * Represents the configuration for the orchestration service. Allows for configuring the different
 * modules of the orchestration service via a fluent API.
 *
 * <p>The orchestration pipeline combines different modules into a single execution flow where the
 * output of one module serves as input for the next. The pipeline consists of the following
 * modules:
 *
 * <ul>
 *   <li>LLM Config (Mandatory)
 *   <li>Templating (Optional)
 *   <li>Data Masking (Optional)
 *   <li>Input Content Filtering (Optional)
 *   <li>Output Content Filtering (Optional)
 * </ul>
 *
 * @param <T> Type of the specific implementation to make a fluent API possible.
 */
public interface OrchestrationConfig<T extends OrchestrationConfig<T>> {

  /**
   * The configured language model settings. This configuration is required when executing requests.
   *
   * @return An Option containing the LLM configuration if set, empty otherwise
   * @see AiModel
   * @see LlmConfig
   */
  @Nonnull
  Option<AiModel> getLlmConfig();

  /**
   * A template to be populated with input parameters. Upon request execution, this template will be
   * enhanced with any messages from {@link OrchestrationPrompt}.
   *
   * @return An Option containing the template configuration if set, empty otherwise
   * @see TemplateConfig
   * @see OrchestrationPrompt
   */
  @Nonnull
  Option<TemplateConfig> getTemplate();

  /**
   * The masking configuration.
   *
   * @return An Option containing the masking configuration if set, empty otherwise
   * @see MaskingConfig
   * @see DpiMaskingConfig
   */
  @Nonnull
  Option<MaskingConfig> getMaskingConfig();

  /**
   * The input content filter.
   *
   * @return An Option containing the input filter configuration if set, empty otherwise
   * @see #getOutputContentFilter()
   * @see ContentFilter
   * @see AzureContentFilter
   */
  @Nonnull
  Option<ContentFilter> getInputContentFilter();

  /**
   * The output content filter.
   *
   * @return An Option containing the output filter configuration if set, empty otherwise
   * @see #getInputContentFilter()
   * @see ContentFilter
   * @see AzureContentFilter
   */
  @Nonnull
  Option<ContentFilter> getOutputContentFilter();

  /**
   * Set the language model configuration.
   *
   * @param llm The LLM configuration to be used
   * @return Fluent API object of this config instance
   * @see AiModel
   * @see LlmConfig
   */
  @Nonnull
  T withLlmConfig(@Nonnull final AiModel llm);

  /**
   * Set the template configuration for prompt composition.
   *
   * @param templateConfig The template configuration to be used
   * @return Fluent API object of this config instance
   * @see TemplateConfig
   * @see OrchestrationPrompt
   */
  @Nonnull
  T withTemplate(@Nonnull final TemplateConfig templateConfig);

  /**
   * Set a data masking configuration.
   *
   * @param maskingConfig The masking configuration to be used
   * @return Fluent API object of this config instance
   * @see MaskingConfig
   * @see DpiMaskingConfig
   */
  @Nonnull
  T withMaskingConfig(@Nonnull final MaskingConfig maskingConfig);

  /**
   * Configure an input content filter.
   *
   * @param filter The content filter configuration to be applied to input
   * @return Fluent API object of this config instance
   * @see #withOutputContentFilter(ContentFilter)
   * @see ContentFilter
   * @see AzureContentFilter
   */
  @Nonnull
  T withInputContentFilter(@Nonnull final ContentFilter filter);

  /**
   * Configure an output content filter.
   *
   * @param filter The content filter configuration to be applied to output
   * @return Fluent API object of this config instance
   * @see #withInputContentFilter(ContentFilter)
   * @see ContentFilter
   * @see AzureContentFilter
   */
  @Nonnull
  T withOutputContentFilter(@Nonnull final ContentFilter filter);
}
