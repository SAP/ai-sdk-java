package com.sap.ai.sdk.orchestration;

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

  SystemMessage(MessageContent messageContent) {
      messageContent
          .contentItemList().stream()
              .filter(contentItem -> !(contentItem instanceof TextItem))
              .findAny()
              .ifPresent(
                  mCMC -> {
                    throw new IllegalArgumentException(
                        "Only TextContent is supported for SystemMessage");
                  });
    content = messageContent;
  }

  @Nonnull
  public SystemMessage addText(@Nonnull String... messages) {
    return new SystemMessage(
        new MessageContent(
            Stream.of(messages).map(TextItem::new).toList(), content));
  }

  public SystemMessage add(@Nonnull MessageContent... messageContents) {
//    TODO: What about images here? Also, see above in line 29.
    return new SystemMessage(
        new MessageContent(
            Stream.of(messageContents)
                .flatMap(contentItem -> contentItem.contentItemList().stream())
                .toList(),
            content));
  }
}
