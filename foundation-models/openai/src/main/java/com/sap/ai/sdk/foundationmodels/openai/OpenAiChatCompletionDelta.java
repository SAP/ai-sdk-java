package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.foundationmodels.openai.model2.CompletionUsage;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * OpenAI chat completion output delta for streaming.
 *
 * @since 1.3.0
 */
@Beta
@Value
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class OpenAiChatCompletionDelta implements StreamedDelta {
  CreateChatCompletionStreamResponse originalResponse;

  @Nonnull
  @Override
  public String getDeltaContent() {
    final var choices = getOriginalResponse().getChoices();
    if (!choices.isEmpty() && choices.get(0).getIndex() == 0) {
      final var message = choices.get(0).getDelta();
      return Objects.requireNonNullElse(message.getContent(), "");
    }
    return "";
  }

  @Nullable
  @Override
  public String getFinishReason() {
    final var choices = getOriginalResponse().getChoices();
    if (!choices.isEmpty()) {
      final var finishReason = choices.get(0).getFinishReason();
      return finishReason != null ? finishReason.getValue() : null;
    }
    return null;
  }

  /**
   * Get the completion usage from the response, or null if it is not available.
   *
   * @param objectMapper The object mapper to use for conversion.
   * @return The completion usage or null.
   */
  @Nullable
  public CompletionUsage getCompletionUsage(@Nonnull final ObjectMapper objectMapper) {
    if (getOriginalResponse().getCustomFieldNames().contains("usage")
        && getOriginalResponse().getCustomField("usage") instanceof Map<?, ?> usage) {
      return objectMapper.convertValue(usage, CompletionUsage.class);
    }
    return null;
  }
}
