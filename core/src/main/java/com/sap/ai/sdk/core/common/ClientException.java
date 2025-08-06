package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
  @Getter(onMethod_ = @Beta, value = AccessLevel.PROTECTED)
  @Setter(onMethod_ = @Beta, value = AccessLevel.PROTECTED)
  ClientError clientError;

  /**
   * The original HTTP response that caused this exception, if available.
   *
   * @since 1.10.0
   */
  @Nullable
  @Getter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  @Setter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  @Accessors(chain = true)
  ClassicHttpResponse httpResponse;

  /**
   * The original HTTP request that caused this exception, if available.
   *
   * @since 1.10.0
   */
  @Nullable
  @Getter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  @Setter(onMethod_ = @Beta, value = AccessLevel.PUBLIC)
  @Accessors(chain = true)
  ClassicHttpRequest httpRequest;
}
