package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafetyFilterConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;

import javax.annotation.Nonnull;

public interface OrchestrationConfigBuilder<T extends OrchestrationConfigBuilder<T>> {

  @Nonnull T withLlmConfig(LLMModuleConfig llm);

  @Nonnull T withTemplate(TemplatingModuleConfig template);

  @Nonnull T withMaskingConfig(MaskingModuleConfig maskingConfig);

}
