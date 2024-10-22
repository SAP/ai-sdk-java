package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.HIGH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationResponse;
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
    final var response = controller.completion();

    assertThat(response.finishReason()).isEqualTo(OrchestrationResponse.FinishReason.STOP);
    assertThat(response.assistantMessage().getContent()).isNotEmpty();
    assertThat(response.assistantMessage().getRole()).isEqualTo("assistant");
    assertThat(response.tokenUsage().getPromptTokens()).isPositive();
    assertThat(response.tokenUsage().getCompletionTokens()).isPositive();
    assertThat(response.tokenUsage().getTotalTokens()).isPositive();

    final var responseDto = response.originalResponseDto();

    assertThat(responseDto.getRequestId()).isNotEmpty();
    var llm = responseDto.getModuleResults().getLlm();
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
    assertThat(responseDto.getOrchestrationResult().getObject()).isEqualTo("chat.completion");
    assertThat(responseDto.getOrchestrationResult().getCreated()).isGreaterThan(1);
    assertThat(responseDto.getOrchestrationResult().getModel())
        .isEqualTo(OrchestrationController.MODEL);
    choices = responseDto.getOrchestrationResult().getChoices();
    assertThat(choices.get(0).getIndex()).isZero();
    assertThat(choices.get(0).getMessage().getContent()).isNotEmpty();
    assertThat(choices.get(0).getMessage().getRole()).isEqualTo("assistant");
    assertThat(choices.get(0).getFinishReason()).isEqualTo("stop");
    usage = responseDto.getOrchestrationResult().getUsage();
    assertThat(usage.getCompletionTokens()).isGreaterThan(1);
    assertThat(usage.getPromptTokens()).isGreaterThan(1);
    assertThat(usage.getTotalTokens()).isGreaterThan(1);
  }

  @Test
  void testTemplate() {
    var result = controller.template();

    var templateResult = result.allMessages().get(0);
    assertThat(templateResult.getContent())
        .isEqualTo("Reply with 'The Orchestration Service is working!' in german");
    assertThat(templateResult.getRole()).isEqualTo("user");
  }

  @Test
  void testLenientContentFilter() {
    var result = controller.filter(AzureContentFilter.Sensitivity.LOW);
    assertThat(result.finishReason()).isEqualTo(OrchestrationResponse.FinishReason.STOP);
    assertThat(result.assistantMessage().getContent()).isNotEmpty();

    var filterResult = result.originalResponseDto().getModuleResults().getInputFiltering();
    assertThat(filterResult.getMessage()).contains("passed");
  }

  @Test
  void testStrictContentFilter() {
    assertThatThrownBy(() -> new OrchestrationController().filter(HIGH))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining("400 Bad Request")
        .hasMessageContaining("Content filtered");
  }

  @SuppressWarnings("unchecked")
  @Test
  void testMasking() {
    var result = controller.masking();

    assertThat(result.assistantMessage().getContent()).contains("foo.bar@baz.ai");
    var maskingResult = result.originalResponseDto().getModuleResults().getInputMasking();
    var data = (Map<String, Object>) maskingResult.getData();
    var maskedMessage = ((List<Map<String, Object>>) data.get("masked_template")).get(0);
    assertThat(maskedMessage.get("content"))
        .asInstanceOf(InstanceOfAssertFactories.STRING)
        .doesNotContain("foo.bar@baz.ai");
  }
}
