package com.sap.ai.sdk.orchestration.spring;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
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
public class OrchestrationChatResponse extends ChatResponse {

  private OrchestrationChatResponse(
      @Nonnull final List<Generation> generations, @Nonnull final ChatResponseMetadata metadata) {
    super(generations, metadata);
  }

  @Nonnull
  static OrchestrationChatResponse fromOrchestrationResponse(
      @Nonnull final CompletionPostResponse response) {
    val result = (LLMModuleResultSynchronous) response.getOrchestrationResult();
    val generations = toGenerations(result);
    val metadata = toChatResponseMetadata(result);
    return new OrchestrationChatResponse(generations, metadata);
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final LLMModuleResultSynchronous result) {
    return result.getChoices().stream()
        .map(OrchestrationChatResponse::toAssistantMessage)
        .map(Generation::new)
        .toList();
  }

  @Nonnull
  static AssistantMessage toAssistantMessage(@Nonnull final LLMChoice choice) {
    final Map<String, Object> metadata = new HashMap<>();
    metadata.put("finish_reason", choice.getFinishReason());
    metadata.put("index", choice.getIndex());
    if (!choice.getLogprobs().isEmpty()) {
      metadata.put("logprobs", choice.getLogprobs());
    }
    return new AssistantMessage(choice.getMessage().getContent(), metadata);
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
