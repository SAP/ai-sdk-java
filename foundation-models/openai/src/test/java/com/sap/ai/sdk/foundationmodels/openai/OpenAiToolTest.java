package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import lombok.EqualsAndHashCode;
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
    final Dummy.Request result = FUNCTION_CALL_A.getArgumentsAsObject(Dummy.Request.class);
    assertThat(result).isInstanceOf(Dummy.Request.class);
    assertThat(result.key()).isEqualTo("value");
  }

  @Test
  void getArgumentsAsObjectInvalid() {
    assertThatThrownBy(() -> INVALID_FUNCTION_CALL_A.getArgumentsAsObject(Integer.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to parse JSON string");
  }

  @Test
  void executeToolsValid() {
    final var toolA =
        OpenAiTool.forFunction(Dummy.conCat)
            .withArgument(Dummy.Request.class)
            .withName("functionA");
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_A));
    final var toolMsgs = OpenAiTool.execute(List.of(toolA), assistMsg);

    assertThat(toolMsgs).hasSize(1);
    assertThat(toolMsgs.get(0).toolCallId()).isEqualTo("1");
    assertThat(((OpenAiTextItem) toolMsgs.get(0).content().items().get(0)).text())
        .isEqualTo("{\"toolMsg\":\"value\"}");
  }

  @Test
  void executeToolsNoMatchingCall() {
    final var toolA =
        OpenAiTool.forFunction(Dummy.conCat)
            .withArgument(Dummy.Request.class)
            .withName("functionA");
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_B));
    final var toolMsgs = OpenAiTool.execute(List.of(toolA), assistMsg);
    assertThat(toolMsgs).isEmpty();
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

    final Function<Dummy.Request, Object> badF =
        request -> new NonSerializableResponse(request.key());
    final var toolA =
        OpenAiTool.forFunction(badF).withArgument(Dummy.Request.class).withName("functionA");
    final var assistMsg = new OpenAiAssistantMessage(EMPTY_MSG_CONTENT, List.of(FUNCTION_CALL_A));

    assertThatThrownBy(() -> OpenAiTool.execute(List.of(toolA), assistMsg))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Failed to serialize object to JSON");
  }
}
