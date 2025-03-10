/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/** The type of the tool call, in this case &#x60;function&#x60;. */
@Beta
public enum ToolCallType {
  FUNCTION("function"),

  UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

  private final String value;

  ToolCallType(String value) {
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
  public static ToolCallType fromValue(@Nonnull final String value) {
    for (final ToolCallType b : ToolCallType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return UNKNOWN_DEFAULT_OPEN_API;
  }
}
