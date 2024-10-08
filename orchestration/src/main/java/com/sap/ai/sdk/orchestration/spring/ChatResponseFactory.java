package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.model.MessageAggregator;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Radu
 */
class ChatResponseFactory {

  // TODO: Should we offer an OrchestrationChatResponse class that extends ChatResponse?

  static ChatResponse toChatResponse(@Nonnull final CompletionPostResponse response) {
    final var generations =
        response.getOrchestrationResult().getChoices().stream()
            .map(ChatResponseFactory::convertToAssistantMessage)
            .map(Generation::new)
            .toList();

    final var metadata = buildMetadata(response);
    return new ChatResponse(generations, metadata);
  }

  @Nonnull
  private static AssistantMessage convertToAssistantMessage(@Nonnull final LLMChoice choice) {
    // TODO: Test
    Map<String, Object> metadata = new HashMap<>();
    if (StringUtils.isNotBlank(choice.getFinishReason())) {
      metadata.put("finish_reason", choice.getFinishReason());
    }
    if (choice.getIndex() != null) {
      metadata.put("index", choice.getIndex().toString());
    }
    if (choice.getLogprobs() != null && !choice.getLogprobs().isEmpty()) {
      metadata.put("logprobs", choice.getLogprobs().toString());
    }
    return new AssistantMessage(choice.getMessage().getContent(), metadata);
  }

  @Nonnull
  private static ChatResponseMetadata buildMetadata(
      @Nonnull final CompletionPostResponse completionResponse) {
    var metadataBuilder = ChatResponseMetadata.builder();

    var orchestrationResponse = completionResponse.getOrchestrationResult();

    // TODO: Test
    if (StringUtils.isNotBlank(completionResponse.getRequestId())) {
      metadataBuilder.withKeyValue("requestId", completionResponse.getRequestId());
    }

    if (orchestrationResponse.getCreated() != null) {
      metadataBuilder.withKeyValue("created", orchestrationResponse.getCreated());
    }

    if (StringUtils.isNotBlank(orchestrationResponse.getId())) {
      metadataBuilder.withId(orchestrationResponse.getId());
    }

    if (StringUtils.isNotBlank(orchestrationResponse.getModel())) {
      metadataBuilder.withModel(orchestrationResponse.getModel());
    }

    if (StringUtils.isNotBlank(orchestrationResponse.getObject())) {
      metadataBuilder.withKeyValue("object", orchestrationResponse.getObject());
    }

    if (orchestrationResponse.getUsage() != null) {
      var usage = orchestrationResponse.getUsage();
      metadataBuilder.withUsage(
          new MessageAggregator.DefaultUsage(
              usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens()));
    }
    metadataBuilder.withKeyValue(
        "orchestrationModuleResults", completionResponse.getModuleResults());

    return metadataBuilder.build();
  }
}
