/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.36.1
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

/**
 * Threshold for the filter. Setting it to &#x60;0&#x60; blocks content with low severity, whereas
 * &#x60;6&#x60; is the most permissive and blocks only the highest severity
 */
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