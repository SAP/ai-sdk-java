package com.sap.ai.sdk.orchestration.spring;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.OrchestrationChatCompletionDelta;
import com.sap.ai.sdk.orchestration.model.LLMChoiceStreaming;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultStreaming;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import java.util.Map;
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
public class OrchestrationSpringChatDelta extends ChatResponse {

  OrchestrationSpringChatDelta(@Nonnull final OrchestrationChatCompletionDelta delta) {
    super(
        toGenerations(delta.getOrchestrationResult()),
        toChatResponseMetadata(delta.getOrchestrationResult()));
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final LLMModuleResultStreaming result) {
    return result.getChoices().stream().map(OrchestrationSpringChatDelta::toGeneration).toList();
  }

  @Nonnull
  static Generation toGeneration(@Nonnull final LLMChoiceStreaming choice) {
    val metadata = ChatGenerationMetadata.builder().finishReason(choice.getFinishReason());
    metadata.metadata("index", choice.getIndex());
    if (!choice.getLogprobs().isEmpty()) {
      metadata.metadata("logprobs", choice.getLogprobs());
    }
    return new Generation(new AssistantMessage(getContent(choice)), metadata.build());
  }

  @Nonnull
  private static String getContent(@Nonnull final LLMChoiceStreaming choice) {
    return choice.toMap().get("delta") instanceof Map<?, ?> delta
            && delta.get("content") instanceof String content
        ? content
        : "";
  }

  @Nonnull
  static ChatResponseMetadata toChatResponseMetadata(
      @Nonnull final LLMModuleResultStreaming orchestrationResult) {
    val metadataBuilder = ChatResponseMetadata.builder();

    metadataBuilder
        .id(orchestrationResult.getId())
        .model(orchestrationResult.getModel())
        .keyValue("object", orchestrationResult.getObject())
        .keyValue("created", orchestrationResult.getCreated());
    if (orchestrationResult.getUsage() != null) {
      metadataBuilder.usage(toDefaultUsage(orchestrationResult.getUsage()));
    }
    return metadataBuilder.build();
  }

  @Nonnull
  private static DefaultUsage toDefaultUsage(@Nonnull final TokenUsage usage) {
    return new DefaultUsage(
        usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
  }
}
