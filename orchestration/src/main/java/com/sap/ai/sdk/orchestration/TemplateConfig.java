package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import javax.annotation.Nonnull;

/**
 * Template configuration for the {@link OrchestrationModuleConfig}.
 *
 * @since 1.5.0
 */
public abstract class TemplateConfig {

  /**
   * Create a low-level representation of the template.
   *
   * @return The low-level representation of the template.
   */
  @Nonnull
  protected abstract TemplatingModuleConfig toLowLevel();

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
   * Build a template reference.
   *
   * @return An intermediate object to build the template reference.
   */
  @Nonnull
  public static ReferenceBuilder reference() {
    return new ReferenceBuilder();
  }

  /** Intermediate object to build a template reference. */
  public static class ReferenceBuilder {
    /**
     * Build a template reference with the given id.
     *
     * @param id The id of the template.
     * @return A template reference with the given id.
     */
    @Nonnull
    public OrchestrationTemplateReference byId(@Nonnull final String id) {
      return new OrchestrationTemplateReference(TemplateRefByID.create().id(id));
    }

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param scenario The scenario of the template.
     * @return An intermediate object to build the template reference.
     */
    @Nonnull
    public ReferenceBuilder1 byScenarioNameVersion(@Nonnull final String scenario) {
      return new ReferenceBuilder1(scenario);
    }
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  public static class ReferenceBuilder1 {
    @Nonnull private final String scenario;

    private ReferenceBuilder1(@Nonnull final String scenario) {
      this.scenario = scenario;
    }

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param name The name of the template.
     * @return An intermediate object to build the template reference.
     */
    @Nonnull
    public ReferenceBuilder2 name(@Nonnull final String name) {
      return new ReferenceBuilder2(scenario, name);
    }
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  public static class ReferenceBuilder2 {
    @Nonnull private final String scenario;
    @Nonnull private final String name;

    private ReferenceBuilder2(@Nonnull final String scenario, @Nonnull final String name) {
      this.scenario = scenario;
      this.name = name;
    }

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param version The version of the template.
     * @return A template reference with the given scenario, name, and version.
     */
    @Nonnull
    public OrchestrationTemplateReference version(@Nonnull final String version) {
      return new OrchestrationTemplateReference(
          TemplateRefByScenarioNameVersion.create().scenario(scenario).name(name).version(version));
    }
  }
}
