package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;

public class OrchestrationTest {

  OrchestrationController controller = new OrchestrationController();

  @Test
  void testCompletion() {
    ChatResponse response = controller.completion();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).contains("Paris");
  }

  @Test
  void testTemplate() {
    ChatResponse response = controller.template();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testMasking() {
    ChatResponse response = controller.masking();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testChatMemory() {
    ChatResponse response = controller.chatMemory();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }
}
