package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.client.model.ModuleResults;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrchestrationResponseTest {
  @Test
  void testFromCompletionPostResponseDto() {
    var message1 = new ChatMessage().role("system").content("foo");
    var message2 = new ChatMessage().role("user").content("bar");
    var message3 = new ChatMessage().role("assistant").content("baz");
    var moduleResults = new ModuleResults().templating(List.of(message1, message2));

    var orchestrationResult =
        new LLMModuleResultSynchronous()
            .id("bar")
            ._object("baz")
            .created(1234)
            .model("quack")
            .choices(List.of(new LLMChoice().index(0).message(message3).finishReason("stop")))
            .usage(mock(TokenUsage.class));

    var postResponse =
        new CompletionPostResponse()
            .requestId("foo")
            .moduleResults(moduleResults)
            .orchestrationResult(orchestrationResult);

    var result = OrchestrationResponse.fromCompletionPostResponseDto(postResponse);

    assertThat(result.assistantMessage()).isEqualTo(new AssistantMessage("baz"));
    assertThat(result.allMessages())
        .containsExactly(
            new SystemMessage("foo"), new UserMessage("bar"), new AssistantMessage("baz"));
    assertThat(result.finishReason()).isEqualTo(OrchestrationResponse.FinishReason.STOP);
    assertThat(result.originalResponseDto()).isSameAs(postResponse);
  }

  @Test
  void testUnknownFinishReason() {
    assertThat(OrchestrationResponse.FinishReason.fromValue("foo"))
        .isEqualTo(OrchestrationResponse.FinishReason.UNKNOWN);
  }
}
