package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.TemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import com.sap.ai.sdk.orchestration.model.TemplateRefTemplateRef;
import java.util.Map;
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
@With
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PromptTemplateReference extends PromptTemplate {
  @SuppressWarnings("PMD.LombokGetterSetterExposesModelType")
  @Nonnull
  @With(AccessLevel.PRIVATE)
  TemplateRefTemplateRef reference;

  /** The scope of the template reference. */
  @Nonnull ScopeEnum scope;

  @Nonnull Map<String, String> inputParameters;

  /**
   * Create a low-level representation of the template.
   *
   * @return the low-level representation of the template.
   */
  @Nonnull
  @Override
  protected PromptTemplatingModuleConfigPrompt toLowLevel() {
    if (reference instanceof TemplateRefByID idRef) {
      final var valueById = TemplateRefByID.ScopeEnum.valueOf(scope.name());
      return TemplateRef.create()
          .templateRef(TemplateRefByID.create().id(idRef.getId()).scope(valueById));
    } else if (reference instanceof TemplateRefByScenarioNameVersion scenarioRef) {
      final var valueByScenario = TemplateRefByScenarioNameVersion.ScopeEnum.valueOf(scope.name());
      return TemplateRef.create()
          .templateRef(
              TemplateRefByScenarioNameVersion.create()
                  .scenario(scenarioRef.getScenario())
                  .name(scenarioRef.getName())
                  .version(scenarioRef.getVersion())
                  .scope(valueByScenario));
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
