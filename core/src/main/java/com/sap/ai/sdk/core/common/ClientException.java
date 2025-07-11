package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.experimental.StandardException;

/**
 * Generic exception for errors occurring when using AI SDK clients.
 *
 * @since 1.1.0
 */
@Beta
@StandardException
public class ClientException extends RuntimeException {
  @Nullable
  @Getter(onMethod_ = @Beta)
  public ClientError clientError;
}
