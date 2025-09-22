package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.CONTENT_FILTER;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

/**
 * Represents the output of an OpenAI chat completion. *
 *
 * @since 1.4.0
 */
@Value
@RequiredArgsConstructor(access = PACKAGE)
@Setter(value = NONE)
public class OpenAiChatCompletionResponse {
  /** The original response from the OpenAI API. */
  @Nonnull CreateChatCompletionResponse originalResponse;

  /** The original request that was sent to the OpenAI API. */
  @Getter(NONE)
  @Nonnull
  OpenAiChatCompletionRequest originalRequest;

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

  /**
   * Execute tool calls that were suggested by the assistant response.
   *
   * @return the list of tool messages that were serialized for the computed results. Empty list if
   *     no tools were called.
   * @since 1.8.0
   */
  @Nonnull
  public List<OpenAiToolMessage> executeTools() {
    final var tools = originalRequest.getToolsExecutable();
    return OpenAiTool.execute(tools != null ? tools : List.of(), getMessage());
  }
}
