package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.prompt.ChatOptions;

/** Configuration to be used for orchestration requests. */
@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions implements ChatOptions {

  @Getter(AccessLevel.PUBLIC)
  @Nonnull
  private Map<String, String> templateParameters = Map.of();

  @Getter(AccessLevel.PUBLIC)
  @Setter(AccessLevel.PUBLIC)
  @Nonnull
  OrchestrationModuleConfig config = new OrchestrationModuleConfig();

  // region satisfy the ChatOptions interface, delegating to the LLM config
  @Nullable
  @Override
  public String getModel() {
    return getLlmConfigNonNull().getModelName();
  }

  @Nullable
  String getModelVersion() {
    return getLlmConfigNonNull().getModelVersion();
  }

  @Nullable
  @Override
  public Double getFrequencyPenalty() {
    return getLlmConfigParam("frequencyPenalty", Double.class);
  }

  @Nullable
  @Override
  public Integer getMaxTokens() {
    return getLlmConfigParam("maxTokens", Integer.class);
  }

  @Nullable
  @Override
  public Double getPresencePenalty() {
    return getLlmConfigParam("presencePenalty", Double.class);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public List<String> getStopSequences() {
    return getLlmConfigParam("stopSequences", List.class);
  }

  @Nullable
  @Override
  public Double getTemperature() {
    return getLlmConfigParam("temperature", Double.class);
  }

  @Nullable
  @Override
  public Integer getTopK() {
    return getLlmConfigParam("topK", Integer.class);
  }

  @Nullable
  @Override
  public Double getTopP() {
    return getLlmConfigParam("topP", Double.class);
  }

  @Override
  public OrchestrationChatOptions copy() {
    var copy = new OrchestrationChatOptions();
    copy.config = this.config;
    copy.templateParameters.putAll(this.templateParameters);
    return copy;
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  private <T> T getLlmConfigParam(@Nonnull final String param, @Nonnull final Class<T> defaultValue) {
    return ((LinkedHashMap<String, T>) getLlmConfigNonNull().getModelParams()).get(param);
  }

  @Nonnull
  private LLMModuleConfig getLlmConfigNonNull() {
    return Objects.requireNonNull(config.getLlmConfig(), "LLM config is not set");
  }
}
