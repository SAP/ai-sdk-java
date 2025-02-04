package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

/** Represents a chat message as 'assistant' to the orchestration service. */
@Getter
@Accessors(fluent = true)
public final class AssistantMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Nonnull String content;

  /** Tool call if there is any. */
  @Nullable List<ToolCall> toolCalls = null;

  /**
   * Represents a tool call.
   *
   * @param id call id
   * @param type "function" or "tool"
   * @param name the name of the function to call
   * @param arguments the arguments to pass to the function
   */
  public record ToolCall(String id, String type, String name, String arguments) {}

  /**
   * Creates a new assistant message with the given text content.
   *
   * @param content the message
   */
  public AssistantMessage(@Nonnull final String content) {
    this.content = content;
  }

  /**
   * Creates a new assistant message with the given tool calls.
   *
   * @param toolCalls list of tool call objects
   */
  public AssistantMessage(@Nonnull final List<ToolCall> toolCalls) {
    content = "";
    this.toolCalls = toolCalls;
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    if (toolCalls() != null) {
      final List<Map<String, Object>> toolCallList =
          toolCalls().stream()
              .map(
                  toolCall -> {
                    val function =
                        Map.of("name", toolCall.name(), "arguments", toolCall.arguments());
                    return Map.of(
                        "id", toolCall.id(), "type", toolCall.type(), toolCall.type(), function);
                  })
              .toList();

      // content shouldn't be required for tool calls 🤷
      val message = SingleChatMessage.create().role(role).content("");
      message.setCustomField("tool_calls", toolCallList);
      return message;
    }
    return Message.super.createChatMessage();
  }
}
