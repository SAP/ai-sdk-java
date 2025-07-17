package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Getter;

/**
 * Exception thrown when an error occurs during orchestration filtering.
 *
 * <p>This exception serves as the base for more specific filter-related exceptions in the
 * orchestration process.
 */
@Beta
public class OrchestrationFilterException extends OrchestrationClientException {

  /** Details about the filter that caused the exception. */
  @Getter @Nonnull protected Map<String, Object> filterDetails;

  public OrchestrationFilterException(
      String message, OrchestrationError clientError, @Nonnull Map<String, Object> filterDetails) {
    super(message, clientError);
    this.filterDetails = filterDetails;
  }
}
