package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.FilteringModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;

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
   * @param dpiMasking The Data Masking configuration to use.
   * @return A new configuration with the given Data Masking configuration.
   */
  @Tolerate
  @Nonnull
  public OrchestrationModuleConfig withDpiMaskingConfig(
      @Nonnull final DpiMaskingConfig dpiMasking) {
    return withMaskingConfig(dpiMasking.createConfig());
  }
}
