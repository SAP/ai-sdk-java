package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;

/** Represents a chat message as 'assistant' to the orchestration service. */
@Getter
@Accessors(fluent = true)
public final class AssistantMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Nonnull String content;

  // TODO: replace Spring AI ToolCall with it's individual fields
  @Nullable ToolCall toolCalls = null;

  public AssistantMessage(@Nonnull final String content) {
    this.content = content;
  }

  public AssistantMessage(@Nonnull final List<ToolCall> toolCalls) {
    content = "";
    this.toolCalls = toolCalls.get(0);
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    if (toolCalls != null) {
      final SingleChatMessage message = SingleChatMessage.create().role(role).content(null);
      Map<String, String> map =
          Map.of(
              "id",
              toolCalls().id(),
              "name",
              toolCalls().name(),
              "type",
              toolCalls().type(),
              "arguments",
              toolCalls().arguments());
      message.setCustomField("tool_calls", map);
      return message;
    }
    return Message.super.createChatMessage();
  }
}
