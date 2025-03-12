package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ResponseMessageToolCall;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
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
  @Nullable List<ResponseMessageToolCall> toolCalls;

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
  public AssistantMessage(@Nonnull final List<ResponseMessageToolCall> toolCalls) {
    content = new MessageContent(List.of());
    this.toolCalls = toolCalls;
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    if (toolCalls() != null) {
      // content shouldn't be required for tool calls 🤷
      val message = SingleChatMessage.create().role(role).content("");
      message.setCustomField("tool_calls", toolCalls);
      return message;
    }
    return Message.super.createChatMessage();
  }
}
