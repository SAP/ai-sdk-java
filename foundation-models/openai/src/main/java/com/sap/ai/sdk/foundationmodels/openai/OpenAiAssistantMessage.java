package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
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
public class OpenAiAssistantMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Getter(onMethod_ = @Beta)
  @Nonnull
  OpenAiMessageContent content;

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public OpenAiAssistantMessage(@Nonnull final String singleMessage) {
    content = new OpenAiMessageContent(List.of(new OpenAiTextItem(singleMessage)));
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding serializable object.
   */
  @Nonnull
  ChatCompletionRequestAssistantMessage createChatCompletionRequestMessage()
      throws IllegalArgumentException {
    final var textItem = (OpenAiTextItem) this.content().items().get(0);
    return new ChatCompletionRequestAssistantMessage()
        .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestAssistantMessageContent.create(textItem.text()));
  }
}
