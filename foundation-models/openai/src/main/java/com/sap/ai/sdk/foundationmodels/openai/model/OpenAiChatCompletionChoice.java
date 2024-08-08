package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Result candidates for OpenAI chat completion output. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAiChatCompletionChoice extends OpenAiCompletionChoice {
  /** Completion chat message. */
  @JsonProperty("message")
  @Getter(onMethod_ = @Nonnull)
  private OpenAiChatAssistantMessage message;
}
