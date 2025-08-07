package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplateRefTemplateRef;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * A reference to a template to use in {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Beta
public class OrchestrationTemplateReference extends TemplateConfig {
  @Nonnull TemplateRefTemplateRef reference;

  /**
   * Create a low-level representation of the template.
   *
   * @return The low-level representation of the template.
   */
  @Nonnull
  @Override
  protected PromptTemplatingModuleConfigPrompt toLowLevel() {
    return TemplateRef.create().templateRef(reference);
  }
}
