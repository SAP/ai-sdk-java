package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'user' to OpenAI service. *
 *
 * @since 1.4.0
 */
@Beta
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
  ChatCompletionRequestUserMessage createChatCompletionRequestMessage() {
    return new ChatCompletionRequestUserMessage()
        .role(ChatCompletionRequestUserMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestUserMessageContent.create(content()));
  }
}
