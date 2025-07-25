package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.StandardException;

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
}
