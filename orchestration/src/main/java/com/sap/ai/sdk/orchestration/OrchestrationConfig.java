package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
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
public class OrchestrationConfig {
  /**
   * The configured language model settings. This configuration is required when executing requests.
   */
  @Nullable AiModel llmConfig;

  /**
   * A template to be populated with input parameters. Upon request execution, this template will be
   * enhanced with any messages and parameter values from {@link OrchestrationPrompt}.
   */
  @Nullable TemplateConfig template;

  /**
   * A masking configuration to pseudonymous or anonymize sensitive data in the input.
   */
  @Nullable MaskingConfig maskingConfig;

  /**
   * A content filter to filter the prompt.
   */
  @Nullable ContentFilter inputContentFilter;

  /**
   * A content filter to filter the language model response.
   */
  @Nullable ContentFilter outputContentFilter;
}
