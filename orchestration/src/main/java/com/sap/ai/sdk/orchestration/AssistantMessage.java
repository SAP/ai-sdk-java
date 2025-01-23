package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'assistant' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class AssistantMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "assistant";

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

  public AssistantMessage(String singleMessage) {
    content = new MessageContentSingle(singleMessage);
  }

  public AssistantMessage(MessageContent messageContent) {
    content = messageContent;
  }

  public AssistantMessage(List<MultiChatMessageContent> multiChatMessageContentList) {
    content =
        new MessageContentMulti(
            multiChatMessageContentList.toArray(MultiChatMessageContent[]::new));
  }

  @Nonnull
  public AssistantMessage addTextMessages(@Nonnull String... messages) {
    return ((AssistantMessage) Message.addTextMessages(content, role, messages));
  }

  @Nonnull
  public AssistantMessage addImage(
      @Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel) {
    return ((AssistantMessage) Message.addImage(content, role, imageUrl, detailLevel));
  }
}
