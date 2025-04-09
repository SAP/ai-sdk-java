package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.CONTENT_FILTER;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.val;

/**
 * Represents the output of an OpenAI chat completion. *
 *
 * @since 1.4.0
 */
@Beta
@Value
@RequiredArgsConstructor(access = PACKAGE)
@Setter(value = NONE)
public class OpenAiChatCompletionResponse {
  /** The original response from the OpenAI API. */
  @Nonnull CreateChatCompletionResponse originalResponse;

  @Nullable List<ChatCompletionTool> functions;

  /**
   * Gets the token usage from the original response.
   *
   * @return the token usage
   */
  @Nonnull
  public CompletionUsage getTokenUsage() {
    return getOriginalResponse().getUsage();
  }

  /**
   * Gets the first choice from the original response.
   *
   * @return the first choice
   */
  @Nonnull
  public CreateChatCompletionResponseChoicesInner getChoice() {
    return getOriginalResponse().getChoices().get(0);
  }

  /**
   * Gets the content of the first choice.
   *
   * <p>The content may be empty {@code ""} if the assistant did not return any content i.e. when
   * tool calls are present.
   *
   * @return the content of the first choice
   * @throws OpenAiClientException if the content is filtered by the content filter
   */
  @Nonnull
  public String getContent() {
    if (CONTENT_FILTER.equals(getOriginalResponse().getChoices().get(0).getFinishReason())) {
      throw new OpenAiClientException("Content filter filtered the output.");
    }

    return Objects.requireNonNullElse(getChoice().getMessage().getContent(), "");
  }

  /**
   * Gets the {@code OpenAiAssistantMessage} for the first choice.
   *
   * @return the assistant message
   * @throws OpenAiClientException if the content is filtered by the content filter
   * @since 1.6.0
   */
  @Nonnull
  public OpenAiAssistantMessage getMessage() {
    final var toolCalls = getChoice().getMessage().getToolCalls();

    if (toolCalls == null) {
      return OpenAiMessage.assistant(getContent());
    }

    final List<OpenAiContentItem> contentItems =
        getContent().isEmpty() ? List.of() : List.of(new OpenAiTextItem(getContent()));

    final var openAiToolCalls =
        toolCalls.stream()
            .<OpenAiToolCall>map(
                toolCall ->
                    new OpenAiFunctionCall(
                        toolCall.getId(),
                        toolCall.getFunction().getName(),
                        toolCall.getFunction().getArguments()))
            .toList();

    return new OpenAiAssistantMessage(new OpenAiMessageContent(contentItems), openAiToolCalls);
  }

  public <T, R> List<OpenAiToolMessage> executeTools( List<OpenAiTool> tools ) {
    return getMessage().toolCalls().stream()
        .filter(toolCall -> toolCall instanceof OpenAiFunctionCall)
        .map(toolCall -> (OpenAiFunctionCall) toolCall)
        .map(
            functionCall -> {
              OpenAiFunctionTool<T, R> request = findFunction(tools, functionCall.getName());
              T arguments = functionCall.parseArguments(new TypeReference<T>() {});
              R response = request.call(arguments);
              return OpenAiMessage.tool(response, functionCall.getId());
            })
        .toList();
  }

  @Nullable
  private <T, R> OpenAiFunctionTool<T, R> findFunction(List<OpenAiTool> tools, String name) {
    if (functions == null) {
      return null;
    }
    for (OpenAiTool tool : tools) {
      if (tool instanceof OpenAiFunctionTool<?, ?> function && function.getName().equals(name)) {
        return (OpenAiFunctionTool<T, R>) function;
      }
    }
    return null;
  }
}
