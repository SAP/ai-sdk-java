package com.sap.ai.sdk.foundationmodels.openai;

import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Represents a function type tool called by an OpenAI model.
 *
 * @since 1.6.0
 */
@Value
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class OpenAiFunctionCall implements OpenAiToolCall {
  /** The unique identifier for the function call. */
  @Nonnull String id;

  /** The name of the function to be called. */
  @Nonnull String name;

  /** The arguments for the function call, encoded as a JSON string. */
  @Nonnull String arguments;
}
