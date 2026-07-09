package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ReasoningBlock;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Accumulates reasoning ({@code reasoning_content}) text across the chunks of a streaming
 * orchestration response, merging the pieces of each block by their position.
 *
 * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
 *     Orchestration - Reasoning</a>
 */
public class ReasoningAccumulator {

  private final List<StringBuilder> contentByIndex = new ArrayList<>();

  /**
   * Consume the reasoning content carried by a single stream chunk.
   *
   * @param delta the streaming delta.
   */
  public void accept(@Nonnull final OrchestrationChatCompletionDelta delta) {
    final List<ReasoningBlock> chunk = rawReasoning(delta);
    for (int i = 0; i < chunk.size(); i++) {
      while (contentByIndex.size() <= i) {
        contentByIndex.add(new StringBuilder());
      }
      final String content = chunk.get(i).getContent();
      if (!content.isEmpty()) {
        contentByIndex.get(i).append(content);
      }
    }
  }

  /**
   * Assemble the fully accumulated reasoning text, one entry per reasoning block.
   *
   * @return the assembled reasoning text, in the order it appeared in the stream.
   */
  @Nonnull
  public List<String> assemble() {
    return contentByIndex.stream().map(StringBuilder::toString).toList();
  }

  @Nonnull
  private static List<ReasoningBlock> rawReasoning(
      @Nonnull final OrchestrationChatCompletionDelta delta) {
    final var choices = delta.getFinalResult().getChoices();
    if (!choices.isEmpty() && choices.get(0).getIndex() == 0) {
      return choices.get(0).getDelta().getReasoningContent();
    }
    return List.of();
  }
}
