package com.sap.ai.sdk.core.commons;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/**
 * Generic class that contains a JSON error response.
 *
 * @since 1.1.0
 */
@Beta
@FunctionalInterface
public interface ClientError {
  /**
   * Get the error message.
   *
   * @return The error message
   */
  @Nonnull
  String getMessage();
}
