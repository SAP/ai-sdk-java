package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafetyFilterConfig;
import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.InputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.OutputFilteringConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class OrchestrationModuleConfig {
  /**
   * The configured language model settings. This configuration is required when executing requests.
   */
  @With @Nullable LLMModuleConfig llmConfig;

  /**
   * A template to be populated with input parameters. Upon request execution, this template will be
   * enhanced with any messages and parameter values from {@link OrchestrationPrompt}.
   */
  @With @Nullable TemplatingModuleConfig templateConfig;

  /** A masking configuration to pseudonymous or anonymize sensitive data in the input. */
  @With @Nullable MaskingModuleConfig maskingConfig;

  /** A content filter to filter the prompt. */
  @With(AccessLevel.PRIVATE)
  @Nullable
  FilteringModuleConfig filteringConfig;

  @Nonnull
  public OrchestrationModuleConfig withInputFiltering(AzureContentSafety contentFilter) {
    var azureFilter =
        new AzureContentSafetyFilterConfig()
            .type(AzureContentSafetyFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
            .config(contentFilter);
    var inputFilters = new InputFilteringConfig().filters(List.of(azureFilter));

    var newFilteringConfig =
        new FilteringModuleConfig()
            .input(inputFilters)
            .output(this.filteringConfig != null ? this.filteringConfig.getOutput() : null);

    return this.withFilteringConfig(newFilteringConfig);
  }

  @Nonnull
  public OrchestrationModuleConfig withOutputFiltering(AzureContentSafety safety) {
    var filter =
        new AzureContentSafetyFilterConfig()
            .type(AzureContentSafetyFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
            .config(safety);
    var outputFilteringConfig = new OutputFilteringConfig().filters(List.of(filter));

    var newFilteringConfig =
        new FilteringModuleConfig()
            .output(outputFilteringConfig)
            .input(this.filteringConfig != null ? this.filteringConfig.getInput() : null);

    return this.withFilteringConfig(newFilteringConfig);
  }
}
