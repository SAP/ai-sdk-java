package com.sap.ai.sdk.foundationmodels.openai;

import javax.annotation.Nonnull;

/**
 * Represents a tool called by an OpenAI model.
 *
 * @since 1.6.0
 */
public sealed interface OpenAiToolCall permits OpenAiFunctionCall {
  /**
   * Creates a new instance of {@link OpenAiToolCall}.
   *
   * @param id the unique identifier for the tool call.
   * @param name the name of the tool to be called.
   * @param arguments the arguments for the tool call, encoded as a JSON string.
   * @return a new instance of {@link OpenAiToolCall}.
   * @since 1.10.0
   */
  @Nonnull
  static OpenAiToolCall function(
      @Nonnull final String id, @Nonnull final String name, @Nonnull final String arguments) {
    return new OpenAiFunctionCall(id, name, arguments);
  }
}
