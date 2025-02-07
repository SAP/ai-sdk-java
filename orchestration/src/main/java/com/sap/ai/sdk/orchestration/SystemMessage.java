package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class SystemMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Nonnull
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /**
   * Creates a new system message from a string.
   *
   * @param message the first message.
   */
  public SystemMessage(@Nonnull final String message) {
    content = new MessageContent(List.of(new TextItem(message)));
  }

  /**
   * Creates a new system message with the given message content.
   *
   * @param messageContent the message content.
   */
  public SystemMessage(@Nonnull final MessageContent messageContent) {
    content = messageContent;
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   */
  @Nonnull
  public SystemMessage andText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.contentItemList());
    contentItems.add(new TextItem(message));
    return new SystemMessage(new MessageContent(contentItems));
  }
}
