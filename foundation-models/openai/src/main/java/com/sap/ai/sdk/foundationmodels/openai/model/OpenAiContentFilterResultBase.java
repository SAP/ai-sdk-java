package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI content filter result. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Beta
public class OpenAiContentFilterResultBase {
  /** Whether the content was filtered. */
  @JsonProperty("filtered")
  @Getter(onMethod_ = @Nonnull)
  private boolean filtered;
}
