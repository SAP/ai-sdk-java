package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/**
 * Represents a tool called by an OpenAI model.
 *
 * @since 1.6.0
 */
@Beta
public sealed interface OpenAiToolCall permits OpenAiFunctionCall {
  /**
   * Creates a new instance of {@link OpenAiToolCall}.
   *
   * @param id The unique identifier for the tool call.
   * @param name The name of the tool to be called.
   * @param arguments The arguments for the tool call, encoded as a JSON string.
   * @return A new instance of {@link OpenAiToolCall}.
   */
  @Nonnull
  static OpenAiToolCall create(
      @Nonnull final String id, @Nonnull final String name, @Nonnull final String arguments) {
    return new OpenAiFunctionCall(id, name, arguments);
  }
}
