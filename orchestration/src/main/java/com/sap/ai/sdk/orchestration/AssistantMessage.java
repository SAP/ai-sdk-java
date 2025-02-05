package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

import com.google.common.annotations.Beta;
import lombok.Getter;
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
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public AssistantMessage(@Nonnull final String singleMessage) {
    content = new MessageContent(singleMessage);
  }
}
