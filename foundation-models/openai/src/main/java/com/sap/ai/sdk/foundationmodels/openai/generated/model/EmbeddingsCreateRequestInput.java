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

import java.util.List;

/**
 * Input text to get embeddings for, encoded as a string. To get embeddings for multiple inputs in a
 * single request, pass an array of strings. Each input must not exceed 2048 tokens in length.
 * Unless you are embedding code, we suggest replacing newlines (\\n) in your input with a single
 * space, as we have observed inferior results when newlines are present.
 */
@com.google.common.annotations.Beta
public interface EmbeddingsCreateRequestInput {
  /** Helper class to create a String that implements {@link EmbeddingsCreateRequestInput}. */
  class InnerString implements EmbeddingsCreateRequestInput {
    @com.fasterxml.jackson.annotation.JsonValue String value;

    InnerString(String value) {
      this.value = value;
    }
  }

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
   * Helper class to create a list of String that implements {@link EmbeddingsCreateRequestInput}.
   */
  record InnerStrings(@com.fasterxml.jackson.annotation.JsonValue List<String> values)
      implements EmbeddingsCreateRequestInput {}

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
}
