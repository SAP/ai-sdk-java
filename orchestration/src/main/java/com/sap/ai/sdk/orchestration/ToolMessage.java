package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.ToolChatMessage.RoleEnum.TOOL;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'tool' to the orchestration service.
 *
 * @since 1.4.0
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
    return ToolChatMessage.create()
        .role(TOOL)
        .toolCallId(id)
        .content(ChatMessageContent.create(content));
  }
}
