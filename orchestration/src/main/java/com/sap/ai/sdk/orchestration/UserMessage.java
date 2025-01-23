package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  MessageContent content;

  @Nonnull
  @Override
  public String content() {
    return MessageContent.toString(content);
  }

  @Override
  @Nonnull
  public MessageContent getContent() {
    return content != null ? content : new MessageContentSingle("");
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
    return ((UserMessage) Message.addTextMessages(content, role, messages));
  }

  @Nonnull
  public UserMessage addImage(
      @Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel) {
    return ((UserMessage) Message.addImage(content, role, imageUrl, detailLevel));
  }
}
