package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TemplateVariableTest {
  @Test
  void testTemplateFormat() {
    final var templateVariable = TemplateVariable.of("test");
    final var templateString = templateVariable.toTemplateString();
    assertThat(templateString).isEqualTo("{{?test}}");
  }
}
