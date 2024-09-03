package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Usage statistics for the completion request. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiUsage {
  /** Tokens consumed for output text completion. */
  @JsonProperty("completion_tokens")
  @Getter(onMethod_ = @Nullable)
  private Integer completionTokens;

  /** Tokens consumed for input prompt tokens. */
  @JsonProperty("prompt_tokens")
  @Getter(onMethod_ = @Nonnull)
  private Integer promptTokens;

  /** Total tokens consumed for input + output. */
  @JsonProperty("total_tokens")
  @Getter(onMethod_ = @Nonnull)
  private Integer totalTokens;

  void addDelta(@Nonnull final OpenAiUsage delta) {
    if (delta.getCompletionTokens() != null) {
      completionTokens = delta.getCompletionTokens();
    }
    promptTokens = delta.getPromptTokens();
    totalTokens = delta.getTotalTokens();
  }
}
