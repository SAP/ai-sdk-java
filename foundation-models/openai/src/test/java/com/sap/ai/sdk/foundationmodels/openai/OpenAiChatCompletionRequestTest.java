package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpenAiChatCompletionRequestTest {

  @Test
  void stopSequence() {
    var request =
        new OpenAiChatCompletionRequest(OpenAiMessage.user("Hello, world"))
            .withStop("stop1", "stop2 stopNot3", "stop3");

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    assertThat(
            ((CreateChatCompletionRequestAllOfStop.InnerStrings) lowLevelRequest.getStop())
                .values())
        .containsExactly("stop1", "stop2 stopNot3", "stop3");
  }

  @Test
  void createWithExistingRequest() {
    var originalRequest =
        new OpenAiChatCompletionRequest(OpenAiMessage.user("First message"))
            .withSeed(123)
            .withTemperature(BigDecimal.valueOf(0.5));

    var newRequest = originalRequest.withMessages(List.of(OpenAiMessage.user("Another message")));

    var lowlevelRequest = newRequest.createCreateChatCompletionRequest();

    assertThat(newRequest).isNotEqualTo(originalRequest);
    assertThat(lowlevelRequest.getMessages()).hasSize(1);
    assertThat(lowlevelRequest.getMessages().get(0))
        .isInstanceOf(ChatCompletionRequestUserMessage.class);
    assertThat(
            ((ChatCompletionRequestUserMessage) lowlevelRequest.getMessages().get(0)).getContent())
        .isEqualTo(ChatCompletionRequestUserMessageContent.create("Another message"));
    assertThat(lowlevelRequest.getSeed()).isEqualTo(123);
    assertThat(lowlevelRequest.getTemperature()).isEqualTo(BigDecimal.valueOf(0.5));
  }
}
