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

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.annotation.Nonnull;

/** Default entities supported by data privacy and integration service */
public enum DPIEntities {
  PERSON("profile-person"),

  ORG("profile-org"),

  EMAIL("profile-email"),

  PHONE("profile-phone"),

  ADDRESS("profile-address"),

  SAPIDS_INTERNAL("profile-sapids-internal"),

  SAPIDS_PUBLIC("profile-sapids-public"),

  URL("profile-url"),

  IBAN("profile-iban"),

  CREDIT_CARD_NUMBER("profile-credit-card-number"),

  PASSPORT("profile-passport"),

  DRIVERLICENSE("profile-driverlicense"),

  NATIONALITY("profile-nationality"),

  POLITICAL_GROUP("profile-political-group"),

  ETHNICITY("profile-ethnicity");

  private final String value;

  DPIEntities(String value) {
    this.value = value;
  }

  /**
   * @return The enum value.
   */
  @JsonValue
  public String getValue() {
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
  public static DPIEntities fromValue(@Nonnull final String value) {
    for (final DPIEntities b : DPIEntities.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
