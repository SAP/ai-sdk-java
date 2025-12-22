package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Class representing a reference to an Orchestration config stored in prompt registry.
 *
 * @since 1.14.0
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public class OrchestrationConfigReference {
  String id;
  String scenario;
  String name;
  String version;

  /**
   * Build a reference from an ID.
   *
   * @param id The id of the reference
   * @return A reference object with the specified id
   * @since 1.14.0
   */
  @Nonnull
  public static OrchestrationConfigReference fromId(@Nonnull final String id) {
    return new OrchestrationConfigReference(id, null, null, null);
  }

  /**
   * Build a reference from a scenario, name, and version.
   *
   * @param scenario The scenario of the reference
   * @return A builder object with the specified scenario
   * @since 1.14.0
   */
  @Nonnull
  public static Builder fromScenario(@Nonnull final String scenario) {
    return new Builder(scenario);
  }

  /**
   * Builder to create an Orchestration config reference from scenario, name, and version.
   *
   * @since 1.14.0
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {
    private final String scenario;

    /**
     * Build a reference from a scenario, name, and version.
     *
     * @param name The name of the reference
     * @return A builder object with the specified scenario and name
     * @since 1.14.0
     */
    @Nonnull
    public Builder1 name(@Nonnull final String name) {
      return new Builder1(scenario, name);
    }
  }

  /**
   * Builder to create an Orchestration config reference from scenario, name, and version.
   *
   * @since 1.14.0
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder1 {
    private final String scenario;
    private final String name;

    /**
     * Build a reference from a scenario, name, and version.
     *
     * @param version The version of the reference
     * @return A reference object with the specified scenario, name, and version
     * @since 1.14.0
     */
    @Nonnull
    public OrchestrationConfigReference version(@Nonnull final String version) {
      return new OrchestrationConfigReference(null, scenario, name, version);
    }
  }
}
