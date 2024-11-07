package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;

/**
 * Language model configuration for the orchestration service.
 *
 * <p>Please note that, depending on which model you are using, some parameters may be required.
 * Furthermore, do not set the "stream" parameter here but use {@link
 * OrchestrationClient#streamChatCompletion(String)} instead.
 *
 * @param name The name of the language model.
 * @param version Optional, the version of the language model.
 * @param parameters Optional, additional parameters of the language model.
 * @see AiModel
 * @see OrchestrationConfig#withLlmConfig(AiModel)
 */
public record LlmConfig(
    @Nonnull String name, @Nullable String version, @Nullable Map<String, ?> parameters)
    implements AiModel {
  /**
   * Reference a language model by name.
   *
   * @param name The name of the language model.
   */
  public LlmConfig(@Nonnull final String name) {
    this(name, null, null);
  }

  /**
   * Reference a language model by name and version.
   *
   * @param name The name of the language model.
   * @param version The version of the language model.
   */
  public LlmConfig(@Nonnull final String name, @Nonnull final String version) {
    this(name, version, null);
  }

  /**
   * Reference a language model by name and parameters.
   *
   * @param name The name of the language model.
   * @param parameters Additional parameters of the language model.
   */
  public LlmConfig(@Nonnull final String name, @Nonnull final Map<String, ?> parameters) {
    this(name, null, parameters);
  }

  @Nonnull
  LLMModuleConfig toLLMModuleConfigDto() {
    final Map<String, Object> params = new TreeMap<>();
    if (parameters != null) {
      params.putAll(parameters);
    }
    val result = new LLMModuleConfig().modelName(name).modelParams(params);
    if (version != null) {
      result.modelVersion(version);
    }
    return result;
  }
}
