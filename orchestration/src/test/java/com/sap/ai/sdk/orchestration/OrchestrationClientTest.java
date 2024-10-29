package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sap.ai.sdk.core.AiCoreDeployment;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.ModuleResults;
import java.util.List;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

class OrchestrationClientTest {
  private OrchestrationClient client;

  private static final OrchestrationConfig config =
      new OrchestrationConfig().withLlmConfig(new LlmConfig("gpt-35-turbo-16k"));

  @BeforeEach
  void setup() {
    var mock = mock(AiCoreDeployment.class);
    client = spy(new OrchestrationClient(mock));
  }

  @Test
  void testSimpleChatCompletion() {
    stubResponse("stop");

    new OrchestrationPrompt(new UserMessage("Hello there!"));
    var result = client.chatCompletion("Hello there!", config);
    assertThat(result).isEqualTo("General Kenobi!");

    var expected = new UserMessage("Hello there!");
    verify(client)
        .chatCompletion(
            ArgumentMatchers.<OrchestrationPrompt>argThat(
                prompt -> prompt.getMessages().contains(expected)));
  }

  @Test
  void testSimpleChatCompletionThrowsOnOutputContentFilter() {

    stubResponse("content_filter");

    assertThatThrownBy(() -> client.chatCompletion("foo", config))
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
    var moduleResults = mock(ModuleResults.class);
    when(moduleResults.getTemplating()).thenReturn(List.of());
    when(response.getModuleResults()).thenReturn(moduleResults);
    when(orchestrationResult.getChoices()).thenReturn(List.of(llmChoice));
    when(response.getOrchestrationResult()).thenReturn(orchestrationResult);

    doReturn(response).when(client).executeRequest(any(BasicClassicHttpRequest.class));
  }
}
