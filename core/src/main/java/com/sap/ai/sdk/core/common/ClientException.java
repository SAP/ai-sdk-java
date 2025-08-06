package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.StandardException;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;

/**
 * Generic exception for errors occurring when using AI SDK clients.
 *
 * @since 1.1.0
 */
@Beta
@StandardException
public class ClientException extends RuntimeException {

  /**
   * Wraps a structured error payload received from the remote service, if available. This can be
   * used to extract more detailed error information.
   */
  @Nullable
  @Getter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  private ClientError clientError;

  /**
   * The original HTTP response that caused this exception, if available.
   *
   * @since 1.10.0
   */
  @Nullable
  @Getter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  private ClassicHttpResponse httpResponse;

  /**
   * The original HTTP request that caused this exception, if available.
   *
   * @since 1.10.0
   */
  @Nullable
  @Getter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  private ClassicHttpRequest httpRequest;

  /**
   * Sets the original HTTP request that caused this exception.
   *
   * @param clientError the original structured error payload received from the remote service, can
   *     be null if not available.
   * @return the current instance of {@link ClientException} with the changed ClientError data
   * @param <T> the type of the exception, typically a subclass of {@link ClientException}
   */
  @SuppressWarnings("unchecked")
  @Nonnull
  public <T extends ClientException> T setClientError(@Nullable final ClientError clientError) {
    this.clientError = clientError;
    return (T) this;
  }

  /**
   * Sets the original HTTP request that caused this exception.
   *
   * @param httpResponse the original HTTP response that caused this exception, can be null if not
   *     available.
   * @return the current instance of {@link ClientException} with the changed HTTP response
   * @param <T> the type of the exception, typically a subclass of {@link ClientException}
   */
  @SuppressWarnings("unchecked")
  @Nonnull
  public <T extends ClientException> T setHttpResponse(
      @Nullable final ClassicHttpResponse httpResponse) {
    this.httpResponse = httpResponse;
    return (T) this;
  }

  /**
   * Sets the original HTTP request that caused this exception.
   *
   * @param httpRequest the original HTTP request that caused this exception, can be null if not
   *     available.
   * @return the current instance of {@link ClientException} with the changed HTTP request
   * @param <T> the type of the exception, typically a subclass of {@link ClientException}
   */
  @SuppressWarnings("unchecked")
  @Nonnull
  public <T extends ClientException> T setHttpRequest(
      @Nullable final ClassicHttpRequest httpRequest) {
    this.httpRequest = httpRequest;
    return (T) this;
  }
}
