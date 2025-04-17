package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenAiToolTest {
  private final OpenAiFunctionCall functionCallA =
      new OpenAiFunctionCall("1", "functionA", "{\"key\":\"value\"}");
  private final OpenAiFunctionCall functionCallB =
      new OpenAiFunctionCall("2", "functionB", "{\"key\":\"value\"}");
  private final OpenAiFunctionCall invalidFunctionCallA =
      new OpenAiFunctionCall("3", "functionA", "{invalid-json}");

  private static class Dummy {
    record Request(String key) {}

    record Response(String result) {}

    static final Function<Dummy.Request, Dummy.Response> conCat =
        request -> new Dummy.Response(request.key());
  }

  private OpenAiTool<Dummy.Request> toolA;

  @BeforeEach
  void setUp() {
    toolA = new OpenAiTool<>("functionA", Dummy.Request.class);
  }

  @Test
  void getArgumentsAsMapValid() {
    final var result = functionCallA.getArgumentsAsMap();
    assertThat(result).containsEntry("key", "value");
  }

  @Test
  void getArgumentsAsMapInvalid() {
    assertThatThrownBy(invalidFunctionCallA::getArgumentsAsMap)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void getArgumentsAsObjectValid() {
    final Dummy.Request result = functionCallA.getArgumentsAsObject(toolA);
    assertThat(result).isInstanceOf(Dummy.Request.class);
    assertThat(result.key()).isEqualTo("value");
  }

  @Test
  void getArgumentsAsObjectInvalid() {
    assertThatThrownBy(() -> invalidFunctionCallA.getArgumentsAsObject(toolA))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void executeToolsValid() {
    toolA.setFunction(Dummy.conCat);
    final var result = OpenAiToolExecutor.executeTools(List.of(toolA), List.of(functionCallA));

    assertThat(result).hasSize(1);
    assertThat(result.get(0).toolCallId()).isEqualTo("1");
    assertThat(((OpenAiTextItem) result.get(0).content().items().get(0)).text())
        .isEqualTo("{\"result\":\"value\"}");
  }

  @Test
  void executeToolsThrowsOnNoFunction() {
    assertThatThrownBy(
            () -> OpenAiToolExecutor.executeTools(List.of(toolA), List.of(functionCallA)))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No function configured to execute.");
  }

  @Test
  void executeToolsNoMatchingCall() {
    final var toolAWithFunction = toolA.setFunction(Dummy.conCat);
    final var result =
        OpenAiToolExecutor.executeTools(List.of(toolAWithFunction), List.of(functionCallB));
    assertThat(result).isEmpty();
  }

  @Test
  void executeToolsThrowsOnSerializationError() {
    class NonSerializableResponse {
      private String result;

      NonSerializableResponse(String result) {
        this.result = result;
      }
    }

    toolA.setFunction(request -> new NonSerializableResponse(request.key()));

    assertThatThrownBy(
            () -> OpenAiToolExecutor.executeTools(List.of(toolA), List.of(functionCallA)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to serialize object to JSON");
  }
}
