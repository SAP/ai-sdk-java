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

package com.sap.ai.sdk.foundationmodels.openai.model2;

import java.util.List;

/** Up to 4 sequences where the API will stop generating further tokens. */
@com.google.common.annotations.Beta
public interface CreateChatCompletionRequestAllOfStop {
  /**
   * Creator to enable deserialization of a String.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerString}.
   */
  @com.fasterxml.jackson.annotation.JsonCreator
  static InnerString create(String val) {
    return new InnerString(val);
  }

  /**
   * Creator to enable deserialization of a list of String.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerStrings}.
   */
  @com.fasterxml.jackson.annotation.JsonCreator
  static InnerStrings create(List<String> val) {
    return new InnerStrings(val);
  }

  /**
   * Helper class to create a String that implements {@link CreateChatCompletionRequestAllOfStop}.
   */
  record InnerString(@com.fasterxml.jackson.annotation.JsonValue String value)
      implements CreateChatCompletionRequestAllOfStop {}

  /**
   * Helper class to create a list of String that implements {@link
   * CreateChatCompletionRequestAllOfStop}.
   */
  record InnerStrings(@com.fasterxml.jackson.annotation.JsonValue List<String> values)
      implements CreateChatCompletionRequestAllOfStop {}
}
