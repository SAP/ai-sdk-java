package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
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
public class OpenAiErrorBase {
  /** The error code. */
  @JsonProperty("code")
  @Getter(onMethod_ = @Nullable)
  private String code;

  /** The error message. */
  @JsonProperty("message")
  @Getter(onMethod_ = @Nullable)
  private String message;
}
