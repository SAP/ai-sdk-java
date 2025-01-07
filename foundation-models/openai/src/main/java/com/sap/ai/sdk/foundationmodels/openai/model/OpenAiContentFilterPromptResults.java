package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** Content filtering results for a prompt in the request. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Beta
public class OpenAiContentFilterPromptResults extends OpenAiContentFilterResultsBase {
  @JsonProperty("jailbreak")
  @Getter(onMethod_ = @Nullable)
  private OpenAiContentFilterDetectedResult jailbreak;

  @Override
  void addDelta(@Nonnull final OpenAiContentFilterPromptResults delta) {
    super.addDelta(delta);

    if (delta.getJailbreak() != null) {
      jailbreak = delta.getJailbreak();
    }
  }
}
