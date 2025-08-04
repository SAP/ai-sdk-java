package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
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
@Value
@EqualsAndHashCode(callSuper = true)
public class OrchestrationSpringChatResponse extends ChatResponse {

  OrchestrationChatResponse orchestrationResponse;

  OrchestrationSpringChatResponse(@Nonnull final OrchestrationChatResponse orchestrationResponse) {
    super(
        toGenerations(orchestrationResponse.getOriginalResponse().getFinalResult()),
        toChatResponseMetadata(orchestrationResponse.getOriginalResponse().getFinalResult()));
    this.orchestrationResponse = orchestrationResponse;
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final LLMModuleResult result) {
    return result.getChoices().stream().map(OrchestrationSpringChatResponse::toGeneration).toList();
  }

  @Nonnull
  static Generation toGeneration(@Nonnull final LLMChoice choice) {
    val metadata = ChatGenerationMetadata.builder().finishReason(choice.getFinishReason());
    metadata.metadata("index", choice.getIndex());
    if (choice.getLogprobs() != null && !choice.getLogprobs().getContent().isEmpty()) {
      metadata.metadata("logprobs", choice.getLogprobs().getContent());
    }

    val toolCalls =
        choice.getMessage().getToolCalls().stream()
            .map(
                toolCall ->
                    new ToolCall(
                        toolCall.getId(),
                        toolCall.getType().getValue(),
                        toolCall.getFunction().getName(),
                        toolCall.getFunction().getArguments()))
            .toList();

    val message = new AssistantMessage(choice.getMessage().getContent(), Map.of(), toolCalls);
    return new Generation(message, metadata.build());
  }

  @Nonnull
  static ChatResponseMetadata toChatResponseMetadata(
      @Nonnull final LLMModuleResult orchestrationResult) {
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
        usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
  }
}
