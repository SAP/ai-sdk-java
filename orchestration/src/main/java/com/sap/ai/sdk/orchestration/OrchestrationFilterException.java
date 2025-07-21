package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.StandardException;

/**
 * Exception thrown when an error occurs during orchestration filtering.
 *
 * <p>This exception serves as the base for more specific filter-related exceptions in the
 * orchestration process.
 */
@Beta
@StandardException(access = AccessLevel.PRIVATE)
public class OrchestrationFilterException extends OrchestrationClientException {

  /** Details about the filter that caused the exception. */
  @Getter @Nonnull protected Map<String, Object> filterDetails = Map.of();

  /** Exception thrown when an error occurs during input filtering in orchestration. */
  public static class OrchestrationInputFilterException extends OrchestrationFilterException {
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
      this.clientError = clientError;
      this.filterDetails = filterDetails;
    }
  }

  /** Exception thrown output filtering in orchestration when finish reason is content filter */
  public static class OrchestrationOutputFilterException extends OrchestrationFilterException {
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
}
