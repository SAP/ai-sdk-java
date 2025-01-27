package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import java.util.List;
import java.util.stream.Stream;
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
  @Nonnull
  MessageContentSingle content;

  @Override
  @Nonnull
  public MessageContent content() {
    return content;
  }

  public AssistantMessage(String singleMessage) {
    content = new MessageContentSingle(singleMessage);
  }

  public AssistantMessage(MessageContentSingle messageContent) {
    content = messageContent;
  }

//  TODO: Do we need addText() for AssistantMessage?
//  @Nonnull
//  public SystemMessage addTextMessages(@Nonnull String... messages) {
//    return new SystemMessage(
//        new MessageContentMulti(
//            Stream.of(messages).map(MultiMessageTextContent::new).toList(), content));
//  }
//  @Nonnull
//  public AssistantMessage addTextMessages(@Nonnull String... messages) {
//    return ((AssistantMessage) Message.addTextMessages(content, role, messages));
//  }

}
