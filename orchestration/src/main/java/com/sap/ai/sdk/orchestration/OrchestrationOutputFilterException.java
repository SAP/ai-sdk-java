package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nonnull;

/** Exception thrown output filtering in orchestration when finish reason is content filter */
public class OrchestrationOutputFilterException extends OrchestrationFilterException {
  /**
   * Constructs a new OrchestrationOutputFilterException.
   *
   * @param message the detail message
   * @param filterDetails details about the filter that caused the exception
   */
  OrchestrationOutputFilterException(
      @Nonnull final String message, @Nonnull final Map<String, Object> filterDetails) {
    super(message);
    this.filterDetails = filterDetails;
  }
}
