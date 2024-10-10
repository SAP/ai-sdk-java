package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class OrchestrationConfig implements OrchestrationConfigBuilder<OrchestrationConfig> {

  @Nullable private LLMModuleConfig llmConfig;
  @Nullable private TemplatingModuleConfig template;
  @Nullable private MaskingModuleConfig maskingConfig;

  @Nonnull
  @Override
  public OrchestrationConfig withLlmConfig(LLMModuleConfig llm) {
    this.llmConfig = llm;
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationConfig withTemplate(TemplatingModuleConfig template) {
    this.template = template;
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationConfig withMaskingConfig(MaskingModuleConfig maskingConfig) {
    this.maskingConfig = maskingConfig;
    return this;
  }

  OrchestrationConfig mergeWithDefaults(@Nullable final OrchestrationConfig defaults) {
    if (defaults == null) {
      return this;
    }
    return new OrchestrationConfig(
        llmConfig != null ? llmConfig : defaults.getLlmConfig(),
        template != null ? template : defaults.getTemplate(),
        maskingConfig != null ? maskingConfig : defaults.getMaskingConfig());
  }

  ModuleConfigs toDTO() {
    return ModuleConfigs.create()
        .llmModuleConfig(llmConfig)
        .templatingModuleConfig(template)
        .maskingModuleConfig(maskingConfig);
  }
}
