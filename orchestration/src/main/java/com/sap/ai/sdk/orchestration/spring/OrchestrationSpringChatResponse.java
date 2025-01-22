package com.sap.ai.sdk.orchestration.spring;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

/**
 * Response from the orchestration service in a Spring AI {@link ChatResponse}.
 *
 * @since 1.2.0
 */
@Beta
@Value
@EqualsAndHashCode(callSuper = true)
public class OrchestrationSpringChatResponse extends ChatResponse {

  OrchestrationChatResponse orchestrationResponse;

  OrchestrationSpringChatResponse(@Nonnull final OrchestrationChatResponse orchestrationResponse) {
    super(
        toGenerations(
            (LLMModuleResultSynchronous)
                orchestrationResponse.getOriginalResponse().getOrchestrationResult()),
        toChatResponseMetadata(
            (LLMModuleResultSynchronous)
                orchestrationResponse.getOriginalResponse().getOrchestrationResult()));
    this.orchestrationResponse = orchestrationResponse;
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final LLMModuleResultSynchronous result) {
    return result.getChoices().stream().map(OrchestrationSpringChatResponse::toGeneration).toList();
  }

  @Nonnull
  static Generation toGeneration(@Nonnull final LLMChoice choice) {
    val metadata = ChatGenerationMetadata.builder().finishReason(choice.getFinishReason());
    metadata.metadata("index", choice.getIndex());
    if (!choice.getLogprobs().isEmpty()) {
      metadata.metadata("logprobs", choice.getLogprobs());
    }
    val message = new AssistantMessage(choice.getMessage().getContent());
    return new Generation(message, metadata.build());
  }

  @Nonnull
  static ChatResponseMetadata toChatResponseMetadata(
      @Nonnull final LLMModuleResultSynchronous orchestrationResult) {
    val metadataBuilder = ChatResponseMetadata.builder();

    metadataBuilder
        .id(orchestrationResult.getId())
        .model(orchestrationResult.getModel())
        .keyValue("object", orchestrationResult.getObject())
        .keyValue("created", orchestrationResult.getCreated())
        .usage(toDefaultUsage(orchestrationResult.getUsage()));

    return metadataBuilder.build();
  }

  @Nonnull
  private static DefaultUsage toDefaultUsage(@Nonnull final TokenUsage usage) {
    return new DefaultUsage(
        usage.getPromptTokens().longValue(),
        usage.getCompletionTokens().longValue(),
        usage.getTotalTokens().longValue());
  }
}
