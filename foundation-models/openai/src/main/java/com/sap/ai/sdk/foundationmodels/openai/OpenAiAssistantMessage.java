package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'assistant' to OpenAI service.
 *
 * @since 1.4.0
 */
@Beta
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
  ChatCompletionRequestAssistantMessage createChatCompletionRequestMessage() {
    return new ChatCompletionRequestAssistantMessage()
        .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestAssistantMessageContent.create(content));
  }
}
