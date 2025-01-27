package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
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

  public UserMessage(MessageContent messageContent) {
    content = messageContent;
  }

  public UserMessage(List<MultiChatMessageContent> multiChatMessageContentList) {
    content =
        new MessageContentMulti(
            multiChatMessageContentList.toArray(MultiChatMessageContent[]::new));
  }

  @Nonnull
  public UserMessage addTextMessages(@Nonnull String... messages) {
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
