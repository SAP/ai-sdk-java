package com.sap.ai.sdk.orchestration;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Accumulates reasoning ({@code reasoning_content}) items across the chunks of a streaming
 * orchestration response.
 *
 * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
 *     Orchestration - Reasoning</a>
 */
public class ReasoningAccumulator {

  private final List<StringBuilder> contentByIndex = new ArrayList<>();
  private final List<String> signatureByIndex = new ArrayList<>();

  /**
   * Consume the reasoning content carried by a single stream chunk.
   *
   * @param delta the streaming delta.
   */
  public void accept(@Nonnull final OrchestrationChatCompletionDelta delta) {
    final List<ReasoningItem> chunk = delta.getDeltaReasoningContent();
    for (int i = 0; i < chunk.size(); i++) {
      while (contentByIndex.size() <= i) {
        contentByIndex.add(new StringBuilder());
        signatureByIndex.add("");
      }
      final ReasoningItem source = chunk.get(i);
      if (!source.content().isEmpty()) {
        contentByIndex.get(i).append(source.content());
      }
      if (!source.signature().isEmpty() && signatureByIndex.get(i).isEmpty()) {
        signatureByIndex.set(i, source.signature());
      }
    }
  }

  /**
   * Assemble the fully accumulated list of reasoning items, ready to be re-submitted as part of
   * {@code messages_history}.
   *
   * @return the assembled items, in the order they appeared in the stream.
   */
  @Nonnull
  public List<ReasoningItem> assemble() {
    final var result = new ArrayList<ReasoningItem>(contentByIndex.size());
    for (int i = 0; i < contentByIndex.size(); i++) {
      result.add(new ReasoningItem(contentByIndex.get(i).toString(), signatureByIndex.get(i)));
    }
    return result;
  }
}
