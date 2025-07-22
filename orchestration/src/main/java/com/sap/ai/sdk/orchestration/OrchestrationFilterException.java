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
@StandardException(access = AccessLevel.PROTECTED)
public class OrchestrationFilterException extends OrchestrationClientException {

  /** Details about the filter that caused the exception. */
  @Getter @Nonnull protected Map<String, Object> filterDetails = Map.of();
}
