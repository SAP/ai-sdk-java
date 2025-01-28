package com.sap.ai.sdk.orchestration;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull MessageContent content;

  @Override
  @Nonnull
  public MessageContent content() {
    return content;
  }

  public UserMessage(String singleMessage) {
    content = new MessageContent(singleMessage);
  }

  UserMessage(MessageContent messageContent) {
    content = messageContent;
  }

  @Nonnull
  public UserMessage addText(@Nonnull String... messages) {
    return new UserMessage(
        new MessageContent(
            Stream.of(messages).map(TextItem::new).toList(), content));
  }

  @Nonnull
  public UserMessage addImage(
      @Nonnull String imageUrl, ImageItem.DetailLevel detailLevel) {
    return new UserMessage(
        new MessageContent(
            List.of(new ImageItem(imageUrl, detailLevel)), content));
  }
}
