package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ReasoningBlock;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

/** Orchestration chat completion output delta for streaming. */
@SuppressWarnings("PMD.PublicClassExtendsModelType")
public class OrchestrationChatCompletionDelta extends CompletionPostResponseStreaming
    implements StreamedDelta {

  @Nonnull
  @Override
  public String getDeltaContent() {
    val choices = getFinalResult().getChoices();
    // Avoid the first delta: "choices":[]
    if (!choices.isEmpty()
        // Multiple choices are spread out on multiple deltas
        // A delta only contains one choice with a variable index
        && choices.get(0).getIndex() == 0) {

      final var message = choices.get(0).getDelta();
      return message.getContent();
    }
    return "";
  }

  /**
   * Get the reasoning (thinking) text carried by this chunk.
   *
   * @return the reasoning text of this chunk's block; never {@code null}, may be empty.
   * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
   *     Orchestration - Reasoning</a>
   */
  @Nonnull
  public String getDeltaReasoningText() {
    val choices = getFinalResult().getChoices();
    if (!choices.isEmpty() && choices.get(0).getIndex() == 0) {
      return choices.get(0).getDelta().getReasoningContent().stream()
          .map(ReasoningBlock::getContent)
          .collect(Collectors.joining());
    }
    return "";
  }

  @Nullable
  @Override
  public String getFinishReason() {
    return getFinalResult().getChoices().get(0).getFinishReason();
  }
}
