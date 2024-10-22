package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// TODO: discuss, what do we want to offer on the convenience API?
public record OrchestrationResponse(
    @Nonnull ChatMessage assistantResponse,
    @Nonnull String finishReason,
    @Nonnull TokenUsage tokenUsage,
    @Nonnull LLMModuleResult llmModuleResult,
    @Nonnull List<ChatMessage> templatingResult,
    @Nullable GenericModuleResult inputFilterResult,
    @Nullable GenericModuleResult outputFilterResult) {
  @Nonnull
  static OrchestrationResponse fromCompletionPostResponseDTO(
      @Nonnull final CompletionPostResponse response) {
    final var choice = response.getOrchestrationResult().getChoices().get(0);
    final var message = choice.getMessage();
    final var finishReason = choice.getFinishReason();
    final var tokenUsage = response.getOrchestrationResult().getUsage();
    final var moduleResults = response.getModuleResults();
    return new OrchestrationResponse(
        message,
        finishReason,
        tokenUsage,
        moduleResults.getLlm(),
        moduleResults.getTemplating(),
        moduleResults.getInputFiltering(),
        moduleResults.getOutputFiltering());
  }
}
