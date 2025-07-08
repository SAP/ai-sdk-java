package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.experimental.StandardException;

@StandardException
public class OrchestrationFilterException extends OrchestrationClientException {

  @Getter @Nonnull protected Map<String, Object> filterDetails;

  public static class OrchestrationInputFilterException extends OrchestrationFilterException {
    OrchestrationInputFilterException(
        String message, Throwable cause, Map<String, Object> filterDetails) {
      super(message, cause);
      this.filterDetails = filterDetails;
    }
  }

  public static class OrchestrationOutputFilterException extends OrchestrationFilterException {
    OrchestrationOutputFilterException(String message, Map<String, Object> filterDetails) {
      super(message);
      this.filterDetails = filterDetails;
    }
  }
}
