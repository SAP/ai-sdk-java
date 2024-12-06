package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Log probability information for the choice. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiChatCompletionLogProbabilityToken {
  /** The token. */
  @JsonProperty("token")
  private String token;

  /** The log probability of this token. */
  @JsonProperty("logprob")
  private Number logprob;

  /** A list of integers representing the UTF-8 bytes representation of the token. */
  @JsonProperty("bytes")
  @Nullable
  private int[] bytes;
}
