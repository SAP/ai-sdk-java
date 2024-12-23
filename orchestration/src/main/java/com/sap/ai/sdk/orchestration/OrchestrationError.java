package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.commons.ClientError;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * Orchestration error response.
 *
 * @since 1.1.0
 */
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
@Value
@Beta
public class OrchestrationError implements ClientError {
  @Delegate(types = {ClientError.class})
  ErrorResponse originalResponse;
}
