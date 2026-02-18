package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import com.sap.ai.sdk.orchestration.model.TemplateRefTemplateRef;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

/**
 * A reference to a template to use in {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode(callSuper = true)
@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrchestrationTemplateReference extends TemplateConfig {
  @Nonnull TemplateRefTemplateRef reference;

  /** The scope of the template reference. */
  @With @Nonnull ScopeEnum scope;

  /**
   * Create a low-level representation of the template.
   *
   * @return The low-level representation of the template.
   */
  @Nonnull
  @Override
  protected PromptTemplatingModuleConfigPrompt toLowLevel() {
    if (reference instanceof TemplateRefByID idRef) {
      final var valueById = TemplateRefByID.ScopeEnum.valueOf(scope.name());
      idRef.setScope(valueById);
      return TemplateRef.create().templateRef(idRef);
    } else if (reference instanceof TemplateRefByScenarioNameVersion scenarioRef) {
      final var valueByScenario = TemplateRefByScenarioNameVersion.ScopeEnum.valueOf(scope.name());
      scenarioRef.setScope(valueByScenario);
      return TemplateRef.create().templateRef(scenarioRef);
    } else {
      throw new IllegalStateException(
          "Unsupported template reference type: " + reference.getClass());
    }
  }

  /** Enum representing the scope of the template reference. */
  public enum ScopeEnum {
    /** Template is resolved within the current tenant scope. */
    TENANT,
    /** Template is resolved within the configured resource group scope. */
    RESOURCE_GROUP
  }
}
