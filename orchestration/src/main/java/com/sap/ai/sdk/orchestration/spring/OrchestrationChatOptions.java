package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.ConfigToRequestTransformer.toModuleConfigs;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.ai.chat.prompt.ChatOptions;

/**
 * Configuration to be used for orchestration requests.
 *
 * @since 1.2.0
 */
@Beta
@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions implements ChatOptions {

  private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  @Getter(AccessLevel.PUBLIC)
  @Setter(AccessLevel.PUBLIC)
  @Nonnull
  OrchestrationModuleConfig config;

  /**
   * Returns the model to use for the chat.
   *
   * @return the model to use for the chat
   * @see com.sap.ai.sdk.orchestration.OrchestrationAiModel
   */
  @Nonnull
  @Override
  public String getModel() {
    return getLlmConfigNonNull().getModelName();
  }

  /**
   * Returns the model version to use for the chat. "latest" by default.
   *
   * @return the model version to use for the chat.
   */
  @Nonnull
  public String getModelVersion() {
    return getLlmConfigNonNull().getModelVersion();
  }

  /**
   * Returns the frequency penalty to use for the chat.
   *
   * @return the frequency penalty to use for the chat
   */
  @Nullable
  @Override
  public Double getFrequencyPenalty() {
    return getLlmConfigParam(FREQUENCY_PENALTY.getName());
  }

  /**
   * Returns the maximum number of tokens to use for the chat.
   *
   * @return the maximum number of tokens to use for the chat
   */
  @Nullable
  @Override
  public Integer getMaxTokens() {
    return getLlmConfigParam(MAX_TOKENS.getName());
  }

  /**
   * Returns the presence penalty to use for the chat.
   *
   * @return the presence penalty to use for the chat
   */
  @Nullable
  @Override
  public Double getPresencePenalty() {
    return getLlmConfigParam(PRESENCE_PENALTY.getName());
  }

  /**
   * Returns the stop sequences to use for the chat.
   *
   * @return the stop sequences to use for the chat
   */
  @Nullable
  @Override
  public List<String> getStopSequences() {
    return getLlmConfigParam("stop_sequences");
  }

  /**
   * Returns the temperature to use for the chat.
   *
   * @return the temperature to use for the chat
   */
  @Nullable
  @Override
  public Double getTemperature() {
    return getLlmConfigParam(TEMPERATURE.getName());
  }

  /**
   * Returns the top K to use for the chat.
   *
   * @return the top K to use for the chat
   */
  @Nullable
  @Override
  public Integer getTopK() {
    return getLlmConfigParam("top_k");
  }

  /**
   * Returns the top P to use for the chat.
   *
   * @return the top P to use for the chat
   */
  @Nullable
  @Override
  public Double getTopP() {
    return getLlmConfigParam(TOP_P.getName());
  }

  /**
   * Returns a copy of this {@link OrchestrationChatOptions}.
   *
   * @return a copy of this {@link OrchestrationChatOptions}
   */
  @SuppressWarnings("unchecked") // The same suppress is in DefaultChatOptions
  @Nonnull
  @Override
  public <T extends ChatOptions> T copy() {
    try {
      val json = JACKSON.writeValueAsString(toModuleConfigs(config));
      val copy = JACKSON.readValue(json, ModuleConfigs.class);
      val copyConfig =
          new OrchestrationModuleConfig()
              .withTemplateConfig(copy.getTemplatingModuleConfig())
              .withFilteringConfig(copy.getFilteringModuleConfig())
              .withLlmConfig(copy.getLlmModuleConfig())
              .withMaskingConfig(copy.getMaskingModuleConfig())
              .withGroundingConfig(copy.getGroundingModuleConfig());
      return (T) new OrchestrationChatOptions(copyConfig);

    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException(
          "Orchestration module configuration not readable: " + config, e);
    }
  }

  @SuppressWarnings("unchecked") // getModelParams() returns Object, it should return Map
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    if (getLlmConfigNonNull().getModelParams() instanceof Map) {
      return ((Map<String, T>) getLlmConfigNonNull().getModelParams()).get(param);
    }
    return null;
  }

  @Nonnull
  private LLMModuleConfig getLlmConfigNonNull() {
    return Objects.requireNonNull(
        config.getLlmConfig(),
        "LLM config is not set. Please set it: new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(...))");
  }
}
