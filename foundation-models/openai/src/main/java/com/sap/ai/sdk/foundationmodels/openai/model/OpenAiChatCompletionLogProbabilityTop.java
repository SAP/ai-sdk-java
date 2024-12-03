package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Log probability information for the choice. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAiChatCompletionLogProbabilityTop extends OpenAiChatCompletionLogProbabilityToken {
  /**
   * List of the most likely tokens and their log probability, at this token position. In rare
   * cases, there may be fewer than the number of requested `top_logprobs` returned.
   */
  @JsonProperty("top_logprobs")
  private List<OpenAiChatCompletionLogProbabilityToken> top_logprobs;
}
