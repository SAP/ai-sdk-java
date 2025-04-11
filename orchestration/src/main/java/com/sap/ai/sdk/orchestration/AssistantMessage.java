package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.AssistantChatMessage.RoleEnum.ASSISTANT;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.MessageToolCall;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

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
   * Creates a new assistant message with the given tool calls.
   *
   * @param toolCalls list of tool call objects
   */
  public AssistantMessage(@Nonnull final List<MessageToolCall> toolCalls) {
    content = new MessageContent(List.of());
    this.toolCalls = toolCalls;
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    if (toolCalls() != null) {
      return AssistantChatMessage.create().role(ASSISTANT).toolCalls(toolCalls);
    }
    return Message.super.createChatMessage();
  }
}
