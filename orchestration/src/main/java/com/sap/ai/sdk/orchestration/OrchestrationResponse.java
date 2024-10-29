package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Represents the response from the orchestration service.
 *
 * @param assistantMessage The fully processed message from the language model.
 * @param allMessages All input messages after processing and the assistant message.
 * @param finishReason The reason why the assistant message finished.
 * @param tokenUsage The token usage details.
 * @param originalResponseDto The underlying response object, containing all orchestration module
 *     result details.
 */
public record OrchestrationResponse(
    @Nonnull AssistantMessage assistantMessage,
    @Nonnull List<Message> allMessages,
    @Nonnull FinishReason finishReason,
    @Nonnull TokenUsage tokenUsage,
    @Nonnull CompletionPostResponse originalResponseDto) {

  /** The reason why an assistant message finished. */
  @RequiredArgsConstructor
  public enum FinishReason {
    /** The assistant message finished normally. */
    STOP("stop"),
    /** The generated message is cut off because the maximum number of output tokens was reached. */
    LENGTH("length"),
    /** The generated message was filtered out by an output content filter. */
    CONTENT_FILTER("content_filter"),
    /** The assistant message finished with a non-standardized reason. */
    UNKNOWN("unknown");

    /** String representation of the finish reason. */
    @Nonnull private final String value;

    @Nonnull
    static FinishReason fromValue(@Nonnull final String value) {
      return Arrays.stream(FinishReason.values())
          .filter(it -> it.value.equalsIgnoreCase(value))
          .findAny()
          .orElse(UNKNOWN);
    }
  }

  @Nonnull
  static OrchestrationResponse fromCompletionPostResponseDto(
      @Nonnull final CompletionPostResponse response) {
    val choice = response.getOrchestrationResult().getChoices().get(0);
    val message = new AssistantMessage(choice.getMessage().getContent());
    val finishReason = FinishReason.fromValue(choice.getFinishReason());
    val tokenUsage = response.getOrchestrationResult().getUsage();
    val allMessages = new ArrayList<Message>();
    response.getModuleResults().getTemplating().stream()
        .map(OrchestrationResponse::fromChatMessage)
        .forEach(allMessages::add);
    allMessages.add(message);
    return new OrchestrationResponse(message, allMessages, finishReason, tokenUsage, response);
  }

  @Nonnull
  static Message fromChatMessage(@Nonnull final ChatMessage chatMessage) {
    return switch (chatMessage.getRole()) {
      case "system" -> new SystemMessage(chatMessage.getContent());
      case "user" -> new UserMessage(chatMessage.getContent());
      case "assistant" -> new AssistantMessage(chatMessage.getContent());
      default ->
          new Message() {
            @Nonnull
            @Override
            public String type() {
              return chatMessage.getRole();
            }

            @Nullable
            @Override
            public String content() {
              return chatMessage.getContent();
            }
          };
    };
  }
}
