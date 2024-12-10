package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
public class SystemMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Nonnull String content;
}
