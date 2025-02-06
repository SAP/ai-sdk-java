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

/**
 * The contents of the assistant message. Required unless &#x60;tool_calls&#x60; or
 * &#x60;function_call&#x60; is specified.
 */
@com.google.common.annotations.Beta
public interface ChatCompletionRequestAssistantMessageContent {
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
   * Creator to enable deserialization of a list of
   * ChatCompletionRequestAssistantMessageContentPart.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerChatCompletionRequestAssistantMessageContentParts}.
   */
  @com.fasterxml.jackson.annotation.JsonCreator
  static InnerChatCompletionRequestAssistantMessageContentParts create(
      List<ChatCompletionRequestAssistantMessageContentPart> val) {
    return new InnerChatCompletionRequestAssistantMessageContentParts(val);
  }

  /**
   * Helper class to create a String that implements {@link
   * ChatCompletionRequestAssistantMessageContent}.
   */
  record InnerString(@com.fasterxml.jackson.annotation.JsonValue String value)
      implements ChatCompletionRequestAssistantMessageContent {}

  /**
   * Helper class to create a list of ChatCompletionRequestAssistantMessageContentPart that
   * implements {@link ChatCompletionRequestAssistantMessageContent}.
   */
  record InnerChatCompletionRequestAssistantMessageContentParts(
      @com.fasterxml.jackson.annotation.JsonValue
          List<ChatCompletionRequestAssistantMessageContentPart> values)
      implements ChatCompletionRequestAssistantMessageContent {}
}
