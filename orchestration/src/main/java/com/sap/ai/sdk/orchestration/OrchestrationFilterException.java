package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.experimental.StandardException;

@StandardException
public class OrchestrationFilterException extends OrchestrationClientException {

  @Getter @Nullable private FilterLocation location;

  enum FilterLocation {
    INPUT_FILTER,
    OUTPUT_FILTER
  }

  public OrchestrationFilterException(
      String message, Throwable cause, OrchestrationError clientError) {
    super(message, cause);
    this.clientError = clientError;

    if (clientError.getOriginalResponse().getLocation().equals("Filtering Module - Input Filter")) {
      this.location = FilterLocation.INPUT_FILTER;
    } else {
      this.location = FilterLocation.OUTPUT_FILTER;
    }
  }

  @Nullable
  public Map<String, Object> getFilteringReason() {
    if (getClientError() != null) {
      var moduleResult =
          ((OrchestrationError) getClientError()).getOriginalResponse().getModuleResults();

      if (this.location == FilterLocation.INPUT_FILTER) {
        return (Map<String, Object>) moduleResult.getInputFiltering().getData();
      } else {
        return (Map<String, Object>) moduleResult.getOutputFiltering().getData();
      }
    }
    return null;
  }
}
