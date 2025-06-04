package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sap.ai.sdk.orchestration.model.LLMModuleResultSynchronous;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class LLMModuleResultDeserializerTest {
  private static final String JSON =
      """
      {
        "object": "chat.completion",
        "id": "chatcmpl-12345",
        "created": 1234567890,
        "model": "gpt-3.5-turbo",
        "choices": %s,
        "usage": {
          "completion_tokens": 10,
          "prompt_tokens": 20,
          "total_tokens": 30
        }
      }
      """;

  @SneakyThrows
  @Test
  void testSubtypeResolutionSynchronous() {
    var choices =
        """
        [
          {
            "index": 0,
            "message": {
              "role": "assistant",
              "content": "Sample response content."
            },
            "finish_reason": "length"
          }
        ]
        """;

    var json = String.format(JSON, choices);
    var result = OrchestrationClient.JACKSON.readValue(json, LLMModuleResultSynchronous.class);
    assertThat(result).isExactlyInstanceOf(LLMModuleResultSynchronous.class);
  }
}
