package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestUserMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'user' to OpenAI service. */
@Value
@Accessors(fluent = true)
class OpenAiUserMessage implements OpenAiMessage {

  /** The role of the message. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull String content;

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestUserMessage} object.
   */
  @Nonnull
  public ChatCompletionRequestUserMessage createDTO() {
    return new ChatCompletionRequestUserMessage()
        .role(ChatCompletionRequestUserMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestUserMessageContent.create(content()));
  }
}
