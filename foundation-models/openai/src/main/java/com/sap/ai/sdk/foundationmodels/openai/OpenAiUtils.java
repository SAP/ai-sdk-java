package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionsCreate200Response;
import javax.annotation.Nonnull;

/**
 * Utility class for handling OpenAI module.
 *
 * <p><b>Only intended for internal usage within this SDK</b>.
 *
 * @since 1.4.0
 */
class OpenAiUtils {

  /**
   * Converts an OpenAiMessage to a ChatCompletionRequestMessage.
   *
   * @param message the OpenAiMessage to convert
   * @return the corresponding ChatCompletionRequestMessage
   * @throws IllegalArgumentException if the message type is unknown
   */
  @Nonnull
  static ChatCompletionRequestMessage createChatCompletionRequestMessage(
      @Nonnull final OpenAiMessage message) throws IllegalArgumentException {
    if (message instanceof OpenAiUserMessage userMessage) {
      return userMessage.createChatCompletionRequestMessage();
    } else if (message instanceof OpenAiAssistantMessage assistantMessage) {
      return assistantMessage.createChatCompletionRequestMessage();
    } else if (message instanceof OpenAiSystemMessage systemMessage) {
      return systemMessage.createChatCompletionRequestMessage();
    } else if (message instanceof OpenAiToolMessage toolMessage) {
      return toolMessage.createChatCompletionRequestMessage();
    } else {
      throw new IllegalArgumentException("Unknown message type: " + message.getClass());
    }
  }

  /**
   * Default object mapper used for JSON de-/serialization.
   *
   * @return A new object mapper with the default configuration.
   */
  @Nonnull
  static ObjectMapper getOpenAiObjectMapper() {
    return getDefaultObjectMapper()
        .addMixIn(
            ChatCompletionsCreate200Response.class,
            JacksonMixins.DefaultChatCompletionCreate200ResponseMixIn.class);
  }
}
