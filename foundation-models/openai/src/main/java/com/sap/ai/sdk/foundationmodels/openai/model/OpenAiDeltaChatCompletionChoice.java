package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Result candidates for OpenAI chat completion output. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpenAiDeltaChatCompletionChoice extends OpenAiCompletionChoice {
  /** Completion chat message. */
  @JsonProperty("message")
  @Getter(onMethod_ = @Nullable)
  @Setter(onMethod_ = @Nullable, value = AccessLevel.PACKAGE)
  private OpenAiChatAssistantMessage message;
}
