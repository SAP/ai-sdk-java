package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI content filter detected result. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Deprecated
public class OpenAiContentFilterDetectedResult extends OpenAiContentFilterResultBase {
  /** Whether the content was detected. */
  @JsonProperty("detected")
  @Getter(onMethod = @__(@Nonnull))
  private boolean detected;
}
