package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import javax.annotation.Nonnull;
import lombok.Value;
import lombok.experimental.Accessors;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull public static final String ROLE = "user";

  @Nonnull String content;

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  public ChatMessage createChatMessage() {
    return ChatMessage.create().role(ROLE).content(content);
  }
}
