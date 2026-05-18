package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

public class BatchTest {

  @Test
  @SneakyThrows
  public void testBatchInput() {
    val mapper = getDefaultObjectMapper();

    val batchInput =
        new OpenAiBatchInput(
            new OpenAiChatCompletionRequest("What is machine learning?"),
            new OpenAiChatCompletionRequest("Explain neural networks in simple terms"));

    val serialized = mapper.writeValueAsString(batchInput);
    val expected =
        """
        {"custom_id":"request-1","method":"POST","url":"/v1/chat/completions","body":{"messages":[{"content":"What is machine learning?","role":"user"}]}}
        {"custom_id":"request-2","method":"POST","url":"/v1/chat/completions","body":{"messages":[{"content":"Explain neural networks in simple terms","role":"user"}]}}
        """;
    assertThat(serialized).isEqualTo(expected);
  }
}
