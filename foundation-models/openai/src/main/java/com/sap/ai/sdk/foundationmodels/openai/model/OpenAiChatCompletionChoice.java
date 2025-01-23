package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import javax.annotation.Nonnull;
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
@Beta
public class OpenAiChatCompletionChoice extends OpenAiCompletionChoice {
  /** Completion chat message. */
  @JsonProperty("message")
  @Getter(onMethod_ = @Nonnull)
  @Setter(onMethod_ = @Nonnull, value = AccessLevel.PACKAGE)
  private OpenAiChatAssistantMessage message;

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
