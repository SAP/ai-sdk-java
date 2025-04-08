package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpenAiToolCallTest {
  private static final OpenAiFunctionCall VALID_FUNCTION_CALL =
      new OpenAiFunctionCall("1", "functionName", "{\"key\":\"value\"}");
  private static final OpenAiFunctionCall INVALID_FUNCTION_CALL =
      new OpenAiFunctionCall("1", "functionName", "{invalid-json}");

  record DummyRequest(String key) {}

  @Test
  void getArgumentsAsMapParsesValidJson() {
    var result = VALID_FUNCTION_CALL.getArgumentsAsMap();
    assertThat(result).containsEntry("key", "value");
  }

  @Test
  void getArgumentsAsMapThrowsOnInvalidJson() {
    assertThatThrownBy(INVALID_FUNCTION_CALL::getArgumentsAsMap)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void getArgumentsAsObjectParsesValidJson() {
    var result = VALID_FUNCTION_CALL.getArgumentsAsObject(DummyRequest.class);
    assertThat(result).isInstanceOf(DummyRequest.class);
    assertThat(result.key()).isEqualTo("value");
  }

  @Test
  void getArgumentsAsObjectThrowsOnInvalidJson() {
    assertThatThrownBy(() -> INVALID_FUNCTION_CALL.getArgumentsAsObject(DummyRequest.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void getArgumentsAsObjectThrowsOnTypeMismatch() {
    assertThatThrownBy(() -> VALID_FUNCTION_CALL.getArgumentsAsObject(Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }
}
