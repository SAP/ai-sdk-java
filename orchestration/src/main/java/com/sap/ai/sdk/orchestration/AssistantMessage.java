package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
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

  @Nullable ToolCall toolCalls = null;

  public record ToolCall(String id, String type, String name, String arguments) {}

  public AssistantMessage(@Nonnull final String content) {
    this.content = content;
  }

  public AssistantMessage(@Nonnull final ToolCall toolCall) {
    content = "";
    this.toolCalls = toolCall;
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
