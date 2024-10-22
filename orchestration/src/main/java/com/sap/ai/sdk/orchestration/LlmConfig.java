package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

public record LlmConfig(
    @Nonnull String name, @Nullable String version, @Nullable Map<String, ?> parameters)
    implements AiModel {
  public LlmConfig(@Nonnull final String name) {
    this(name, null, null);
  }

  public LlmConfig(@Nonnull final String name, @Nonnull final String version) {
    this(name, version, null);
  }

  public LlmConfig(@Nonnull final String name, @Nonnull final Map<String, ?> parameters) {
    this(name, null, parameters);
  }

  @Nonnull
  LLMModuleConfig toLLMModuleConfigDTO() {
    final Map<String, Object> params = new TreeMap<>();
    if (parameters != null) {
      params.putAll(parameters);
    }
    val result = LLMModuleConfig.create().modelName(name).modelParams(params);
    if (version != null) {
      result.modelVersion(version);
    }
    return result;
  }
}
