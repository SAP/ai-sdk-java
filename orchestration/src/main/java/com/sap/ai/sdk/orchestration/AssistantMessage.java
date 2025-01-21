package com.sap.ai.sdk.orchestration;

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
  @Nonnull String content;

  @Override
  public MessageContent getContent() {
    return new MessageContentSingle("------------Placeholder---------");
  }
}
