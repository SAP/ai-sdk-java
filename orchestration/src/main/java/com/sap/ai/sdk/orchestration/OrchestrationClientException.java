package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonProcessingException;

public class OrchestrationClientException extends RuntimeException {
  public OrchestrationClientException(String msg, Exception cause) {
    super(msg, cause);
  }

  public OrchestrationClientException(String msg) {
    super(msg);
  }
}
