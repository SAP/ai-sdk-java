package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.CONTENT_FILTER;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner;
import java.util.ArrayList;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

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
  @Nonnull final CreateChatCompletionResponse originalResponse;

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
   * Gets the {@code OpenAiAssistantMessage} for the first choice in response. *
   *
   * @return the assistant message
   * @since 1.6.0
   */
  @Nonnull
  public OpenAiAssistantMessage getMessage() {

    if (getChoice().getMessage().getToolCalls() == null) {
      return OpenAiMessage.assistant(getContent());
    }

    final var contentItems = new ArrayList<OpenAiContentItem>();
    if (!getContent().isEmpty()) {
      contentItems.add(new OpenAiTextItem(getContent()));
    }

    for (final var toolCall : getChoice().getMessage().getToolCalls()) {
      contentItems.add(
          new OpenAiFunctionCallItem(
              toolCall.getId(),
              toolCall.getFunction().getName(),
              toolCall.getFunction().getArguments()));
    }

    return new OpenAiAssistantMessage(new OpenAiMessageContent(contentItems));
  }
}
