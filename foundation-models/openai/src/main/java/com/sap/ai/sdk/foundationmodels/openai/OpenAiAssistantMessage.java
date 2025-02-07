package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionRequestAssistantMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'assistant' to OpenAI service.
 *
 * @since 1.3.0
 */
@Value
@Accessors(fluent = true)
class OpenAiAssistantMessage implements OpenAiMessage {

  /** The role of the message. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Nonnull String content;

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestAssistantMessage} object.
   */
  @Nonnull
  public ChatCompletionRequestAssistantMessage createDTO() {
    return new ChatCompletionRequestAssistantMessage()
        .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestAssistantMessageContent.create(content));
  }
}
