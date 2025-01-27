package com.sap.ai.sdk.foundationmodels.openai;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor
public class OpenAiChatCompletionRequest {
  @Nonnull List<OpenAiMessage> messages = new ArrayList<>();

  public OpenAiChatCompletionRequest(@Nonnull final String message) {
    this.messages.add(OpenAiMessage.user(message));
  }

  public OpenAiChatCompletionRequest(
      @Nonnull final OpenAiMessage message, @Nonnull final OpenAiMessage... messages) {
    this.messages.add(message);
    this.messages.addAll(List.of(messages));
  }

  public OpenAiChatCompletionRequest(@Nonnull final List<OpenAiMessage> messages) {
    this.messages.addAll(messages);
  }
}
