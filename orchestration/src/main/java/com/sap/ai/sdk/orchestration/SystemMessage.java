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

  public SystemMessage(String singleMessage) {
    content = new MessageContent(singleMessage);
  }

  public SystemMessage(MessageContent messageContent) {
    content = messageContent;
  }

  @Nonnull
  public SystemMessage addText(@Nonnull String... messages) {
    return new SystemMessage(
        new MessageContent(
            Stream.concat(
                    content.contentItemList().stream(), Stream.of(messages).map(TextItem::new))
                .toList()));
  }

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
