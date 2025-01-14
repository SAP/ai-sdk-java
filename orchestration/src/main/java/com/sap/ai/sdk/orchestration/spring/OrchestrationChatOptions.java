package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;

import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.ai.chat.prompt.ChatOptions;

/** Configuration to be used for orchestration requests. */
@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions implements ChatOptions {

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

  private void setModel(@Nonnull final String model) {
    getLlmConfigNonNull().setModelName(model);
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

  private void setModelVersion(@Nonnull final String modelVersion) {
    getLlmConfigNonNull().setModelVersion(modelVersion);
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

  private void setFrequencyPenalty(@Nonnull final Double frequencyPenalty) {
    setLlmConfigParam(FREQUENCY_PENALTY.getName(), frequencyPenalty);
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

  private void setMaxTokens(@Nonnull final Integer maxTokens) {
    setLlmConfigParam(MAX_TOKENS.getName(), maxTokens);
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

  private void setPresencePenalty(@Nonnull final Double presencePenalty) {
    setLlmConfigParam(PRESENCE_PENALTY.getName(), presencePenalty);
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

  private void setStopSequences(@Nonnull final List<String> stopSequences) {
    setLlmConfigParam("stop_sequences", stopSequences);
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

  private void setTemperature(@Nonnull final Double temperature) {
    setLlmConfigParam(TEMPERATURE.getName(), temperature);
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

  private void setTopK(@Nonnull final Integer topK) {
    setLlmConfigParam("top_k", topK);
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

  private void setTopP(@Nonnull final Double topP) {
    setLlmConfigParam(TOP_P.getName(), topP);
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
    val copy = new OrchestrationChatOptions(config);
    copy.setModel(this.getModel());
    copy.setModelVersion(this.getModelVersion());
    if (getFrequencyPenalty() != null) {
      copy.setFrequencyPenalty(this.getFrequencyPenalty());
    }
    if (getMaxTokens() != null) {
      copy.setMaxTokens(this.getMaxTokens());
    }
    if (getPresencePenalty() != null) {
      copy.setPresencePenalty(this.getPresencePenalty());
    }
    if (getStopSequences() != null) {
      copy.setStopSequences(new ArrayList<>(this.getStopSequences()));
    }
    if (getTemperature() != null) {
      copy.setTemperature(this.getTemperature());
    }
    if (getTopK() != null) {
      copy.setTopK(this.getTopK());
    }
    if (getTopP() != null) {
      copy.setTopP(this.getTopP());
    }
    return (T) copy;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    if (getLlmConfigNonNull().getModelParams() instanceof LinkedHashMap) {
      return ((LinkedHashMap<String, T>) getLlmConfigNonNull().getModelParams()).get(param);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private <T> void setLlmConfigParam(@Nonnull final String param, @Nonnull final T value) {
    ((LinkedHashMap<String, T>) getLlmConfigNonNull().getModelParams()).put(param, value);
  }

  @Nonnull
  private LLMModuleConfig getLlmConfigNonNull() {
    return Objects.requireNonNull(config.getLlmConfig(), "LLM config is not set");
  }
}
