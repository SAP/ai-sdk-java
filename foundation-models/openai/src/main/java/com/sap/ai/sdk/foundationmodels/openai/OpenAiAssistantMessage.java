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
    var message =
        new ChatCompletionRequestAssistantMessage()
            .role(ChatCompletionRequestAssistantMessage.RoleEnum.fromValue(role()));

    for (var item : content().items()) {
      if (item instanceof OpenAiTextItem textItem) {
        message.content(ChatCompletionRequestAssistantMessageContent.create(textItem.text()));
      } else if (item instanceof OpenAiFunctionCallItem functionItem) {

        var functionCall =
            new ChatCompletionMessageToolCallFunction()
                .name(functionItem.getName())
                .arguments(functionItem.getArguments());

        var toolCall =
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
