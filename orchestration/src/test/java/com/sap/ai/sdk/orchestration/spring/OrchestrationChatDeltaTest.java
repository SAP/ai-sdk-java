package com.sap.ai.sdk.orchestration.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.ResponseChatMessage;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.metadata.EmptyUsage;
import org.springframework.ai.chat.model.Generation;

class OrchestrationChatDeltaTest {

  @Test
  void testToGeneration() {
    var choice =
        LLMChoice.create()
            .index(0)
            .message(
                ResponseChatMessage.create()
                    .role(ResponseChatMessage.RoleEnum.UNKNOWN_DEFAULT_OPEN_API)
                    .content("wrong"))
            .finishReason("stop");
    // this will be fixed once the spec is fixed
    choice.setCustomField("delta", Map.of("content", "Hello, world!"));

    Generation generation = OrchestrationSpringChatDelta.toGeneration(choice);

    assertThat(generation.getOutput().getText()).isEqualTo("Hello, world!");
    assertThat(generation.getMetadata().getFinishReason()).isEqualTo("stop");
    assertThat(generation.getMetadata().<Integer>get("index")).isEqualTo(0);
  }

  @Test
  void testToChatResponseMetadata() {
    var moduleResult =
        LLMModuleResultSynchronous.create()
            .id("test-id")
            ._object("test-object")
            .created(123456789)
            .model("test-model")
            .choices(List.of())
            .usage(TokenUsage.create().completionTokens(20).promptTokens(10).totalTokens(30));

    var metadata = OrchestrationSpringChatDelta.toChatResponseMetadata(moduleResult);

    assertThat(metadata.getId()).isEqualTo("test-id");
    assertThat(metadata.getModel()).isEqualTo("test-model");
    assertThat(metadata.<String>get("object")).isEqualTo("test-object");
    assertThat(metadata.<Integer>get("created")).isEqualTo(123456789);

    var usage = metadata.getUsage();

    assertThat(usage.getPromptTokens()).isEqualTo(10L);
    assertThat(usage.getCompletionTokens()).isEqualTo(20L);
    assertThat(usage.getTotalTokens()).isEqualTo(30L);

    // delta without token usage
    moduleResult.usage(null);
    metadata = OrchestrationSpringChatDelta.toChatResponseMetadata(moduleResult);
    assertThat(metadata.getUsage()).isInstanceOf(EmptyUsage.class);
  }
}
