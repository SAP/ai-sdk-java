package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

/**
 * Default implementation of the {@link
 * com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig} interface. This class supports
 * delegation from other classes by accepting a wrapper instance.
 */
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class OrchestrationConfig {
  @Nullable AiModel llmConfig;
  @Nullable TemplateConfig template;
  @Nullable MaskingConfig maskingConfig;
  @Nullable ContentFilter inputContentFilter;
  @Nullable ContentFilter outputContentFilter;
}
