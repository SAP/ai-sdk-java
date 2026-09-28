package com.sap.ai.sdk.core.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A factory whose implementations can provide customized exception types and error mapping logic
 * for different service clients or error scenarios.
 *
 * @param <E> the subtype of {@link ClientException} to be created by this factory.
 * @param <R> the subtype of {@link ClientError} payload that can be processed by this factory.
 */
@FunctionalInterface
public interface ClientExceptionFactory<E extends ClientException, R extends ClientError> {

  /**
   * Creates an exception with a message and optional cause.
   *
   * @param message a descriptive message for the exception.
   * @param cause an optional cause of the exception. Can be {@code null} if not applicable.
   * @return an instance of the specified {@link ClientException} type.
   */
  @Nonnull
  default E build(@Nonnull final String message, @Nullable final Throwable cause) {
    return build(message, null, cause);
  }

  /**
   * Creates an exception with a message and optional cause.
   *
   * @param message a descriptive message for the exception.
   * @return an instance of the specified {@link ClientException} type.
   */
  @Nonnull
  default E build(@Nonnull final String message) {
    return build(message, null);
  }

  /**
   * Creates an exception from a given message and an HTTP error response that has been successfully
   * deserialized into a {@link ClientError} object.
   *
   * @param message a descriptive message for the exception.
   * @param clientError the structured {@link ClientError} object deserialized from the response,
   *     null if not exist.
   * @param cause an optional cause of the exception. Can be {@code null} if not applicable.
   * @return an instance of the specified {@link ClientException} type.
   */
  @Nonnull
  E build(
      @Nonnull final String message,
      @Nullable final R clientError,
      @Nullable final Throwable cause);
}
