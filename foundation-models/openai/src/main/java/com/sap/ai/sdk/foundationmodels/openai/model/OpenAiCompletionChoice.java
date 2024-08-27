package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Result for OpenAI chat completion output. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
public class OpenAiCompletionChoice {
  /** Reason for finish. */
  @JsonProperty("finish_reason")
  @Getter(onMethod_ = @Nullable)
  private String finishReason;

  /** Index of choice. */
  @JsonProperty("index")
  @Getter // Nullable
  private Integer index;

  /**
   * Information about the content filtering category, if it has been detected, as well as the
   * severity level and if it has been filtered or not.
   */
  @JsonProperty("content_filter_results")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterPromptResults contentFilterResults;

  void addDelta(@Nonnull final OpenAiCompletionChoice delta) {

    if (delta.getFinishReason() != null) {
      finishReason = delta.getFinishReason();
    }

    if (delta.getIndex() != null) {
      index = delta.getIndex();
    }

    if (delta.getContentFilterResults() != null) {
      if (contentFilterResults == null) {
        contentFilterResults = new OpenAiContentFilterPromptResults();
      }
      contentFilterResults.addDelta(delta.getContentFilterResults());
    }
  }
}
