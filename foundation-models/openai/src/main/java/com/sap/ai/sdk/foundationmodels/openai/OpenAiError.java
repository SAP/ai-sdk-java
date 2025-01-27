package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientError;
import com.sap.ai.sdk.foundationmodels.openai.model2.ErrorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

@Beta
@Value
@AllArgsConstructor(onConstructor = @__({@JsonCreator}), access = AccessLevel.PROTECTED)
public class OpenAiError implements ClientError {
  @Delegate(types = {ClientError.class})
  ErrorResponse originalResponse;

  public String getMessage() {
    return originalResponse.getError().getMessage();
  }
}
