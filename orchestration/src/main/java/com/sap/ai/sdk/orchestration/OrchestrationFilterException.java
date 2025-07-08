package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Getter;

public class OrchestrationFilterException extends OrchestrationClientException {

  @Getter @Nonnull private final FilterLocation location;
  @Getter @Nonnull private final Map<String, Object> filterDetails;

  public enum FilterLocation {
    INPUT_FILTER,
    OUTPUT_FILTER
  }

  public OrchestrationFilterException(
      String message, Throwable cause, FilterLocation location, Map<String, Object> filterDetails) {
    super(message, cause);
    this.location = location;
    this.filterDetails = filterDetails;
  }

  public OrchestrationFilterException(
      String message, FilterLocation location, Map<String, Object> filterDetails) {
    this(message, null, location, filterDetails);
  }
}
