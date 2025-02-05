package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
   * Creates a new system message from one or more strings.
   *
   * @param message the first message.
   * @param additionalMessages the additional messages.
   */
  public SystemMessage(
      @Nonnull final String message, @Nullable final String... additionalMessages) {
    content = MessageContent.text(message, additionalMessages);
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
   * @param messages the text to add.
   * @return the new message.
   */
  @Nonnull
  public SystemMessage andText(@Nonnull final String... messages) {
    return new SystemMessage(
        new MessageContent(
            Stream.concat(
                    content.contentItemList().stream(), Stream.of(messages).map(TextItem::new))
                .toList()));
  }

  /**
   * Add content to the message. The content will be added to the end of the message. As of now,
   * only TextItem will be successfully consumed by an AI.
   *
   * @param messageContents the content to add.
   * @return the new message.
   */
  @Nonnull
  public SystemMessage and(@Nonnull final MessageContent... messageContents) {
    final List<ContentItem> combinedItems =
        Stream.concat(
                content.contentItemList().stream(),
                Stream.of(messageContents)
                    .flatMap(contentItem -> contentItem.contentItemList().stream()))
            .toList();
    return new SystemMessage(new MessageContent(combinedItems));
  }
}
