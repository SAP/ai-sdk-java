package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.commons.ClientError;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI error. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiError implements ClientError {
  /** The error object. */
  @JsonProperty("error")
  @Getter(onMethod_ = @Nullable)
  private OpenAiErrorBase error;

  @Nonnull
  @Override
  public String getMessage() {
    final String message = error.getMessage();
    return message == null ? "" : message;
  }
}
