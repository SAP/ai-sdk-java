package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a tool message in the OpenAI service.
 *
 * @since 1.4.0
 */
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = PACKAGE)
public class OpenAiToolMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "tool";

  /** Response of the executed tool call. */
  @Nonnull OpenAiMessageContent content;

  /** The tool call id associated with this message. */
  @Nonnull String toolCallId;

  /**
   * Creates a new tool message from a tool execution response and tool call id.
   *
   * @param message response of the executed tool call.
   * @param toolCallId identifier of the tool call that the assistant expected.
   */
  OpenAiToolMessage(@Nonnull final String message, @Nonnull final String toolCallId) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(message))), toolCallId);
  }

  /**
   * Creates a ChatCompletionRequestToolMessage from the current message content.
   *
   * @return the created ChatCompletionRequestToolMessage.
   */
  @Nonnull
  ChatCompletionRequestToolMessage createChatCompletionRequestMessage() {
    final var textItem = (OpenAiTextItem) this.content().items().get(0);
    return new ChatCompletionRequestToolMessage()
        .role(ChatCompletionRequestToolMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestToolMessageContent.create(textItem.text()))
        .toolCallId(toolCallId());
  }
}
