package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenAiToolTest {
  private static final OpenAiFunctionCall FUNCTION_CALL_A =
      new OpenAiFunctionCall("1", "functionA", "{\"key\":\"value\"}");
  private static final OpenAiFunctionCall FUNCTION_CALL_B =
      new OpenAiFunctionCall("2", "functionB", "{\"key\":\"value\"}");
  private static final OpenAiFunctionCall INVALID_FUNCTION_CALL_A =
      new OpenAiFunctionCall("3", "functionA", "{invalid-json}");

  private static final OpenAiMessageContent EMPTY_MSG_CONTENT =
      new OpenAiMessageContent(Collections.emptyList());

  private static class Dummy {
    record Request(String key) {}

    record Response(String toolMsg) {}

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
    final var result = FUNCTION_CALL_A.getArgumentsAsMap();
    assertThat(result).containsEntry("key", "value");
  }

  @Test
  void getArgumentsAsMapInvalid() {
    assertThatThrownBy(INVALID_FUNCTION_CALL_A::getArgumentsAsMap)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void getArgumentsAsObjectValid() {
    final Dummy.Request result = FUNCTION_CALL_A.getArgumentsAsObject(toolA.getRequestClass());
    assertThat(result).isInstanceOf(Dummy.Request.class);
    assertThat(result.key()).isEqualTo("value");
  }

  @Test
  void getArgumentsAsObjectInvalid() {
    assertThatThrownBy(() -> INVALID_FUNCTION_CALL_A.getArgumentsAsObject(toolA.getRequestClass()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void executeToolsValid() {
    toolA.setFunction(Dummy.conCat);
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_A));
    final var execution = OpenAiTool.execute(List.of(toolA), assistMsg);

    final var results = execution.getResults();
    assertThat(results)
        .hasSize(1)
        .containsExactly(Map.entry(FUNCTION_CALL_A, new Dummy.Response("value")));

    final var toolMsg = execution.getMessages();
    assertThat(toolMsg).hasSize(1);
    assertThat(toolMsg.get(0).toolCallId()).isEqualTo("1");
    assertThat(((OpenAiTextItem) toolMsg.get(0).content().items().get(0)).text())
        .isEqualTo("{\"toolMsg\":\"value\"}");
  }

  @Test
  void executeToolsThrowsOnNoFunction() {
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_A));
    assertThatThrownBy(() -> OpenAiTool.execute(List.of(toolA), assistMsg))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Tool functionA is missing a method reference to execute.");
  }

  @Test
  void executeToolsNoMatchingCall() {
    final var toolAWithFunction = toolA.setFunction(Dummy.conCat);
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_B));
    final var executions = OpenAiTool.execute(List.of(toolAWithFunction), assistMsg);
    assertThat(executions.getResults()).isEmpty();
    assertThat(executions.getMessages()).isEmpty();
  }

  @Test
  void executeToolsThrowsOnSerializationError() {
    @EqualsAndHashCode
    class NonSerializableResponse {
      private String result;

      NonSerializableResponse(String result) {
        this.result = result;
      }
    }

    toolA.setFunction(request -> new NonSerializableResponse(request.key()));
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_A));
    final var executions = OpenAiTool.execute(List.of(toolA), assistMsg);

    assertThat(executions.getResults())
        .containsExactly(Map.entry(FUNCTION_CALL_A, new NonSerializableResponse("value")));

    assertThatThrownBy(executions::getMessages)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to serialize object to JSON");
  }
}
