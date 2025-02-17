package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessage;
import javax.annotation.Nonnull;

/**
 * Utility class for handling OpenAI module.
 *
 * @since 1.4.0
 */
@Beta
class OpenAiUtils {

  /**
   * Converts an OpenAiMessage to a ChatCompletionRequestMessage.
   *
   * @param message the OpenAiMessage to convert
   * @return the corresponding ChatCompletionRequestMessage
   * @throws IllegalArgumentException if the message type is unknown
   */
  @Nonnull
  static ChatCompletionRequestMessage toChatCompletionRequestSystemMessage(
      @Nonnull final OpenAiMessage message) throws IllegalArgumentException {
    if (message instanceof OpenAiUserMessage userMessage) {
      return userMessage.toChatCompletionRequestSystemMessage();
    } else if (message instanceof OpenAiAssistantMessage assistantMessage) {
      return assistantMessage.toChatCompletionRequestSystemMessage();
    } else if (message instanceof OpenAiSystemMessage systemMessage) {
      return systemMessage.toChatCompletionRequestSystemMessage();
    } else {
      throw new IllegalArgumentException("Unknown message type: " + message.getClass());
    }
  }
}
