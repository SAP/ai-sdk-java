package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureContentFilter.Sensitivity.HIGH;
import static com.sap.ai.sdk.orchestration.ModuleConfigFactory.toModuleConfigDTO;
import static com.sap.ai.sdk.orchestration.client.model.FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModuleConfigFactoryTest {
  private static final List<ChatMessage> messages = List.of(mock(ChatMessage.class));
  private DefaultOrchestrationConfig<?> config;

  @BeforeEach
  void setUp() {
    config = DefaultOrchestrationConfig.standalone();
    config.withLlmConfig(mock(LlmConfig.class));
  }

  @Test
  void testThrowsOnMissingConfig() {
    config = DefaultOrchestrationConfig.standalone();

    assertThatThrownBy(() -> toModuleConfigDTO(config, messages))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("LLM module config is required");

    config.withLlmConfig(mock(LlmConfig.class));
    assertThatThrownBy(() -> toModuleConfigDTO(config, List.of()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("prompt is required");
  }

  @Test
  void testLlmConfig() {
    var llmConfig = new LlmConfig("foo", "bar", Map.of("baz", "quack"));

    config.withLlmConfig(llmConfig);

    var result = toModuleConfigDTO(config, messages).getLlmModuleConfig();
    assertThat(result.getModelName()).isEqualTo("foo");
    assertThat(result.getModelVersion()).isEqualTo("bar");
    assertThat(result.getModelParams())
        .asInstanceOf(InstanceOfAssertFactories.MAP)
        .containsEntry("baz", "quack");
  }

  @Test
  void testTemplateIsCreatedFromMessages() {
    var result = toModuleConfigDTO(config, messages).getTemplatingModuleConfig();

    assertThat(result.getTemplate()).containsExactly(messages.get(0));
    assertThat(result.getDefaults()).isNull();
  }

  @Test
  void testMessagesAreMergedIntoTemplate() {
    var message1 = mock(ChatMessage.class);
    var message2 = mock(ChatMessage.class);
    config.withTemplate(TemplatingModuleConfig.create().template(List.of(message1)));

    var result = toModuleConfigDTO(config, List.of(message2)).getTemplatingModuleConfig();

    assertThat(result.getTemplate()).containsExactly(message2, message1);
  }

  @Test
  void testInputFilter() {
    var filter = new AzureContentFilter().hate(HIGH);
    config.withInputContentFilter(filter);

    var result = toModuleConfigDTO(config, messages).getFilteringModuleConfig();

    assertThat(result.getInput().getFilters()).isNotEmpty();

    var filterDto = result.getInput().getFilters().get(0);
    assertThat(filterDto.getType()).isEqualTo(AZURE_CONTENT_SAFETY);
    assertThat(filterDto.getConfig().getHate().getValue()).isZero();
    assertThat(filterDto.getConfig().getViolence()).isNull();

    assertThat(result.getOutput()).isNull();
  }

  @Test
  void testOutputFilter() {
    var filter = new AzureContentFilter().hate(HIGH);
    config.withOutputContentFilter(filter);

    var result = toModuleConfigDTO(config, messages).getFilteringModuleConfig();

    assertThat(result.getOutput().getFilters()).isNotEmpty();
    var filterDto = result.getOutput().getFilters().get(0);
    assertThat(filterDto.getType()).isEqualTo(AZURE_CONTENT_SAFETY);
    assertThat(filterDto.getConfig().getHate().getValue()).isZero();
    assertThat(filterDto.getConfig().getViolence()).isNull();
    assertThat(result.getInput()).isNull();
  }

  @Test
  void testInputAndOutputFilter() {
    var inputFilter = new AzureContentFilter();
    var outputFilter = new AzureContentFilter();
    config.withInputContentFilter(inputFilter);
    config.withOutputContentFilter(outputFilter);

    var result = toModuleConfigDTO(config, messages).getFilteringModuleConfig();

    assertThat(result.getInput().getFilters()).isNotEmpty();
    assertThat(result.getOutput().getFilters()).isNotEmpty();
  }

  @Test
  void testMasking() {
    var maskingConfig = DpiMaskingConfig.anonymization().withEntities(DPIEntities.ADDRESS);
    config.withMaskingConfig(maskingConfig);

    var result = toModuleConfigDTO(config, messages).getMaskingModuleConfig();

    assertThat(result.getMaskingProviders())
        .isNotEmpty()
        .extracting(MaskingProviderConfig::getEntities)
        .extracting(it -> it.get(0))
        .extracting(DPIEntityConfig::getType)
        .containsOnly(DPIEntities.ADDRESS);
  }
}
