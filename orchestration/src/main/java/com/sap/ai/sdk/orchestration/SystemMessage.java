package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
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
  @Tolerate
  public SystemMessage(@Nonnull final String message) {
    content = new MessageContent(List.of(new TextItem(message)));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public SystemMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new TextItem(message));
    return new SystemMessage(new MessageContent(contentItems));
  }
}
