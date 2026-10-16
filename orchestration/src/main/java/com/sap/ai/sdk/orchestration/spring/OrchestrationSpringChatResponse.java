package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationChatResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
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
import org.springframework.ai.support.UsageCalculator;

/**
 * Response from the orchestration service in a Spring AI {@link ChatResponse}.
 *
 * <p>For blocking calls that execute tools internally, the usage metadata covers every request made
 * for the call, while {@link #getOrchestrationResponse()} describes the final request only.
 *
 * @since 1.2.0
 */
@Value
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("PMD.PublicClassExtendsModelType")
public class OrchestrationSpringChatResponse extends ChatResponse {

  OrchestrationChatResponse orchestrationResponse;

  OrchestrationSpringChatResponse(@Nonnull final OrchestrationChatResponse orchestrationResponse) {
    super(
        toGenerations(orchestrationResponse.getOriginalResponse().getFinalResult()),
        toChatResponseMetadata(orchestrationResponse.getOriginalResponse().getFinalResult()));
    this.orchestrationResponse = orchestrationResponse;
  }

  private OrchestrationSpringChatResponse(
      @Nonnull final OrchestrationSpringChatResponse response,
      @Nonnull final ChatResponseMetadata metadata) {
    super(response.getResults(), metadata);
    this.orchestrationResponse = response.getOrchestrationResponse();
  }

  /**
   * Returns the response that followed this response's tool calls, with this response's usage added
   * to its usage.
   */
  @Nonnull
  ChatResponse withCumulativeUsage(@Nonnull final ChatResponse nextResponse) {
    val metadata = nextResponse.getMetadata();
    val builder =
        ChatResponseMetadata.builder()
            .id(metadata.getId())
            .model(metadata.getModel())
            .rateLimit(metadata.getRateLimit())
            .promptMetadata(metadata.getPromptMetadata())
            .usage(UsageCalculator.getCumulativeUsage(metadata.getUsage(), this));
    metadata.entrySet().forEach(entry -> builder.keyValue(entry.getKey(), entry.getValue()));
    if (nextResponse instanceof OrchestrationSpringChatResponse response) {
      return new OrchestrationSpringChatResponse(response, builder.build());
    }
    return new ChatResponse(nextResponse.getResults(), builder.build());
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

    val message =
        AssistantMessage.builder()
            .content(choice.getMessage().getContent())
            .toolCalls(toolCalls)
            .build();
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
