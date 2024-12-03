package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Information about the content filtering results. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Beta
public class OpenAiContentFilterSeverityResult extends OpenAiContentFilterResultBase {
  /** Severity of the content. */
  @JsonProperty("severity")
  @Getter(onMethod_ = @Nonnull)
  private Severity severity;

  /** The severity of the content. */
  @RequiredArgsConstructor
  public enum Severity {
    /** General content or related content in generic or non-harmful contexts. */
    SAFE("safe"),

    /** Harmful content at a low intensity and risk level. */
    LOW("low"),

    /** Harmful content at a medium intensity and risk level. */
    MEDIUM("medium"),

    /** Harmful content at a high intensity and risk level. */
    HIGH("high");

    @JsonValue
    @Getter(onMethod_ = @Nonnull)
    private final String value;
  }
}
