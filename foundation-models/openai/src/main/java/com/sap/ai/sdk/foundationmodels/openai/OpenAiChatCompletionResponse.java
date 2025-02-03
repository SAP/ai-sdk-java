package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponseChoicesInner.FinishReasonEnum.CONTENT_FILTER;
import static lombok.AccessLevel.PROTECTED;

import com.sap.ai.sdk.foundationmodels.openai.model2.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionResponseChoicesInner;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/** Represents the output of an OpenAI chat completion. */
@Data
@RequiredArgsConstructor(access = PROTECTED)
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
}
