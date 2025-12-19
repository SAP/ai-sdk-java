package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

// JONAS: Making this a sealed interface permitting 2 classes for ID and SNV seemed like an overkill
// JONAS: Not a record because I do not want a public all-args constructor
// JONAS: Is string correct for ID? (Use UUID?)
// JONAS: Split SNV into 3 methods?

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Beta
public class OrchestrationConfigReference {
  String id;
  String scenario;
  String name;
  String version;

  public static OrchestrationConfigReference fromId(@Nonnull final String id) {
    return new OrchestrationConfigReference(id, null, null, null);
  }

  public static OrchestrationConfigReference fromScenarioNameVersion(
      @Nonnull final String scenario, @Nonnull final String name, @Nonnull final String version) {
    return new OrchestrationConfigReference(null, scenario, name, version);
  }
}
