package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {
  @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
  @JsonDeserialize(as = CreateChatCompletionStreamResponse.class)
  public interface DefaultChatCompletionCreate200ResponseMixIn {}
}
