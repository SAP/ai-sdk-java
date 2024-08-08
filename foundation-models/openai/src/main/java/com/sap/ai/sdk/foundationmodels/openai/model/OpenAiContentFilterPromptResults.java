package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Content filtering results for a prompt in the request. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAiContentFilterPromptResults extends OpenAiContentFilterResultsBase {
  @JsonProperty("jailbreak")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterDetectedResult jailbreak;
}
