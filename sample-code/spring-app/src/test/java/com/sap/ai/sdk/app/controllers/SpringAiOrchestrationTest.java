package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;

public class SpringAiOrchestrationTest {

  SpringAiOrchestrationService service = new SpringAiOrchestrationService();

  @Test
  void testCompletion() {
    ChatResponse response = service.completion();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).contains("Paris");
  }

  @Test
  void testTemplate() {
    ChatResponse response = service.template();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testMasking() {
    ChatResponse response = service.masking();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }
}
