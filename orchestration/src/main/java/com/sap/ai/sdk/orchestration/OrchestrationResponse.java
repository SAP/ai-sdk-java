package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import javax.annotation.Nonnull;

/** Orchestration chat completion output. */
public class OrchestrationResponse extends CompletionPostResponse {
  /**
   * Get the message content from the output.
   *
   * <p>Note: If there are multiple choices only the first one is returned
   *
   * @return the message content or empty string.
   * @throws OrchestrationClientException if the content filter filtered the output.
   */
  @Nonnull
  public String getContent() throws OrchestrationClientException {
    if (getOrchestrationResult().getChoices().isEmpty()) {
      return "";
    }
    if ("content_filter".equals(getOrchestrationResult().getChoices().get(0).getFinishReason())) {
      throw new OrchestrationClientException("Content filter filtered the output.");
    }
    return getOrchestrationResult().getChoices().get(0).getMessage().getContent();
  }
}
