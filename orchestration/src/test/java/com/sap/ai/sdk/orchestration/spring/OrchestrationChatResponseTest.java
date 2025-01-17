package com.sap.ai.sdk.orchestration.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.LLMChoice;
import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import com.sap.ai.sdk.orchestration.model.TokenUsage;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;

class OrchestrationChatResponseTest {

  @Test
  void testToAssistantMessage() {
    var choice =
        LLMChoice.create()
            .index(0)
            .message(ChatMessage.create().role("assistant").content("Hello, world!"))
            .finishReason("stop");

    AssistantMessage message = OrchestrationSpringChatResponse.toAssistantMessage(choice);

    assertThat(message.getContent()).isEqualTo("Hello, world!");
    assertThat(message.getMetadata()).containsEntry("finish_reason", "stop");
    assertThat(message.getMetadata()).containsEntry("index", 0);
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

    var metadata = OrchestrationSpringChatResponse.toChatResponseMetadata(moduleResult);

    assertThat(metadata.getId()).isEqualTo("test-id");
    assertThat(metadata.getModel()).isEqualTo("test-model");
    assertThat(metadata.<String>get("object")).isEqualTo("test-object");
    assertThat(metadata.<Integer>get("created")).isEqualTo(123456789);

    var usage = metadata.getUsage();

    assertThat(usage.getPromptTokens()).isEqualTo(10L);
    assertThat(usage.getGenerationTokens()).isEqualTo(20L);
    assertThat(usage.getTotalTokens()).isEqualTo(30L);
  }
}
