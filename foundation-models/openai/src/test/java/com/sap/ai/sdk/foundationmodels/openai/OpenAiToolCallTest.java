package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class OpenAiToolCallTest {
  private static final OpenAiFunctionCall VALID_FUNCTION_CALL =
      new OpenAiFunctionCall("1", "functionName", "{\"key\":\"value\"}");
  private static final OpenAiFunctionCall INVALID_FUNCTION_CALL =
      new OpenAiFunctionCall("1", "functionName", "{invalid-json}");

  private static class Dummy {
    record Request(String key) {}

    record Response(String result) {}

    static Function<Request, Response> conCat = request -> new Response(request.key());
  }

  private static final OpenAiTool<Dummy.Request> TOOL =
      new OpenAiTool<>("functionName", Dummy.Request.class);

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
    Dummy.Request result = VALID_FUNCTION_CALL.getArgumentsAsObject(TOOL);
    assertThat(result).isInstanceOf(Dummy.Request.class);
    assertThat(result.key()).isEqualTo("value");
  }

  @Test
  void getArgumentsAsObjectThrowsOnInvalidJson() {
    assertThatThrownBy(() -> INVALID_FUNCTION_CALL.getArgumentsAsObject(TOOL))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }
}
