/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.annotation.Nonnull;

/** Gets or Sets AzureThreshold */
public enum AzureThreshold {
  NUMBER_0(0),

  NUMBER_2(2),

  NUMBER_4(4),

  NUMBER_6(6),

  NUMBER_unknown_default_open_api(11184809);

  private final Integer value;

  AzureThreshold(Integer value) {
    this.value = value;
  }

  /**
   * @return The enum value.
   */
  @JsonValue
  public Integer getValue() {
    return value;
  }

  /**
   * @return The String representation of the enum value.
   */
  @Override
  @Nonnull
  public String toString() {
    return String.valueOf(value);
  }

  /**
   * Converts the given value to its enum representation.
   *
   * @param value The input value.
   * @return The enum representation of the given value.
   */
  @JsonCreator
  public static AzureThreshold fromValue(@Nonnull final Integer value) {
    for (final AzureThreshold b : AzureThreshold.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return NUMBER_unknown_default_open_api;
  }
}
