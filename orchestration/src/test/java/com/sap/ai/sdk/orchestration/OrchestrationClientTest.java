package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class OrchestrationClientTest {
  private OrchestrationClient client;

  private static final LLMModuleConfig LLM_CONFIG =
      LLMModuleConfig.create().modelName("gpt-35-turbo-16k").modelParams(Map.of());

  @BeforeEach
  void setup() {
    var destination = DefaultHttpDestination.builder("").build();
    client = spy(new OrchestrationClient(destination).withLlmConfig(LLM_CONFIG));
  }

  @Test
  void testSimpleChatCompletion() {
    stubResponse("stop");

    var result = client.chatCompletion("Hello there!");
    assertThat(result).isEqualTo("General Kenobi!");

    var expected = ChatMessage.create().role("user").content("Hello there!");
    verify(client)
        .chatCompletion(
            ArgumentMatchers.<OrchestrationPrompt>argThat(
                prompt -> prompt.getMessages().contains(expected)));
  }

  @Test
  void testSimpleChatCompletionThrowsOnOutputContentFilter() {

    stubResponse("content_filter");

    assertThatThrownBy(() -> client.chatCompletion("foo"))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("content filter");
  }

  private void stubResponse(String finishReason) {
    var response = mock(CompletionPostResponse.class);
    var orchestrationResult = mock(LLMModuleResult.class);
    var llmChoice =
        LLMChoice.create()
            .index(0)
            .message(ChatMessage.create().role("assistant").content("General Kenobi!"))
            .finishReason(finishReason);

    when(orchestrationResult.getChoices()).thenReturn(List.of(llmChoice));
    when(response.getOrchestrationResult()).thenReturn(orchestrationResult);

    doReturn(response).when(client).executeRequest(any());
  }
}
