package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nonnull;

/** Exception thrown when an error occurs during input filtering in orchestration. */
public class OrchestrationInputFilterException extends OrchestrationFilterException {
  /**
   * Constructs a new OrchestrationInputFilterException.
   *
   * @param message the detail message
   * @param clientError the specific client error
   * @param filterDetails details about the filter that caused the exception
   */
  OrchestrationInputFilterException(
      @Nonnull final String message,
      @Nonnull final OrchestrationError clientError,
      @Nonnull final Map<String, Object> filterDetails) {
    super(message);
    setClientError(clientError);
    this.filterDetails = filterDetails;
  }
}
