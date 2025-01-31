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

/** The contents of the system message. */
@com.google.common.annotations.Beta
public interface ChatCompletionRequestSystemMessageContent {
  /**
   * Helper class to create a String that implements {@link
   * ChatCompletionRequestSystemMessageContent}.
   */
  record InnerString(@com.fasterxml.jackson.annotation.JsonValue String value)
      implements ChatCompletionRequestSystemMessageContent {}

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
   * Helper class to create a list of ChatCompletionRequestSystemMessageContentPart that implements
   * {@link ChatCompletionRequestSystemMessageContent}.
   */
  record InnerChatCompletionRequestSystemMessageContentParts(
      @com.fasterxml.jackson.annotation.JsonValue
          List<ChatCompletionRequestSystemMessageContentPart> values)
      implements ChatCompletionRequestSystemMessageContent {}

  /**
   * Creator to enable deserialization of a list of ChatCompletionRequestSystemMessageContentPart.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerChatCompletionRequestSystemMessageContentParts}.
   */
  @com.fasterxml.jackson.annotation.JsonCreator
  static InnerChatCompletionRequestSystemMessageContentParts create(
      List<ChatCompletionRequestSystemMessageContentPart> val) {
    return new InnerChatCompletionRequestSystemMessageContentParts(val);
  }
}
