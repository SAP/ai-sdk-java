package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.LLMModelDetails;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An {@link OrchestrationModuleConfig} that carries an {@link OrchestrationTemplateReference}. The
 * template reference is the only source of prompt input — no free-form messages are accepted.
 * Obtain instances via {@link
 * OrchestrationModuleConfig#withTemplateConfig(OrchestrationTemplateReference)}.
 *
 * @since 1.26.0
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OrchestrationModuleConfigWithRef {

  @Getter(AccessLevel.PACKAGE)
  @Nonnull
  private final OrchestrationModuleConfig inner;

  @Getter(AccessLevel.PACKAGE)
  @Nonnull
  private final OrchestrationTemplateReference templateRef;

  /**
   * Creates a new configuration with the given LLM configuration.
   *
   * @param llm The LLM configuration to use.
   * @return A new configuration with the given LLM configuration.
   * @see OrchestrationModuleConfig#withLlmConfig(LLMModelDetails)
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public OrchestrationModuleConfigWithRef withLlmConfig(@Nonnull final LLMModelDetails llm) {
    return new OrchestrationModuleConfigWithRef(inner.withLlmConfig(llm), templateRef);
  }

  /**
   * Creates a new configuration with the given LLM configuration.
   *
   * @param model The LLM configuration to use.
   * @return A new configuration with the given LLM configuration.
   * @see OrchestrationModuleConfig#withLlmConfig(OrchestrationAiModel)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withLlmConfig(@Nonnull final OrchestrationAiModel model) {
    return new OrchestrationModuleConfigWithRef(inner.withLlmConfig(model), templateRef);
  }

  /**
   * Creates a new configuration with the given Data Masking configuration.
   *
   * @param maskingProvider The Data Masking configuration to use.
   * @param maskingProviders Additional Data Masking configurations to use.
   * @return A new configuration with the given Data Masking configuration.
   * @see OrchestrationModuleConfig#withMaskingConfig(MaskingProvider, MaskingProvider...)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withMaskingConfig(
      @Nonnull final MaskingProvider maskingProvider,
      @Nonnull final MaskingProvider... maskingProviders) {
    return new OrchestrationModuleConfigWithRef(
        inner.withMaskingConfig(maskingProvider, maskingProviders), templateRef);
  }

  /**
   * Adds input content filters to the configuration.
   *
   * @param contentFilter A filter to apply to the input.
   * @param contentFilters Zero or more additional content filters to apply to the input.
   * @return A new configuration with the specified input filters added.
   * @see OrchestrationModuleConfig#withInputFiltering(ContentFilter, ContentFilter...)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withInputFiltering(
      @Nonnull final ContentFilter contentFilter, @Nonnull final ContentFilter... contentFilters) {
    return new OrchestrationModuleConfigWithRef(
        inner.withInputFiltering(contentFilter, contentFilters), templateRef);
  }

  /**
   * Adds output content filters to the configuration.
   *
   * @param contentFilter A filter to apply to the output.
   * @param contentFilters Zero or more additional content filters to apply to the output.
   * @return A new configuration with the specified output filters added.
   * @see OrchestrationModuleConfig#withOutputFiltering(ContentFilter, ContentFilter...)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withOutputFiltering(
      @Nonnull final ContentFilter contentFilter, @Nonnull final ContentFilter... contentFilters) {
    return new OrchestrationModuleConfigWithRef(
        inner.withOutputFiltering(contentFilter, contentFilters), templateRef);
  }

  /**
   * Creates a new configuration with the given grounding configuration.
   *
   * @param groundingProvider The grounding configuration to use.
   * @return A new configuration with the given grounding configuration.
   * @see OrchestrationModuleConfig#withGrounding(GroundingProvider)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withGrounding(
      @Nonnull final GroundingProvider groundingProvider) {
    return new OrchestrationModuleConfigWithRef(
        inner.withGrounding(groundingProvider), templateRef);
  }

  /**
   * Configure input translation using a high-level TranslationConfig.
   *
   * @param translationConfig The translation configuration.
   * @return A new configuration with input translation configured.
   * @see OrchestrationModuleConfig#withInputTranslationConfig(TranslationConfig.Input)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withInputTranslationConfig(
      @Nonnull final TranslationConfig.Input translationConfig) {
    return new OrchestrationModuleConfigWithRef(
        inner.withInputTranslationConfig(translationConfig), templateRef);
  }

  /**
   * Configure output translation using a high-level TranslationConfig.
   *
   * @param translationConfig The translation configuration.
   * @return A new configuration with output translation configured.
   * @see OrchestrationModuleConfig#withOutputTranslationConfig(TranslationConfig.Output)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withOutputTranslationConfig(
      @Nonnull final TranslationConfig.Output translationConfig) {
    return new OrchestrationModuleConfigWithRef(
        inner.withOutputTranslationConfig(translationConfig), templateRef);
  }

  /**
   * Creates a new configuration with the given stream configuration.
   *
   * @param config The stream configuration to use.
   * @return A new configuration with the given stream configuration.
   * @see OrchestrationModuleConfig#withStreamConfig(OrchestrationStreamConfig)
   */
  @Nonnull
  public OrchestrationModuleConfigWithRef withStreamConfig(
      @Nonnull final OrchestrationStreamConfig config) {
    return new OrchestrationModuleConfigWithRef(inner.withStreamConfig(config), templateRef);
  }
}
