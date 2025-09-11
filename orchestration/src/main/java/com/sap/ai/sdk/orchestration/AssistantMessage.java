package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.AssistantChatMessage.RoleEnum.ASSISTANT;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.MessageToolCall;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.val;

/** Represents a chat message as 'assistant' to the orchestration service. */
@Value
@Getter
@Accessors(fluent = true)
public class AssistantMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Nonnull
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /** Tool call if there is any. */
  @Nullable List<MessageToolCall> toolCalls;

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public AssistantMessage(@Nonnull final String singleMessage) {
    content = new MessageContent(List.of(new TextItem(singleMessage)));
    toolCalls = null;
  }

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param content the single message.
   */
  AssistantMessage(@Nonnull final MessageContent content) {
    this.content = content;
    toolCalls = null;
  }

  /**
   * Creates a new assistant message with the given tool calls.
   *
   * @param toolCalls list of tool call objects
   */
  public AssistantMessage(@Nonnull final List<MessageToolCall> toolCalls) {
    content = new MessageContent(List.of());
    this.toolCalls = toolCalls;
  }

  /**
   * Creates a new assistant message with the given message and tool calls.
   *
   * @param singleMessage the message content.
   * @param toolCalls list of tool call objects
   * @since 1.11.0
   */
  public AssistantMessage(
      @Nonnull final String singleMessage, @Nullable final List<MessageToolCall> toolCalls) {
    this.content = new MessageContent(List.of(new TextItem(singleMessage)));
    this.toolCalls = toolCalls;
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    val assistantChatMessage = AssistantChatMessage.create().role(ASSISTANT);

    if (toolCalls != null) {
      assistantChatMessage.setToolCalls(toolCalls);
    }

    ChatMessageContent text;
    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      text = ChatMessageContent.create(textItem.text());
    } else {
      val texts =
          content.items().stream()
              .filter(item -> item instanceof TextItem)
              .map(item -> (TextItem) item)
              .map(item -> TextContent.create().type(TextContent.TypeEnum.TEXT).text(item.text()))
              .toList();
      text = ChatMessageContent.create(texts);
    }
    return assistantChatMessage.content(text);
  }
}
