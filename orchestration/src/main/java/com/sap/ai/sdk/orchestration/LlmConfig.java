package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record LlmConfig(
    @Nonnull String name, @Nullable String version, @Nullable Map<String, ?> parameters)
    implements AiModel {
  public LlmConfig(@Nonnull String name) {
    this(name, null, null);
  }

  public LlmConfig(@Nonnull String name, @Nonnull String version) {
    this(name, version, null);
  }

  public LlmConfig(@Nonnull String name, @Nonnull Map<String, ?> parameters) {
    this(name, null, parameters);
  }

  @Nonnull
  LLMModuleConfig toLLMModuleConfigDTO() {
    final Map<String, Object> params = new TreeMap<>();
    if (parameters != null) {
      params.putAll(parameters);
    }
    final var result = LLMModuleConfig.create().modelName(name).modelParams(params);
    if (version != null) {
      result.modelVersion(version);
    }
    return result;
  }
}
