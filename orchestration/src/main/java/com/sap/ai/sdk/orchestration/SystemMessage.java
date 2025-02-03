package com.sap.ai.sdk.orchestration;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class SystemMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Nonnull MessageContent content;

  @Override
  @Nonnull
  public MessageContent content() {
    return content;
  }

  /**
   * Creates a new system message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public SystemMessage(@Nonnull String singleMessage) {
    content = new MessageContent(singleMessage);
  }

  /**
   * Creates a new system message with the given message content.
   *
   * @param messageContent the message content.
   */
  public SystemMessage(@Nonnull MessageContent messageContent) {
    content = messageContent;
  }

  /**
   * Add text to the message.
   *
   * @param messages the text to add.
   * @return the new message.
   */
  @Nonnull
  public SystemMessage addText(@Nonnull String... messages) {
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
  public SystemMessage add(@Nonnull MessageContent... messageContents) {
    List<ContentItem> combinedItems =
        Stream.concat(
                Stream.of(messageContents)
                    .flatMap(contentItem -> contentItem.contentItemList().stream()),
                content.contentItemList().stream())
            .toList();
    return new SystemMessage(new MessageContent(combinedItems));
  }
}
