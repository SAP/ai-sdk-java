package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;
import lombok.val;

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
 */
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class OrchestrationModuleConfig {
  /**
   * The configured language model settings. This configuration is required when executing requests.
   */
  @Nullable LLMModuleConfig llmConfig;

  /**
   * A template to be populated with input parameters. Upon request execution, this template will be
   * enhanced with any messages and parameter values from {@link OrchestrationPrompt}.
   */
  @Nullable TemplatingModuleConfig templateConfig;

  /** A masking configuration to pseudonymous or anonymize sensitive data in the input. */
  @Nullable MaskingModuleConfig maskingConfig;

  /** A content filter to filter the prompt. */
  @Nullable FilteringModuleConfig filteringConfig;

  /**
   * Creates a new configuration with the given LLM configuration.
   *
   * @param aiModel The LLM configuration to use.
   * @return A new configuration with the given LLM configuration.
   */
  @Tolerate
  @Nonnull
  public OrchestrationModuleConfig withLlmConfig(@Nonnull final OrchestrationAiModel aiModel) {
    return withLlmConfig(aiModel.createConfig());
  }

  /**
   * Creates a new configuration with the given Data Masking configuration.
   *
   * @param maskingProvider The Data Masking configuration to use.
   * @param maskingProviders Additional Data Masking configurations to use.
   * @return A new configuration with the given Data Masking configuration.
   */
  @Tolerate
  @Nonnull
  public OrchestrationModuleConfig withMaskingConfig(
      @Nonnull final MaskingProvider maskingProvider,
      @Nonnull final MaskingProvider... maskingProviders) {
    val newMaskingConfig =
        MaskingModuleConfig.create().maskingProviders(maskingProvider.createConfig());
    Arrays.stream(maskingProviders)
        .forEach(it -> newMaskingConfig.addMaskingProvidersItem(it.createConfig()));

    return withMaskingConfig(newMaskingConfig);
  }

  /**
   * Adds input content filters to the orchestration configuration.
   *
   * <p>Preferred over {@link #withFilteringConfig(FilteringModuleConfig)} for adding input filters.
   *
   * @param contentFilters one or more content filters to apply to the input.
   * @return a new {@code OrchestrationModuleConfig} instance with the specified input filters
   *     added.
   */
  @Nonnull
  public OrchestrationModuleConfig withInputFiltering(
      @Nonnull final ContentFilter contentFilter, @Nonnull final ContentFilter... contentFilters) {

    final var allFilters = new ArrayList<ContentFilter>();
    allFilters.add(contentFilter);
    allFilters.addAll(Arrays.asList(contentFilters));

    final var filterConfigs =
        allFilters.stream().filter(Objects::nonNull).map(ContentFilter::createConfig).toList();

    final var inputFilter = InputFilteringConfig.create().filters(filterConfigs);

    final var newFilteringConfig =
        FilteringModuleConfig.create()
            .input(inputFilter)
            .output(this.filteringConfig != null ? this.filteringConfig.getOutput() : null);

    return this.withFilteringConfig(newFilteringConfig);
  }

  /**
   * Adds output content filters to the orchestration configuration.
   *
   * <p>Preferred over {@link #withFilteringConfig(FilteringModuleConfig)} for adding output
   * filters.
   *
   * @param contentFilters one or more content filters to apply to the output.
   * @return a new {@code OrchestrationModuleConfig} instance with the specified output filters
   *     added.
   */
  @Nonnull
  public OrchestrationModuleConfig withOutputFiltering(
      @Nonnull final ContentFilter contentFilter, @Nonnull final ContentFilter... contentFilters) {

    final var allFilters = new ArrayList<ContentFilter>();
    allFilters.add(contentFilter);
    allFilters.addAll(Arrays.asList(contentFilters));

    final var filterConfigs =
        allFilters.stream().filter(Objects::nonNull).map(ContentFilter::createConfig).toList();

    final var outputFilter = OutputFilteringConfig.create().filters(filterConfigs);

    final var newFilteringConfig =
        FilteringModuleConfig.create()
            .output(outputFilter)
            .input(this.filteringConfig != null ? this.filteringConfig.getInput() : null);

    return this.withFilteringConfig(newFilteringConfig);
  }
}
