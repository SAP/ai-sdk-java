package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClientException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    implements DeltaAggregatable<OpenAiChatCompletionDelta> {
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
   * Get the message content from the output.
   *
   * <p>Note: If there are multiple choices only the first one is returned
   *
   * @return the message content or empty string.
   * @throws OpenAiClientException if the content filter filtered the output.
   */
  @Nonnull
  public String getContent() throws OpenAiClientException {
    //    We expect choices to be defined and never empty.
    if ("content_filter".equals(getChoices().get(0).getFinishReason())) {
      throw new OpenAiClientException("Content filter filtered the output.");
    }
    return Objects.requireNonNullElse(getChoices().get(0).getMessage().getContent(), "");
  }

  @Override
  public void addDelta(@Nonnull final OpenAiChatCompletionDelta delta) {
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
      final int index = delta.getChoices().get(0).getIndex();
      for (int i = choices.size(); i < index + 1; i++) {
        choices.add(new OpenAiChatCompletionChoice());
      }
      choices.get(index).addDelta(delta.getChoices().get(0));
    }
  }
}
