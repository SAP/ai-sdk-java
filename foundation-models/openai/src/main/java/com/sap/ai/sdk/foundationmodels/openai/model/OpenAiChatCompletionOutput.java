package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI chat completion output. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpenAiChatCompletionOutput extends OpenAiCompletionOutput
    implements DeltaAggregatable<OpenAiDeltaChatCompletion> {
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
   * Add a streamed delta to the total output.
   *
   * @param delta the delta to add.
   */
  public void addDelta(OpenAiDeltaChatCompletion delta) {
    super.addDelta(delta);

    if (delta.getSystemFingerprint() != null) {
      systemFingerprint = delta.getSystemFingerprint();
    }

    if (!delta.getChoices().isEmpty()) {
      if (choices == null) {
        choices = new ArrayList<>();
      }
      // Multiple choices are spread out on multiple deltas
      // A delta only contains one choice with a variable index
      int index = delta.getChoices().get(0).getIndex();
      for (int i = choices.size(); i < index + 1; i++) {
        choices.add(new OpenAiChatCompletionChoice());
      }
      choices.get(index).addDelta(delta.getChoices().get(0));
    }
  }
}
