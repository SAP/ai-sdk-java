package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiAgenticWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SpringAiAgenticWorkflowTest {

  SpringAiAgenticWorkflowService service = new SpringAiAgenticWorkflowService();

  @Test
  void testCompletion() {
    var response = service.runAgent("I want to visit Paris for a day. What can I do there?");
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getText())
        .contains("Le Comptoir du Relais")
        .contains("L'As du Fallafel")
        .contains("Breizh Café")
        .contains("1°C");
  }
}
