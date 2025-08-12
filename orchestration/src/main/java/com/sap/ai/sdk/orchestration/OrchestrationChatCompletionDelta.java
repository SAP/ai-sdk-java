package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponseStreaming;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

/** Orchestration chat completion output delta for streaming. */
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

  @Nullable
  @Override
  public String getFinishReason() {
    return getFinalResult().getChoices().get(0).getFinishReason();
  }
}
