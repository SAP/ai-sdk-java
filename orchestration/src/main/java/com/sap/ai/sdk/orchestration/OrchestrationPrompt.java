package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

/**
 * Represents a request that can be sent to the orchestration service, containing messages and
 * configuration for the orchestration modules. Prompts may be reused across multiple requests.
 *
 * @see OrchestrationClient
 * @see OrchestrationConfig
 */
@Value
@Getter(AccessLevel.PACKAGE)
public class OrchestrationPrompt {
  @Nonnull List<Message> messages = new ArrayList<>();
  @Nonnull List<Message> messagesHistory = new ArrayList<>();

  /**
   * Initialize a prompt with the given user message.
   *
   * @param message a user message.
   */
  public OrchestrationPrompt(@Nonnull final String message) {
    this(message, null);
  }

  /**
   * Initialize a prompt with the given user message with optional cache checkpoint configuration.
   *
   * @param message a user message.
   * @param cacheControl optional cache checkpoint configuration.
   * @since 1.23.0
   */
  public OrchestrationPrompt(
      @Nonnull final String message, @Nullable final CacheControl cacheControl) {
    messages.add(new UserMessage(message, cacheControl));
  }

  /**
   * Initialize a prompt from the given messages.
   *
   * @param message the first message.
   * @param messages optionally, more messages.
   */
  public OrchestrationPrompt(@Nonnull final Message message, @Nonnull final Message... messages) {
    this.messages.add(message);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Set the chat history of this prompt.
   *
   * @param messagesHistory the chat history to add.
   * @return the current instance of {@link OrchestrationPrompt} with the changed chat history.
   */
  @Nonnull
  public OrchestrationPrompt messageHistory(@Nonnull final List<Message> messagesHistory) {
    this.messagesHistory.clear();
    this.messagesHistory.addAll(messagesHistory);
    return this;
  }
}
