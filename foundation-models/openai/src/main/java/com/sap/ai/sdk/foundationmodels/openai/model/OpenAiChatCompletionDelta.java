package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI chat completion output delta for streaming. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAiChatCompletionDelta extends OpenAiCompletionOutput implements StreamedDelta
{
  /** List of result candidates. */
  @JsonProperty("choices")
  @Getter(onMethod_ = @Nonnull)
  private List<OpenAiChatCompletionChoice> choices;

  /**
   * Can be used in conjunction with the seed request parameter to understand when backend changes
   * have been made that might impact determinism.
   */
  @JsonProperty("system_fingerprint")
  @Getter(onMethod_ = @Nonnull)
  private String systemFingerprint;

  /**
   * Get the message content from the delta.
   *
   * @return the message content or null.
   */
  @Nullable
  public String getDeltaContent() {
    // Avoid the first delta: "choices":[]
    if (!getChoices().isEmpty()
        // Multiple choices are spread out on multiple deltas
        // A delta only contains one choice with a variable index
        && getChoices().get(0).getIndex() == 0
        // Avoid the second delta: "choices":[{"delta":{"content":"","role":"assistant"}}]
        && getChoices().get(0).getMessage() != null
        // Avoid the last delta "choices":[{"delta":{}}]
        && getChoices().get(0).getMessage().getContent() != null) {

      return getChoices().get(0).getMessage().getContent();
    }
    return null;
  }
}
