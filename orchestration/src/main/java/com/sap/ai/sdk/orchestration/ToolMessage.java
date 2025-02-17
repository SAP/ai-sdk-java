package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'tool' to the orchestration service.
 *
 * @since 1.3.0
 */
@Value
@Accessors(fluent = true)
public class ToolMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "tool";

  @Nonnull String id;

  @Nonnull String content;

  @Nonnull
  @Override
  public MessageContent content() {
    return new MessageContent(List.of(new TextItem(content)));
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    final SingleChatMessage message = SingleChatMessage.create().role(role()).content(content);
    message.setCustomField("tool_call_id", id);
    return message;
  }
}
