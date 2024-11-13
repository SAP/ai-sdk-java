package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class LLMModuleResultDeserializerTest {

  private final ObjectMapper objectMapper = OrchestrationClient.JACKSON;
  private final String jsonTemplate =
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

    var json = String.format(jsonTemplate, choices);

    // Deserialize JSON content
    LLMModuleResult result = objectMapper.readValue(json, LLMModuleResult.class);

    // Assert
    assertThat(result).isExactlyInstanceOf(LLMModuleResultSynchronous.class);
  }
}
