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
    content = new MessageContentSingle(singleMessage);
  }

  UserMessage(MessageContent messageContent) {
    content = messageContent;
  }

  @Nonnull
  public UserMessage addText(@Nonnull String... messages) {
    return new UserMessage(
        new MessageContentMulti(
            Stream.of(messages).map(MultiMessageTextContent::new).toList(), content));
  }

  @Nonnull
  public UserMessage addImage(
      @Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel) {
    return new UserMessage(
        new MessageContentMulti(
            List.of(new MultiMessageImageContent(imageUrl, detailLevel)), content));
  }
}
