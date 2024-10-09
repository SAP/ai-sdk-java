package com.sap.ai.sdk.orchestration.spring;

import com.google.common.base.Strings;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.model.MessageAggregator;

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
    if (!Strings.isNullOrEmpty(choice.getFinishReason())) {
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
    if (!Strings.isNullOrEmpty(completionResponse.getRequestId())) {
      metadataBuilder.withKeyValue("requestId", completionResponse.getRequestId());
    }

    if (orchestrationResponse.getCreated() != null) {
      metadataBuilder.withKeyValue("created", orchestrationResponse.getCreated());
    }

    if (!Strings.isNullOrEmpty(orchestrationResponse.getId())) {
      metadataBuilder.withId(orchestrationResponse.getId());
    }

    if (!Strings.isNullOrEmpty(orchestrationResponse.getModel())) {
      metadataBuilder.withModel(orchestrationResponse.getModel());
    }

    if (!Strings.isNullOrEmpty(orchestrationResponse.getObject())) {
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
