package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import javax.annotation.Nonnull;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public interface Message {

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  ChatMessage createChatMessage();
}
