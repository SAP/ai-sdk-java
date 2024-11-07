package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.InstanceOfAssertFactories;
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

    assertThat(result).isNotNull();
    assertThat(result.getOrchestrationResult().getChoices().get(0).getMessage().getContent())
        .isNotEmpty();
  }

  @Test
  void testTemplate() {
    final var result = controller.template();

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
    assertThat(choices.get(0).getIndex()).isZero();
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
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = result.getOrchestrationResult().getUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);
  }

  @Test
  void testLenientContentFilter() {
    var result = controller.filter(AzureThreshold.NUMBER_4);
    var llmChoice = result.getOrchestrationResult().getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(llmChoice.getMessage().getContent()).isNotEmpty();

    var filterResult = result.getModuleResults().getInputFiltering();
    assertThat(filterResult.getMessage()).contains("passed");
  }

  @Test
  void testStrictContentFilter() {
    assertThatThrownBy(() -> controller.filter(AzureThreshold.NUMBER_0))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request")
        .hasMessageContaining("Content filtered");
  }

  @Test
  void testMessagesHistory() {
    CompletionPostResponse result = controller.messagesHistory();
    final var choices = result.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
  }

  @SuppressWarnings("unchecked")
  @Test
  void testMaskingAnonymization() {
    var result = controller.maskingAnonymization();
    var llmChoice = result.getOrchestrationResult().getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");

    var maskingResult = result.getModuleResults().getInputMasking();
    assertThat(maskingResult.getMessage()).isNotEmpty();
    var data = (Map<String, Object>) maskingResult.getData();
    var maskedMessage = ((List<Map<String, Object>>) data.get("masked_template")).get(0);
    assertThat(maskedMessage.get("content"))
        .asInstanceOf(InstanceOfAssertFactories.STRING)
        .doesNotContain("Alice", "Bob");

    assertThat(result.getModuleResults().getOutputUnmasking()).isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Test
  void testMaskingPseudonymization() {
    var result = controller.maskingPseudonymization();
    var llmChoice = result.getOrchestrationResult().getChoices().get(0);
    assertThat(llmChoice.getFinishReason()).isEqualTo("stop");
    assertThat(llmChoice.getMessage().getContent())
        .describedAs("The final response should contain the original user name")
        .contains("Mallory");

    var maskingResult = result.getModuleResults().getInputMasking();
    assertThat(maskingResult.getMessage()).isNotEmpty();
    var data = (Map<String, Object>) maskingResult.getData();
    var maskedMessage = ((List<Map<String, Object>>) data.get("masked_template")).get(1);
    assertThat(maskedMessage.get("content"))
        .asInstanceOf(InstanceOfAssertFactories.STRING)
        .describedAs("The masked input should not contain any user names but only pseudonyms")
        .doesNotContain("Mallory", "Alice", "Bob")
        .contains("MASKED_PERSON");

    var unmaskingResult = result.getModuleResults().getOutputUnmasking();
    assertThat(unmaskingResult).isNotEmpty();
    assertThat(unmaskingResult.get(0).getMessage().getContent())
        .describedAs("The unmasking step should replace the pseudonyms used by the LLM")
        .doesNotContain("MASKED_PERSON")
        .contains("Mallory");
  }
}
