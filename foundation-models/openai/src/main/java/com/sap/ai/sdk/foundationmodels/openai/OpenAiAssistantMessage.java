package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = PACKAGE)
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
  OpenAiAssistantMessage(@Nonnull final String singleMessage) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(singleMessage))));
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestAssistantMessage} object.
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
