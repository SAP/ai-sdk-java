package com.sap.ai.sdk.orchestration.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrchestrationChatOptionsTest {

  OrchestrationChatOptions opts;

  @BeforeEach
  void setUp() {
    opts = new OrchestrationChatOptions();
  }

  @Test
  void testFluentApi() {
    assertThat(opts).isSameAs(opts.withLlmConfig(null));
  }

  @Test
  void testHyperParameters() {
    var llm =
        LLMModuleConfig.create()
            .modelName("foo")
            .modelParams(Map.of("temperature", 0.5, "maxTokens", 100));
    opts.withLlmConfig(llm);

    assertThat(opts.getTemperature()).isEqualTo(0.5);
    assertThat(opts.getMaxTokens()).isEqualTo(100);
  }
}
