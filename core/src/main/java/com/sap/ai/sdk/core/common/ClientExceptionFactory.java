package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A factory whose implementations can provide customized exception types and error mapping logic
 * for different service clients or error scenarios.
 *
 * @param <E> The subtype of {@link ClientException} to be created by this factory.
 * @param <R> The subtype of {@link ClientError} payload that can be processed by this factory.
 */
@Beta
public interface ClientExceptionFactory<E extends ClientException, R extends ClientError> {

  /**
   * Creates an exception with a message and optional cause.
   *
   * @param message A descriptive message for the exception.
   * @param cause An optional cause of the exception, can be null if not applicable.
   * @return An instance of the specified {@link ClientException} type
   */
  @Nonnull
  E build(@Nonnull final String message, @Nullable final Throwable cause);

  /**
   * Creates an exception with a message and optional cause.
   *
   * @param message A descriptive message for the exception.
   * @return An instance of the specified {@link ClientException} type
   */
  @Nonnull
  default E build(@Nonnull final String message) {
    return build(message, null);
  }

  /**
   * Creates an exception from a given message and an HTTP error response that has been successfully
   * deserialized into a {@link ClientError} object.
   *
   * @param message A descriptive message for the exception.
   * @param clientError The structured {@link ClientError} object deserialized from the response.
   * @return An instance of the specified {@link ClientException} type
   */
  @Nonnull
  E buildFromClientError(@Nonnull final String message, @Nonnull final R clientError);
}
