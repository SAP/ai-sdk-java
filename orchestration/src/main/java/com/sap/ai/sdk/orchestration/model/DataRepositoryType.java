/*
 * Internal Orchestration Service API
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
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

/** Only include DataRepositories with the given type. */
public enum DataRepositoryType {
  VECTOR("vector"),

  HELP_SAP_COM("help.sap.com"),

  UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

  private final String value;

  DataRepositoryType(String value) {
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
  public static DataRepositoryType fromValue(@Nonnull final String value) {
    for (final DataRepositoryType b : DataRepositoryType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return UNKNOWN_DEFAULT_OPEN_API;
  }
}
