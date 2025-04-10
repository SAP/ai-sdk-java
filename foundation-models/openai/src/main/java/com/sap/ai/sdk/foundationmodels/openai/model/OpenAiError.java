package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.core.common.ClientError;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/** OpenAI error. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Deprecated
public class OpenAiError implements ClientError {
  /** The error object. */
  @JsonProperty("error")
  @Getter(onMethod_ = @Nullable)
  @Delegate(types = {ClientError.class})
  private OpenAiErrorBase error;
}
