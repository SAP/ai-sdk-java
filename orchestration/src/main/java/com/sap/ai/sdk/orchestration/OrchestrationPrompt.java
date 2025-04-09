package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nonnull;
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
  @Nonnull Map<String, String> templateParameters = new TreeMap<>();
  @Nonnull List<Message> messagesHistory = new ArrayList<>();

  /**
   * Initialize a prompt with the given user message.
   *
   * @param message A user message.
   */
  public OrchestrationPrompt(@Nonnull final String message) {
    messages.add(new UserMessage(message));
  }

  /**
   * Initialize a prompt from the given messages.
   *
   * @param message The first message.
   * @param messages Optionally, more messages.
   */
  public OrchestrationPrompt(@Nonnull final Message message, @Nonnull final Message... messages) {
    this.messages.add(message);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Initialize a prompt based on template variables.
   *
   * @param inputParams The input parameters as entries of template variables and their contents.
   * @param messages The messages to be sent to the orchestration service.
   */
  public OrchestrationPrompt(
      @Nonnull final Map<String, String> inputParams, @Nonnull final Message... messages) {
    this.templateParameters.putAll(inputParams);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Set the chat history of this prompt.
   *
   * @param messagesHistory The chat history to add.
   * @return The current instance of {@link OrchestrationPrompt} with the changed chat history.
   */
  @Nonnull
  public OrchestrationPrompt messageHistory(@Nonnull final List<Message> messagesHistory) {
    this.messagesHistory.clear();
    this.messagesHistory.addAll(messagesHistory);
    return this;
  }
}
