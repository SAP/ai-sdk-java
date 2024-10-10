package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.OrchestrationConfigBuilder;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.prompt.ChatOptions;

/** Configuration to be used for orchestration requests. */
@Data
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions
    implements ChatOptions, OrchestrationConfigBuilder<OrchestrationChatOptions> {
  // Unfortunately, Lomboks @Delegate doesn't work with chaining, because "return this" returns the
  // delegate itself
  private OrchestrationConfig delegate = new OrchestrationConfig();

  @Nonnull
  @Override
  public OrchestrationChatOptions withLlmConfig(LLMModuleConfig llm) {
    delegate.withLlmConfig(llm);
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationChatOptions withTemplate(TemplatingModuleConfig template) {
    delegate.withTemplate(template);
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationChatOptions withMaskingConfig(MaskingModuleConfig maskingConfig) {
    delegate.withMaskingConfig(maskingConfig);
    return this;
  }

  // region satisfy the ChatOptions interface, delegating to the LLM config
  @Nullable
  @Override
  public String getModel() {
    return Option.of(delegate.getLlmConfig()).map(LLMModuleConfig::getModelName).getOrNull();
  }

  @Nullable
  String getModelVersion() {
    return Option.of(delegate.getLlmConfig()).map(LLMModuleConfig::getModelVersion).getOrNull();
  }

  @Nullable
  @Override
  public Double getFrequencyPenalty() {
    return getLlmConfigParam("frequencyPenalty");
  }

  @Nullable
  @Override
  public Integer getMaxTokens() {
    return getLlmConfigParam("maxTokens");
  }

  @Nullable
  @Override
  public Double getPresencePenalty() {
    return getLlmConfigParam("presencePenalty");
  }

  @Nullable
  @Override
  public List<String> getStopSequences() {
    return getLlmConfigParam("stopSequences");
  }

  @Nullable
  @Override
  public Double getTemperature() {
    return getLlmConfigParam("temperature");
  }

  @Nullable
  @Override
  public Integer getTopK() {
    return getLlmConfigParam("topK");
  }

  @Nullable
  @Override
  public Double getTopP() {
    return getLlmConfigParam("topP");
  }

  @Override
  public OrchestrationChatOptions copy() {
    throw new RuntimeException("Not implemented");
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return Option.of(delegate.getLlmConfig())
        .map(LLMModuleConfig::getModelParams)
        .map(it -> (Map<String, Object>) it)
        .map(m -> (T) m.get(param))
        .getOrNull();
  }
  // endregion
}
