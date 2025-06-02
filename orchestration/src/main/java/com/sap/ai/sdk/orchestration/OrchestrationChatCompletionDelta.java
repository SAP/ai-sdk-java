package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.orchestration.model.ChatDelta;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponseStreaming;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

/** Orchestration chat completion output delta for streaming. */
public class OrchestrationChatCompletionDelta extends CompletionPostResponseStreaming
    implements StreamedDelta {

  @Nonnull
  @Override
  // will be fixed once the generated code add a discriminator which will allow this class to extend
  // CompletionPostResponseStreaming
  @SuppressWarnings("unchecked")
  public String getDeltaContent() {
    val choices = getOrchestrationResult().getChoices();
    // Avoid the first delta: "choices":[]
    if (!choices.isEmpty()
        // Multiple choices are spread out on multiple deltas
        // A delta only contains one choice with a variable index
        && choices.get(0).getIndex() == 0) {

      final var message =
          (Map<String, Object>) ((ChatDelta) choices.get(0).toMap().get("delta")).toMap();
      // Avoid the second delta: "choices":[{"delta":{"content":"","role":"assistant"}}]
      if (message != null && message.get("content") != null) {
        return message.get("content").toString();
      }
    }
    return "";
  }

  @Nullable
  @Override
  public String getFinishReason() {
    return getOrchestrationResult().getChoices().get(0).getFinishReason();
  }
}
