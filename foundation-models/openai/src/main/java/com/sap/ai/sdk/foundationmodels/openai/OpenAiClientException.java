package com.sap.ai.sdk.foundationmodels.openai;

import java.io.Serial;
import javax.annotation.Nonnull;

/** Generic exception for errors occurring when using OpenAI foundation models. */
public class OpenAiClientException extends RuntimeException {
  static final String BASE_ERROR_MESSAGE = "Request to OpenAI model failed";
  @Serial private static final long serialVersionUID = -7345541120979974432L;

  /** Create a new exception with the base message: {@code Request to OpenAI model failed} */
  public OpenAiClientException() {
    super(BASE_ERROR_MESSAGE);
  }

  /**
   * Create a new exception with the base message: {@code Request to OpenAI model failed}
   *
   * @param e the cause
   */
  public OpenAiClientException(@Nonnull final Exception e) {
    super(BASE_ERROR_MESSAGE, e);
  }

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
