package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class RealtimeParamSystemPromptUnitTest {

  @Test
  void getParamName() {
    assertThat(new RealtimeParamSystemPrompt(randomString()).getParamName())
        .isEqualTo(RealtimeParam.ParamName.SYSTEM_PROMPT);
  }

  @Test
  void getValueAsString() {
    final var randomValue = randomString();
    final var prompt = new RealtimeParamSystemPrompt(randomValue);
    assertThat(prompt.getValueAsString()).isEqualTo(randomValue);
  }

  @Test
  void testEquals() {
    final var randomValue = randomString();
    final var prompt1 = new RealtimeParamSystemPrompt(randomValue);
    final var prompt2 = new RealtimeParamSystemPrompt(randomValue);
    assertThat(prompt1).isEqualTo(prompt2);

    final var otherRandomValue = randomString();
    final var prompt3 = new RealtimeParamSystemPrompt(otherRandomValue);
    assertThat(prompt1).isNotEqualTo(prompt3);
    assertThat(prompt2).isNotEqualTo(prompt3);
  }

  @Test
  void testHashCode() {
    final var randomValue = randomString();
    final var prompt1 = new RealtimeParamSystemPrompt(randomValue);
    final var prompt2 = new RealtimeParamSystemPrompt(randomValue);

    assertThat(prompt1.hashCode()).isEqualTo(prompt2.hashCode());
  }

  private String randomString() {
    return UUID.randomUUID().toString().substring(0, 20).replace("-", " ");
  }
}
