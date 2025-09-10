package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.core.common.StreamedDelta;
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
@ToString(callSuper = true)
@Deprecated
public class OpenAiChatCompletionDelta extends OpenAiCompletionOutput implements StreamedDelta {
  /** List of result candidates. */
  @JsonProperty("choices")
  @Getter(onMethod_ = @Nonnull)
  private List<OpenAiDeltaChatCompletionChoice> choices;

  /**
   * Can be used in conjunction with the seed request parameter to understand when backend changes
   * have been made that might impact determinism.
   */
  @JsonProperty("system_fingerprint")
  @Getter(onMethod_ = @Nullable)
  private String systemFingerprint;

  @Nonnull
  @Override
  public String getDeltaContent() {
    // Avoid the first delta: "choices":[]
    if (!getChoices().isEmpty()
        // Multiple choices are spread out on multiple deltas
        // A delta only contains one choice with a variable index
        && getChoices().get(0).getIndex() == 0) {

      final var message = getChoices().get(0).getMessage();
      // Avoid the second delta: "choices":[{"delta":{"content":"","role":"assistant"}}]
      if (message != null && message.getContent() != null) {
        return message.getContent();
      }
    }
    return "";
  }

  @Nullable
  @Override
  public String getFinishReason() {
    // Avoid the first delta: "choices":[]
    if (!getChoices().isEmpty()
        // Multiple choices are spread out on multiple deltas
        // A delta only contains one choice with a variable index
        && getChoices().get(0).getIndex() == 0) {
      return getChoices().get(0).getFinishReason();
    }
    return null;
  }

  @Override
  public boolean isError() {
    return false;
  }
}
