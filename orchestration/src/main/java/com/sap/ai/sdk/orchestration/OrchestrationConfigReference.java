package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.annotation.Nonnull;

// JONAS: Making this a sealed interface permitting 2 classes for ID and SNV seemed like an overkill
// JONAS: Can I make this a record class? (Probably not because I do not want an all-args constructor)
// JONAS: Use @VALUE?

//@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public class OrchestrationConfigReference {
  private String id;
  private String name;
  private String scenario;
  private String version;

  public static OrchestrationConfigReference fromId(@Nonnull final String id) {
    return new OrchestrationConfigReference(id, null, null, null);
  }

  public static OrchestrationConfigReference fromScenarioNameVersion(@Nonnull final String scenario, @Nonnull final String name, @Nonnull final String version) {
    return new OrchestrationConfigReference(null, scenario, name, version);
  }

  String getId() {
    return id;
  }

  String getName() {
    return name;
  }

  String getScenario() {
    return scenario;
  }

  String getVersion() {
    return version;
  }
}
