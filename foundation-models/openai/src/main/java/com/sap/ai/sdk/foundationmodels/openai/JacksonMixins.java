package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionStreamResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains Jackson Mixins for customizing the serialization and deserialization behavior
 * of certain classes in the OpenAI SDK.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {

  /**
   * Mixin interface to customize the deserialization of CreateChatCompletionStreamResponse.
   *
   * <p>Disables type information inclusion and specifies the concrete class to use for
   * deserialization.
   */
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = CreateChatCompletionStreamResponse.class)
  interface DefaultChatCompletionCreate200ResponseMixIn {}
}
