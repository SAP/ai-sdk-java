package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
public class OrchestrationClientException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param msg the error message
   */
  public OrchestrationClientException(@Nonnull final String msg) {
    super(msg);
  }

  /**
   * Constructor.
   *
   * @param msg the error message
   * @param cause the cause of the error
   */
  public OrchestrationClientException(@Nonnull final String msg, @Nonnull final Throwable cause) {
    super(msg, cause);
  }
}
