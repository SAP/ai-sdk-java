package com.sap.ai.sdk.core.commons;

import javax.annotation.Nonnull;

/** Generic class that contains a JSON error response. */
public interface ClientError {
  /**
   * Get the error message.
   *
   * @return The error message
   */
  @Nonnull
  String getMessage();
}
