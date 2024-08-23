package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Content filtering results for a single prompt in the request. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
public class OpenAiPromptFilterResult {
  /** Index of the prompt in the request. */
  @JsonProperty("prompt_index")
  @Getter(onMethod_ = @Nullable)
  private Integer promptIndex;

  /**
   * Information about the content filtering category, if it has been detected, as well as the
   * severity level and if it has been filtered or not.
   */
  @JsonProperty("content_filter_results")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterPromptResults contentFilterResults;
}
