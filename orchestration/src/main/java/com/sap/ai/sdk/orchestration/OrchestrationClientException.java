package com.sap.ai.sdk.orchestration;

public class OrchestrationClientException extends RuntimeException {
  public OrchestrationClientException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public OrchestrationClientException(String msg) {
    super(msg);
  }
}
