package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationTemplateReference.ScopeEnum.TENANT;

import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;

/**
 * Template configuration for the {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode
public abstract class TemplateConfig {

  /**
   * Create a low-level representation of the template.
   *
   * @return The low-level representation of the template.
   */
  @Nonnull
  protected abstract PromptTemplatingModuleConfigPrompt toLowLevel();

  /**
   * Build a template.
   *
   * @return A new empty template.
   */
  @Nonnull
  public static OrchestrationTemplate create() {
    return new OrchestrationTemplate();
  }

  /**
   * Build a template reference with tenant level scope.
   *
   * @return An intermediate object to build the template reference.
   */
  @Nonnull
  public static ReferenceBuilder reference() {
    final var templ = TemplateRefByScenarioNameVersion.create();

    return scenario ->
        name ->
            version ->
                new OrchestrationTemplateReference(
                    templ.scenario(scenario).name(name).version(version), TENANT);
  }

  /** Intermediate object to build a template reference. */
  @FunctionalInterface
  public interface ReferenceBuilder {
    /**
     * Build a template reference with the given id for tenant scope.
     *
     * @param id The id of the template.
     * @return A template reference with the given id.
     */
    @Nonnull
    default OrchestrationTemplateReference byId(@Nonnull final String id) {
      return new OrchestrationTemplateReference(TemplateRefByID.create().id(id), TENANT);
    }

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param scenario The scenario of the template.
     * @return An intermediate object to build the template reference.
     */
    @Nonnull
    ReferenceBuilder1 byScenario(@Nonnull final String scenario);
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  @FunctionalInterface
  public interface ReferenceBuilder1 {

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param name The name of the template.
     * @return An intermediate object to build the template reference.
     */
    @Nonnull
    ReferenceBuilder2 name(@Nonnull final String name);
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  @FunctionalInterface
  public interface ReferenceBuilder2 {
    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param version The version of the template.
     * @return A template reference with the given scenario, name, and version.
     */
    @Nonnull
    OrchestrationTemplateReference version(@Nonnull final String version);
  }
}
