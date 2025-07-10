package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.ClientException;
import javax.annotation.Nullable;

import lombok.Getter;
import lombok.experimental.StandardException;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
public class OrchestrationClientException extends ClientException {
  @Getter @Nullable protected OrchestrationError clientError;

  OrchestrationClientException(OrchestrationError clientError) {
    super(clientError.getMessage());
    this.clientError = clientError;
  }
}
