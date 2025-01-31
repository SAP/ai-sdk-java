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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Deprecated in favor of &#x60;tool_choice&#x60;. Controls which (if any) function is called by the
 * model. &#x60;none&#x60; means the model will not call a function and instead generates a message.
 * &#x60;auto&#x60; means the model can pick between generating a message or calling a function.
 * Specifying a particular function via &#x60;{\&quot;name\&quot;: \&quot;my_function\&quot;}&#x60;
 * forces the model to call that function. &#x60;none&#x60; is the default when no functions are
 * present. &#x60;auto&#x60; is the default if functions are present.
 *
 * @deprecated
 */
@Deprecated
@com.google.common.annotations.Beta
public interface CreateChatCompletionRequestAllOfFunctionCall {
  /**
   * Helper class to create a ChatCompletionFunctionCallOption that implements {@link
   * CreateChatCompletionRequestAllOfFunctionCall}.
   */
  record InnerChatCompletionFunctionCallOption(@JsonValue ChatCompletionFunctionCallOption value)
      implements CreateChatCompletionRequestAllOfFunctionCall {}

  /**
   * Creator to enable deserialization of a ChatCompletionFunctionCallOption.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerChatCompletionFunctionCallOption}.
   */
  @JsonCreator
  static InnerChatCompletionFunctionCallOption create(ChatCompletionFunctionCallOption val) {
    return new InnerChatCompletionFunctionCallOption(val);
  }

  /**
   * Helper class to create a String that implements {@link
   * CreateChatCompletionRequestAllOfFunctionCall}.
   */
  record InnerString(@JsonValue String value)
      implements CreateChatCompletionRequestAllOfFunctionCall {}

  /**
   * Creator to enable deserialization of a String.
   *
   * @param val the value to use
   * @return a new instance of {@link InnerString}.
   */
  @JsonCreator
  static InnerString create(String val) {
    return new InnerString(val);
  }
}
