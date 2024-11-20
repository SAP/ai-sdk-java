package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureModerationPolicy.ALLOW_SAFE_LOW_MEDIUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrchestrationModuleConfigTest {

  private OrchestrationModuleConfig config;

  @BeforeEach
  void setUp() {
    final var llmModuleConfig =
        new LLMModuleConfig().modelName("gpt-35-turbo").modelParams(Map.of());
    config = new OrchestrationModuleConfig().withLlmConfig(llmModuleConfig);
  }

  @Test
  void testStackingInputAndOutputFilter() {
    final var filter =
        new AzureContentFilter()
            .hate(ALLOW_SAFE_LOW_MEDIUM)
            .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
            .sexual(ALLOW_SAFE_LOW_MEDIUM)
            .violence(ALLOW_SAFE_LOW_MEDIUM);

    final var configWithInputFirst = config.withInputFiltering(filter).withOutputFiltering(filter);
    assertThat(configWithInputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithInputFirst.getFilteringConfig().getInput()).isNotNull();

    final var configWithOutputFirst = config.withOutputFiltering(filter).withInputFiltering(filter);
    assertThat(configWithOutputFirst.getFilteringConfig()).isNotNull();
    assertThat(configWithOutputFirst.getFilteringConfig().getOutput()).isNotNull();
  }

  @Test
  void testThrowOnEmptyFilterConfig() {
    final var filter = new AzureContentFilter();

    assertThatThrownBy(() -> config.withInputFiltering(filter))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter moderation policy must be set");
    assertThatThrownBy(() -> config.withOutputFiltering(filter))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one filter moderation policy must be set");
  }
}
