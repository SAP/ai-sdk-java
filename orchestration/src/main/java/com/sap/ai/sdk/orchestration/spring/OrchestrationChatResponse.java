package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.ModuleResults;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

@Value
@EqualsAndHashCode(callSuper = true)
public class OrchestrationChatResponse extends ChatResponse {
  @Nonnull ModuleResults moduleResults;

  private OrchestrationChatResponse(
      @Nonnull final List<Generation> generations,
      @Nonnull final ChatResponseMetadata metadata,
      @Nonnull final ModuleResults moduleResults) {
    super(generations, metadata);
    this.moduleResults = moduleResults;
  }

  @Nonnull
  static OrchestrationChatResponse fromOrchestrationResponse(
      @Nonnull final CompletionPostResponse response) {
    final var generations = toGenerations(response.getOrchestrationResult());

    final var metadata = toChatResponseMetadata(response.getOrchestrationResult());
    return new OrchestrationChatResponse(generations, metadata, response.getModuleResults());
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final LLMModuleResult result) {
    return result.getChoices().stream()
        .map(OrchestrationChatResponse::toAssistantMessage)
        .map(Generation::new)
        .toList();
  }

  @Nonnull
  static AssistantMessage toAssistantMessage(@Nonnull final LLMChoice choice) {
    Map<String, Object> metadata = new HashMap<>();
    metadata.put("finish_reason", choice.getFinishReason());
    metadata.put("index", choice.getIndex());
    if (!choice.getLogprobs().isEmpty()) {
      metadata.put("logprobs", choice.getLogprobs());
    }
    return new AssistantMessage(choice.getMessage().getContent(), metadata);
  }

  @Nonnull
  static ChatResponseMetadata toChatResponseMetadata(
      @Nonnull final LLMModuleResult orchestrationResult) {
    var metadataBuilder = ChatResponseMetadata.builder();

    metadataBuilder.withId(orchestrationResult.getId());
    metadataBuilder.withModel(orchestrationResult.getModel());
    metadataBuilder.withKeyValue("object", orchestrationResult.getObject());
    metadataBuilder.withKeyValue("created", orchestrationResult.getCreated());
    metadataBuilder.withUsage(toDefaultUsage(orchestrationResult.getUsage()));

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
