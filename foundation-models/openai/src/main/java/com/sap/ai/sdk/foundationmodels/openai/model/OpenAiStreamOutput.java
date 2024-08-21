package com.sap.ai.sdk.foundationmodels.openai.model;

import javax.annotation.Nullable;

/** OpenAI chat completion output for streaming. */
public class OpenAiStreamOutput extends OpenAiChatCompletionOutput {
  /**
   * Get the message content from the delta.
   *
   * @return the message content from the delta or null.
   */
  @Nullable
  public String getDeltaContent() {
    // Check if choices: []
    if (!getChoices().isEmpty()
        // Check if "choices":[{"delta":{"content":"","role":"assistant"}}]
        && getChoices().get(0).getMessage() != null
        // Check if "choices":[{"delta":{}}]
        && getChoices().get(0).getMessage().getContent() != null) {

      return getChoices().get(0).getMessage().getContent();
    }
    return null;
  }
}
