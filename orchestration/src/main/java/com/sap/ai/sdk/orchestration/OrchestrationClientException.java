package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

public class OrchestrationClientException extends RuntimeException {
  public OrchestrationClientException(@Nonnull final String msg, @Nonnull final Throwable cause) {
    super(msg, cause);
  }

  public OrchestrationClientException(@Nonnull final String msg) {
    super(msg);
  }
}
