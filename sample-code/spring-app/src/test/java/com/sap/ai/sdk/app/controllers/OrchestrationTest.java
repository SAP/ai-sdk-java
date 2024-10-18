package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.HIGH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrchestrationTest {
  OrchestrationController controller;

  @BeforeEach
  void setUp() {
    controller = new OrchestrationController();
  }

  @Test
  void testCompletion() {
    final var result = controller.completion();

    assertThat(result.getRequestId()).isNotEmpty();
    var llm = result.getModuleResults().getLlm();
    assertThat(llm.getId()).isNotEmpty();
    assertThat(llm.getObject()).isEqualTo("chat.completion");
    assertThat(llm.getCreated()).isGreaterThan(1);
    assertThat(llm.getModel()).isEqualTo(OrchestrationController.MODEL);
    var choices = llm.getChoices();
    assertThat(choices.get(0).getIndex()).isEqualTo(0);
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    var usage = llm.getUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);
    assertThat(result.getOrchestrationResult().getObject()).isEqualTo("chat.completion");
    assertThat(result.getOrchestrationResult().getCreated()).isGreaterThan(1);
    assertThat(result.getOrchestrationResult().getModel()).isEqualTo(OrchestrationController.MODEL);
    choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getIndex()).isEqualTo(0);
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = result.getOrchestrationResult().getUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);
  }

  @Test
  void testTemplate() {
    var result = controller.template();

    var templateResult = result.getModuleResults().getTemplating().get(0);
    assertThat(templateResult.getContent())
        .isEqualTo("Reply with 'The Orchestration Service is working!' in german");
    assertThat(templateResult.getRole()).isEqualTo("user");
  }

  @Test
  void testLenientContentFilter() {
    var result = controller.filter(AzureContentFilter.Sensitivity.LOW);

    var filterResult = result.getModuleResults().getInputFiltering();

    assertThat(filterResult.getMessage()).contains("passed");
  }

  @Test
  void testStrictContentFilter() {
    assertThatThrownBy(() -> new OrchestrationController().filter(HIGH))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request")
        .hasMessageContaining("Content filtered");
  }
}
