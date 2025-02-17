package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessageContent;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'system' to OpenAI service. *
 *
 * @since 1.4.0
 */
@Beta
@Value
@Accessors(fluent = true)
class OpenAiSystemMessage implements OpenAiMessage {

  /** The role of the message. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Nonnull String content;

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestSystemMessage} object.
   */
  @Nonnull
  ChatCompletionRequestSystemMessage createChatCompletionRequestMessage() {
    return new ChatCompletionRequestSystemMessage()
        .role(ChatCompletionRequestSystemMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestSystemMessageContent.create(content()));
  }
}
