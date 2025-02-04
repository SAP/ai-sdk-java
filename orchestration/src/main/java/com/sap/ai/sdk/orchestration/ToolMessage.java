package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'tool' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class ToolMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "tool";

  @Nonnull String id;

  @Nonnull String content;

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    SingleChatMessage message = SingleChatMessage.create().role(role()).content(content);
    message.setCustomField("tool_call_id", id);
    return message;
  }
}
