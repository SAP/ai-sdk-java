package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.TemplateRefTemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import lombok.Value;

@Value
public class OrchestrationTemplateReference extends TemplateConfig{
  TemplateRefTemplateRef reference;

  public OrchestrationTemplateReference(TemplateRefTemplateRef reference) {
    this.reference = reference;
  }

  @Override
  protected TemplatingModuleConfig toLowLevel() {
    return null;
  }
}
