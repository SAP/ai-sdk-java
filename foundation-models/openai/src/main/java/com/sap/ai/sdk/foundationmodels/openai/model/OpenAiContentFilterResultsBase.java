package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Information about the content filtering results. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiContentFilterResultsBase {
  /** Sexual content filter result. */
  @JsonProperty("sexual")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterSeverityResult sexual;

  /** Violent content filter result. */
  @JsonProperty("violence")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterSeverityResult violence;

  /** Hate speech content filter result. */
  @JsonProperty("hate")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterSeverityResult hate;

  /** Intolerant content filter result. */
  @JsonProperty("self_harm")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterSeverityResult selfHarm;

  /** Profanity content filter result. */
  @JsonProperty("profanity")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterDetectedResult profanity;

  @JsonProperty("error")
  @Getter(onMethod_ = @Nullable)
  private OpenAiErrorBase error;

  void addDelta(@Nonnull final OpenAiContentFilterPromptResults delta) {
    if (delta.getSexual() != null) {
      sexual = delta.getSexual();
    }
    if (delta.getViolence() != null) {
      violence = delta.getViolence();
    }
    if (delta.getHate() != null) {
      hate = delta.getHate();
    }
    if (delta.getSelfHarm() != null) {
      selfHarm = delta.getSelfHarm();
    }
    if (delta.getProfanity() != null) {
      profanity = delta.getProfanity();
    }
    if (delta.getError() != null) {
      error = delta.getError();
    }
  }
}
