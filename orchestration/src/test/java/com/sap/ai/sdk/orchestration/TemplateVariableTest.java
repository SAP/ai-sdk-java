package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class TemplateVariableTest {
  @Test
  void testTemplateFormat() {
    final var templateVariable = new TemplateVariable("test");
    var expected = "{{?test}}";
    assertThat(templateVariable).hasToString(expected);
  }

  @Test
  void testTemplateToEntry() {
    var actual = new TemplateVariable("test").apply("value");
    var expected = Map.entry("test", "value");
    assertThat(actual).isEqualTo(expected);
  }
}
