package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestToolMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'system' to OpenAI service. */
@Value
@Accessors(fluent = true)
class OpenAiToolMessage implements OpenAiMessage {

  /** The role of the message. */
  @Nonnull String role = "tool";

  /** The content of the message. */
  @Nonnull String content;

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestToolMessage} object.
   */
  @Nonnull
  public ChatCompletionRequestToolMessage createDTO() {
    return new ChatCompletionRequestToolMessage()
        .role(ChatCompletionRequestToolMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestToolMessageContent.create(content()));
  }
}
