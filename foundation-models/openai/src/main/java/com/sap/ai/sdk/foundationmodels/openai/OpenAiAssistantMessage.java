package com.sap.ai.sdk.foundationmodels.openai;

import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCallFunction;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ToolCallType;
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
 * OpenAiToolCallItem}.
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
   * Creates a new assistant message with the given single message as text content.
   *
   * @param singleMessage the message.
   */
  OpenAiAssistantMessage(@Nonnull final String singleMessage) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(singleMessage))));
  }

  /**
   * Retrieves the list of tool calls within the content items in this assistant message.
   *
   * @return a list of {@link OpenAiToolCallItem} representing the tool calls.
   * @since 1.6.0
   */
  @Nonnull
  public List<OpenAiToolCallItem> getToolCalls() {
    return this.content().items().stream()
        .filter(item -> item instanceof OpenAiToolCallItem)
        .map(item -> (OpenAiToolCallItem) item)
        .toList();
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
            .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()));

    for (final var item : content().items()) {
      if (item instanceof OpenAiTextItem textItem) {
        message.content(ChatCompletionRequestAssistantMessageContent.create(textItem.text()));
      } else if (item instanceof OpenAiFunctionCallItem functionItem) {

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
