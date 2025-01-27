package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.orchestration.model.TextContent;
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
    content = new MessageContentSingle(singleMessage);
  }

  SystemMessage(MessageContent messageContent) {
    if (!(messageContent instanceof MessageContentSingle)) {
      ((MessageContentMulti) messageContent).multiContentList().stream()
          .filter(mCMC -> !(mCMC instanceof MultiMessageTextContent))
          .findAny()
          .ifPresent(mCMC -> {
            throw new IllegalArgumentException("Only TextContent is supported for SystemMessage");
          });
    }
    content = messageContent;
  }

  @Nonnull
  public SystemMessage addText(@Nonnull String... messages) {
    return new SystemMessage(
        new MessageContentMulti(
            Stream.of(messages).map(MultiMessageTextContent::new).toList(), content));
  }
}
