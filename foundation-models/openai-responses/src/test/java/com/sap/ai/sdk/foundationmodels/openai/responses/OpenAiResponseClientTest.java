package com.sap.ai.sdk.foundationmodels.openai.responses;

import static org.assertj.core.api.Assertions.assertThat;

import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class OpenAiResponseClientTest {
  private static final OpenAiResponsesClient client = new OpenAiResponsesClient();

  @Test
  @Disabled("Integration test - requires actual API credentials")
  void createResponse() {
    final ResponseCreateParams request =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .input("List 5 famous late twentieth century novels.")
            .build();

    final var response = client.createResponse(request);

    assertThat(response).isNotNull();
    assertThat(response.output()).isNotEmpty();
  }
}
