package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionStreamResponse;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OpenAiChatCompletionDelta extends CreateChatCompletionStreamResponse
    implements StreamedDelta {
  @Nonnull
  @Override
  public String getDeltaContent() {
    final var choices = super.getChoices();
    if (!choices.isEmpty() && choices.get(0).getIndex() == 0) {
      final var message = choices.get(0).getDelta();
      if (message != null) {
        return Objects.requireNonNullElse(message.getContent(), "");
      }
    }
    return "";
  }

  @Nullable
  @Override
  public String getFinishReason() {
    final var choices = super.getChoices();
    if (!choices.isEmpty()) {
      final var finishReason = choices.get(0).getFinishReason();
      return finishReason != null ? finishReason.getValue() : null;
    }
    return null;
  }
}
