package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Knows how to turn a 4xx/5xx HTTP response into a ClientException (and subtype). */
@Beta
public interface ClientExceptionFactory<E extends ClientException, R extends ClientError> {

  /** Creates a “base” exception with a message and optional cause. */
  E create(@Nonnull final String message, @Nullable final Throwable cause);

  /**
   * Inspect the HTTP response, attempt to deserialize a JSON error payload, and return either the
   */
  E fromClientError(@Nonnull final String message, @Nonnull final R clientError);
}
