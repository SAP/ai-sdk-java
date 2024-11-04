package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import org.junit.jupiter.api.Test;

class OrchestrationTest {
  @Test
  void template() {
    final var result = new OrchestrationController().template();

    assertThat(result.getRequestId()).isNotEmpty();
    assertThat(result.getModuleResults().getTemplating().get(0).getContent())
        .isEqualTo("Reply with 'Orchestration Service is working!' in German");
    assertThat(result.getModuleResults().getTemplating().get(0).getRole()).isEqualTo("user");
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
  void looseFilter() {
    assertThat(new OrchestrationController().filter("4")).isNotNull();
  }

  @Test
  void strictFilter() {
    assertThatThrownBy(() -> new OrchestrationController().filter("0"))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request");
  }

  @Test
  void messagesHistory() {
    CompletionPostResponse result = new OrchestrationController().messagesHistory();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void maskingAnonymization() {
    final var result = new OrchestrationController().maskingAnonymization();
    assertThat(result).isNotNull();
    GenericModuleResult inputMasking = result.getModuleResults().getInputMasking();
    assertThat(inputMasking.getMessage()).isNotEmpty();
    assertThat(inputMasking.getData()).isNotNull();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @Test
  void maskingPseudonymization() {
    final var result = new OrchestrationController().maskingPseudonymization();
    assertThat(result).isNotNull();
    GenericModuleResult inputMasking = result.getModuleResults().getInputMasking();
    assertThat(inputMasking.getMessage()).isNotEmpty();
    assertThat(inputMasking.getData()).isNotNull();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }
}
