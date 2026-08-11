package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.ToolChatMessage.RoleEnum.TOOL;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Represents a chat message as 'tool' to the orchestration service.
 *
 * @since 1.4.0
 */
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public final class ToolMessage implements Message {

  /** The role of the assistant. */
  @Nonnull final String role = "tool";

  @Nonnull final String id;

  @Nonnull final String content;

  @Nullable final CacheControl cacheControl;

  /**
   * Constructs ToolMessage object
   *
   * @param id tool call id
   * @param content message content
   */
  public ToolMessage(@Nonnull final String id, @Nonnull final String content) {
    this(id, content, null);
  }

  @Nonnull
  @Override
  public MessageContent content() {
    return new MessageContent(List.of(new TextItem(content, cacheControl)));
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
