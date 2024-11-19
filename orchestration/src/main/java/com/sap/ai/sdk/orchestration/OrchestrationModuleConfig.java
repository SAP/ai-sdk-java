package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

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
      @Nonnull final ContentFilter... contentFilters) {

    var filterConfigs = Arrays.stream(contentFilters).map(ContentFilter::toSerializable).toList();

    var inputFilter = new InputFilteringConfig().filters(filterConfigs);

    var newFilteringConfig =
        new FilteringModuleConfig()
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
      @Nonnull final ContentFilter... contentFilters) {

    var filterConfigs = Arrays.stream(contentFilters).map(ContentFilter::toSerializable).toList();
    var outputFilter = new OutputFilteringConfig().filters(filterConfigs);

    var newFilteringConfig =
        new FilteringModuleConfig()
            .output(outputFilter)
            .input(this.filteringConfig != null ? this.filteringConfig.getInput() : null);

    return this.withFilteringConfig(newFilteringConfig);
  }
}
