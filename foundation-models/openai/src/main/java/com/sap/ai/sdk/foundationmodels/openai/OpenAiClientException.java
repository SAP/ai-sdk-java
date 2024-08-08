package com.sap.ai.sdk.foundationmodels.openai;

import java.io.Serial;
import javax.annotation.Nonnull;

/** Generic exception for errors occurring when using OpenAI foundation models. */
public class OpenAiClientException extends RuntimeException {
  @Serial private static final long serialVersionUID = -7345541120979974432L;

  /**
   * Create a new exception with the given message.
   *
   * @param message the message
   */
  public OpenAiClientException(@Nonnull final String message) {
    super(message);
  }

  /**
   * Create a new exception with the given message and cause.
   *
   * @param message the message
   * @param e the cause
   */
  public OpenAiClientException(@Nonnull final String message, @Nonnull final Exception e) {
    super(message, e);
  }
}
