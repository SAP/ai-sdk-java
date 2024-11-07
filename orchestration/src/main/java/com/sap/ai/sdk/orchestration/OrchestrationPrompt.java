package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
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
  @Nonnull List<ChatMessage> messages = new ArrayList<>();
  @Nonnull Map<String, String> templateParameters = new TreeMap<>();
  @Nonnull List<ChatMessage> messagesHistory = new ArrayList<>();

  /**
   * Initialize a prompt with the given user message.
   *
   * @param message A user message.
   */
  public OrchestrationPrompt(@Nonnull final String message) {
    messages.add(ChatMessage.create().role("user").content(message));
  }

  /**
   * Initialize a prompt from the given messages.
   *
   * @param message The first message.
   * @param messages Optionally, more messages.
   */
  public OrchestrationPrompt(
      @Nonnull final ChatMessage message, @Nonnull final ChatMessage... messages) {
    this.messages.add(message);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Initialize a prompt based on template variables.
   *
   * @param inputParams The input parameters as entries of template variables and their contents.
   */
  public OrchestrationPrompt(
      @Nonnull final Map<String, String> inputParams, @Nonnull final ChatMessage... messages) {
    this.templateParameters.putAll(inputParams);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Add a chat history to this prompt.
   *
   * @param messagesHistory The chat history to add.
   */
  public void setMessageHistory(@Nonnull final List<ChatMessage> messagesHistory) {
    this.messagesHistory.clear();
    this.messagesHistory.addAll(messagesHistory);
  }
}
