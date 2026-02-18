package com.sap.ai.sdk.core.common;

import javax.annotation.Nullable;

/**
 * Generic class that contains a JSON error response.
 *
 * @since 1.1.0
 */
@FunctionalInterface
public interface ClientError {
  /**
   * Get the error message.
   *
   * @return The error message
   */
  @Nullable
  String getMessage();
}
