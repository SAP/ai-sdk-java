package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

// TODO: discuss, what do we want to offer on the convenience API?
public record OrchestrationResponse(
    @Nonnull ChatMessage assistantMessage,
    @Nonnull List<ChatMessage> allMessages,
    @Nonnull FinishReason finishReason,
    @Nonnull TokenUsage tokenUsage,
    @Nonnull CompletionPostResponse originalResponseDto) {

  @RequiredArgsConstructor
  public enum FinishReason {
    STOP("stop"),
    MAX_TOKENS("max_tokens"),
    CONTENT_FILTER("content_filter"),
    UNKNOWN("");

    @Nonnull final String value;

    @Nonnull
    static FinishReason fromValue(@Nonnull final String value) {
      return Arrays.stream(FinishReason.values())
          .filter(it -> it.value.equalsIgnoreCase(value))
          .findAny()
          .orElse(UNKNOWN);
    }
  }

  @Nonnull
  static OrchestrationResponse fromCompletionPostResponseDTO(
      @Nonnull final CompletionPostResponse response) {
    final var choice = response.getOrchestrationResult().getChoices().get(0);
    final var message = choice.getMessage();
    final var finishReason = FinishReason.fromValue(choice.getFinishReason());
    final var tokenUsage = response.getOrchestrationResult().getUsage();
    final var allMessages = new ArrayList<>(response.getModuleResults().getTemplating());
    allMessages.add(message);
    return new OrchestrationResponse(message, allMessages, finishReason, tokenUsage, response);
  }
}
