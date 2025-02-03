package com.sap.ai.sdk.orchestration;

import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.messages.ToolResponseMessage;

import javax.annotation.Nonnull;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class ToolMessage implements Message {

  /** The role of the assistant. */
  @Nonnull
  String role = "tool";

  /** The content of the message. */
  @Nonnull
  ToolResponseMessage message;

  @Nonnull
  @Override
  public String content()
  {
    return "";
  }
}

