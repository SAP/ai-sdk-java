package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ReasoningBlock;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Accumulates reasoning ({@code reasoning_content}) blocks across the chunks of a streaming
 * orchestration response.
 *
 * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
 *     Orchestration - Reasoning</a>
 */
public class ReasoningAccumulator {

  private final List<ReasoningBlock> blocks = new ArrayList<>();

  /**
   * Consume the reasoning content carried by a single stream chunk.
   *
   * @param delta the streaming delta.
   */
  public void accept(@Nonnull final OrchestrationChatCompletionDelta delta) {
    final List<ReasoningBlock> chunk = delta.getDeltaReasoningContent();
    for (int i = 0; i < chunk.size(); i++) {
      while (blocks.size() <= i) {
        blocks.add(ReasoningBlock.create().content("").signature(""));
      }
      final ReasoningBlock target = blocks.get(i);
      final ReasoningBlock source = chunk.get(i);
      if (!source.getContent().isEmpty()) {
        target.setContent(target.getContent() + source.getContent());
      }
      if (!source.getSignature().isEmpty() && target.getSignature().isEmpty()) {
        target.setSignature(source.getSignature());
      }
    }
  }

  /**
   * Assemble the fully accumulated list of reasoning blocks, ready to be re-submitted as part of
   * {@code messages_history}.
   *
   * @return the assembled blocks, in the order they appeared in the stream.
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public List<ReasoningBlock> assemble() {
    return List.copyOf(blocks);
  }
}
