package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMChoice;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.ModuleResults;
import com.sap.ai.sdk.orchestration.client.model.TokenUsage;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrchestrationResponseTest {
  @Test
  void testFromCompletionPostResponseDTO() {
    var message1 = mock(ChatMessage.class);
    var message2 = mock(ChatMessage.class);
    var message3 = mock(ChatMessage.class);
    var moduleResults = ModuleResults.create().templating(List.of(message1, message2));

    var orchestrationResult =
        LLMModuleResult.create()
            .id("bar")
            ._object("baz")
            .created(1234)
            .model("quack")
            .choices(LLMChoice.create().index(0).message(message3).finishReason("stop"))
            .usage(mock(TokenUsage.class));

    var postResponse =
        CompletionPostResponse.create()
            .requestId("foo")
            .moduleResults(moduleResults)
            .orchestrationResult(orchestrationResult);

    var result = OrchestrationResponse.fromCompletionPostResponseDTO(postResponse);

    assertThat(result.assistantMessage()).isSameAs(message3);
    assertThat(result.allMessages()).containsExactly(message1, message2, message3);
    assertThat(result.finishReason()).isEqualTo(OrchestrationResponse.FinishReason.STOP);
    assertThat(result.originalResponseDto()).isSameAs(postResponse);
  }

  @Test
  void testUnknownFinishReason() {
    assertThat(OrchestrationResponse.FinishReason.fromValue("foo"))
        .isEqualTo(OrchestrationResponse.FinishReason.UNKNOWN);
  }
}
