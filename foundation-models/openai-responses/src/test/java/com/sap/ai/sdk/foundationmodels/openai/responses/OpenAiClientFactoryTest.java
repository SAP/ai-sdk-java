package com.sap.ai.sdk.foundationmodels.openai.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link OpenAiClientFactory}. */
class OpenAiClientFactoryTest {

  private static final String BASE_URL = "http://localhost:8080";
  private static final String AUTH_TOKEN = "test-token-123";

  @Test
  @Disabled("Integration test that requires a running SAP AI Core deployment")
  void testCreateOpenAiClientFromAiModel() {
    final OpenAIClient client = OpenAiClientFactory.forModel(OpenAiModel.GPT_5);

    final ResponseCreateParams request =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_5) // Utilizes a different model list that earlier
            .input("List 5 famous late twentieth century novels.")
            .build();

    final Response response = client.responses().create(request);

    assertThat(response).isNotNull();
    assertThat(response.id()).isNotNull();
    assertThat(response.status()).isPresent();
    assertThat(response.output()).isNotEmpty();
  }

  @Test
  void testFromDestinationWithoutAuthorizationHeader() {
    final HttpDestination destination = DefaultHttpDestination.builder(BASE_URL).build();

    assertThatThrownBy(() -> OpenAiClientFactory.fromDestination(destination))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No Authorization header found");
  }

  @Test
  void testFromDestinationWithValidAuthorizationHeader() {
    final HttpDestination destination =
        DefaultHttpDestination.builder(BASE_URL)
            .header("Authorization", "Bearer " + AUTH_TOKEN)
            .build();

    final OpenAIClient client = OpenAiClientFactory.fromDestination(destination);

    assertThat(client).isNotNull();
  }
}
