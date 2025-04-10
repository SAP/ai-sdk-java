/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.annotation.Nonnull;

/** The role of the author of the response message. */
public enum ChatCompletionResponseMessageRole {

  /** ASSISTANT option of this ChatCompletionResponseMessageRole */
  ASSISTANT("assistant"),

  /** UNKNOWN_DEFAULT_OPEN_API option of this ChatCompletionResponseMessageRole */
  UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

  private final String value;

  ChatCompletionResponseMessageRole(String value) {
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
  public static ChatCompletionResponseMessageRole fromValue(@Nonnull final String value) {
    for (final ChatCompletionResponseMessageRole b : ChatCompletionResponseMessageRole.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    return UNKNOWN_DEFAULT_OPEN_API;
  }
}
