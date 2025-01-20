package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonMixins {
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      property = "object",
      defaultImpl = CreateChatCompletionResponse.class,
      visible = true)
  @JsonSubTypes({
    @JsonSubTypes.Type(value = CreateChatCompletionResponse.class, name = "chat.completion"),
    @JsonSubTypes.Type(
        value = CreateChatCompletionStreamResponse.class,
        name = "chat.completion.chunk"),
  })
  public interface ChatCompletionCreate200ResponseMixIn {}
}
