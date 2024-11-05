package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.val;

/**
 * Represents a request that can be sent to the orchestration service, containing messages and
 * configuration for the orchestration modules. Prompts may be reused across multiple requests.
 *
 * @see OrchestrationClient
 * @see OrchestrationConfig
 */
@Value
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class OrchestrationPrompt {
  @Nonnull List<ChatMessage> messages;
  @Nonnull Map<String, String> templateParameters;

  /**
   * Initialize a prompt with the given user message.
   *
   * @param message A user message.
   */
  public OrchestrationPrompt(@Nonnull final String message) {
    this(List.of(ChatMessage.create().role("user").content(message)), Map.of());
  }

  /**
   * Initialize a prompt from the given messages.
   *
   * @param message The first message.
   * @param messages Optionally, more messages.
   */
  public OrchestrationPrompt(
      @Nonnull final ChatMessage message, @Nonnull final ChatMessage... messages) {
    val allMessages = new ArrayList<ChatMessage>();
    allMessages.add(message);
    allMessages.addAll(Arrays.asList(messages));
    this.messages = allMessages;
    this.templateParameters = Map.of();
  }

  /**
   * Initialize a prompt based on template variables.
   *
   * @param inputParams The input parameters as entries of template variables and their contents.
   */
  public OrchestrationPrompt(@Nonnull final Map<String, String> inputParams) {
    this(List.of(), inputParams);
  }
}
