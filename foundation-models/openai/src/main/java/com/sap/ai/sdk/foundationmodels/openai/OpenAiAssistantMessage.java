package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCallFunction;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ToolCallType;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'assistant' to OpenAI service.
 *
 * <p>When {@link OpenAiAssistantMessage} is received from {@link OpenAiChatCompletionResponse}, it
 * may contain tool calls that need to be executed. The tool calls are represented as {@link
 * OpenAiToolCall}.
 *
 * @since 1.4.0
 */
@Value
@Accessors(fluent = true)
@AllArgsConstructor(access = PACKAGE)
public class OpenAiAssistantMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "assistant";

  /**
   * The content of the message.
   *
   * <p>May contain an empty list of {@link OpenAiContentItem} when tool calls are present.
   */
  @Getter @Nonnull OpenAiMessageContent content;

  /**
   * The tool calls associated with this message if present.
   *
   * @since 1.6.0
   */
  @Getter @Nonnull List<OpenAiToolCall> toolCalls;

  /**
   * Creates a new assistant message with the given single message as text content.
   *
   * @param singleMessage the message.
   */
  OpenAiAssistantMessage(@Nonnull final String singleMessage) {
    this(
        new OpenAiMessageContent(List.of(new OpenAiTextItem(singleMessage))),
        Collections.emptyList());
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding {@code ChatCompletionRequestAssistantMessage} object.
   */
  @Nonnull
  ChatCompletionRequestAssistantMessage createChatCompletionRequestMessage() {
    final var message =
        new ChatCompletionRequestAssistantMessage()
            .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()))
            .toolCalls(null);

    final var items = content().items();
    if (!items.isEmpty() && items.get(0) instanceof OpenAiTextItem textItem) {
      message.content(ChatCompletionRequestAssistantMessageContent.create(textItem.text()));
    }

    for (final var item : toolCalls()) {
      if (item instanceof OpenAiFunctionCall functionItem) {
        final var functionCall =
            new ChatCompletionMessageToolCallFunction()
                .name(functionItem.getName())
                .arguments(functionItem.getArguments());

        final var toolCall =
            new ChatCompletionMessageToolCall()
                .type(ToolCallType.FUNCTION)
                .id(functionItem.getId())
                .function(functionCall);

        message.addToolCallsItem(toolCall);
      }
    }
    return message;
  }
}
