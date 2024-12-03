package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import javax.annotation.Nonnull;
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
public class OpenAiChatCompletionChoice extends OpenAiCompletionChoice {
  /** Completion chat message. */
  @JsonProperty("message")
  @Getter(onMethod_ = @Nonnull)
  @Setter(onMethod_ = @Nonnull, value = AccessLevel.PACKAGE)
  private OpenAiChatAssistantMessage message;

  /** Completion chat log probability information for the choice. */
  @JsonProperty("logprobs")
  @Getter(onMethod_ = @Nullable)
  @Setter(onMethod_ = @Nullable, value = AccessLevel.PACKAGE)
  private OpenAiChatCompletionLogProbability logProbs;

  void addDelta(@Nonnull final OpenAiDeltaChatCompletionChoice delta) {
    super.addDelta(delta);

    if (delta.getMessage() != null) {
      if (message == null) {
        message = new OpenAiChatAssistantMessage();
      }
      message.addDelta(delta.getMessage());
    }
  }
}
