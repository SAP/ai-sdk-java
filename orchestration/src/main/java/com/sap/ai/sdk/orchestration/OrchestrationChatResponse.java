package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.PACKAGE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;

/** Orchestration chat completion output. */
@Value
@RequiredArgsConstructor(access = PACKAGE)
public class OrchestrationChatResponse {
  CompletionPostResponse originalResponse;

  /**
   * Get the message content from the output.
   *
   * <p>Note: If there are multiple choices only the first one is returned
   *
   * @return the message content or empty string.
   * @throws OrchestrationFilterException.Output if the content filter filtered the output.
   */
  @Nonnull
  public String getContent() throws OrchestrationFilterException.Output {
    final var choice = getChoice();

    if ("content_filter".equals(choice.getFinishReason())) {
      final var filterDetails = Try.of(this::getOutputFilteringChoices).getOrElseGet(e -> Map.of());
      final var message = "Content filter filtered the output.";
      throw new OrchestrationFilterException.Output(message, filterDetails);
    }
    return choice.getMessage().getContent();
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> getOutputFilteringChoices() {
    final var f = getOriginalResponse().getIntermediateResults().getOutputFiltering();
    return ((List<Map<String, Object>>) ((Map<String, Object>) f.getData()).get("choices")).get(0);
  }

  /**
   * Get the token usage.
   *
   * @return The token usage.
   */
  @Nonnull
  public TokenUsage getTokenUsage() {
    return originalResponse.getFinalResult().getUsage();
  }

  /**
   * Get all messages. This can be used for subsequent prompts as a message history.
   *
   * @throws IllegalArgumentException if the MultiChatMessage type message in chat.
   * @return A list of all messages.
   */
  @Nonnull
  public List<Message> getAllMessages() throws IllegalArgumentException {
    val messages = new ArrayList<Message>();
    for (final ChatMessage chatMessage :
        originalResponse.getIntermediateResults().getTemplating()) {
      if (chatMessage instanceof AssistantChatMessage assistantChatMessage) {
        val toolCalls = assistantChatMessage.getToolCalls();
        if (!toolCalls.isEmpty()) {
          messages.add(new AssistantMessage(toolCalls));
        } else {
          messages.add(
              new AssistantMessage(
                  MessageContent.fromChatMessageContent(assistantChatMessage.getContent())));
        }
      } else if (chatMessage instanceof SystemChatMessage systemChatMessage) {
        messages.add(
            new SystemMessage(
                MessageContent.fromChatMessageContent(systemChatMessage.getContent())));
      } else if (chatMessage instanceof UserChatMessage userChatMessage) {
        messages.add(
            new UserMessage(
                MessageContent.fromUserChatMessageContent(userChatMessage.getContent())));
      } else if (chatMessage instanceof ToolChatMessage toolChatMessage) {
        messages.add(
            new ToolMessage(
                toolChatMessage.getToolCallId(),
                ((ChatMessageContent.InnerString) toolChatMessage.getContent()).value()));
      } else {
        throw new IllegalArgumentException(
            "Messages of type " + chatMessage.getClass() + " are not supported by convenience API");
      }
    }

    messages.add(Message.assistant(getChoice().getMessage().getContent()));
    return messages;
  }

  /**
   * Get the LLM response. Useful for accessing the finish reason or further data like logprobs.
   *
   * @return The (first, in case of multiple) {@link LLMChoice}.
   */
  @Nonnull
  public LLMChoice getChoice() {
    //    We expect choices to be defined and never empty.
    return originalResponse.getFinalResult().getChoices().get(0);
  }

  /**
   * Transform a JSON response into an entity of the given type.
   *
   * <p>This is possible on a request with a {@link OrchestrationTemplate#withJsonSchemaResponse}
   * configured into {@link OrchestrationModuleConfig#withTemplateConfig}.
   *
   * @param type the class type to deserialize the JSON content into.
   * @return the deserialized entity of type T.
   * @param <T> the type of the entity to deserialize to.
   * @throws OrchestrationClientException if the model refused to answer the question or if the
   *     content
   */
  @Nonnull
  public <T> T asEntity(@Nonnull final Class<T> type) throws OrchestrationClientException {
    final String refusal =
        getOriginalResponse().getFinalResult().getChoices().get(0).getMessage().getRefusal();
    if (refusal != null) {
      throw new OrchestrationClientException(
          "The model refused to answer the question: " + refusal);
    }
    try {
      return new ObjectMapper().readValue(getContent(), type);
    } catch (InvalidDefinitionException e) {
      throw new OrchestrationClientException(
          "Failed to deserialize the JSON content. Please make sure to use the correct class and that the class has a no-args constructor or is static: "
              + e.getMessage()
              + "\nJSON content: "
              + getContent(),
          e);
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException(
          "Failed to deserialize the JSON content. Please configure an OrchestrationTemplate with format set to JSON schema into your OrchestrationModuleConfig: "
              + e.getMessage()
              + "\nJSON content: "
              + getContent(),
          e);
    }
  }
}
