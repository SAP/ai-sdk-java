package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Information about the content filtering results. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
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

  void addDelta(OpenAiContentFilterPromptResults delta) {
    if (delta.getSexual() != null) {
      sexual = delta.getSexual();
      System.out.println(sexual.getSeverity());
      System.out.println(sexual.isFiltered());
    } else {
      System.out.println("Sexual is null");
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
