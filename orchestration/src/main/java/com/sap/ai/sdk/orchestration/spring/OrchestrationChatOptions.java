package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.orchestration.model.FunctionObject;
import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.model.Template;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.val;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

/**
 * Configuration to be used for orchestration requests.
 *
 * @since 1.2.0
 */
@Beta
@Data
@Getter(AccessLevel.PUBLIC)
public class OrchestrationChatOptions implements ToolCallingChatOptions {

  @Getter(AccessLevel.NONE)
  private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  @Nonnull private OrchestrationModuleConfig config;

  private List<FunctionCallback> functionCallbacks;

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
    // note: this is a shallow copy
    val copyConfig =
        new OrchestrationModuleConfig()
            .withTemplateConfig(config.getTemplateConfig())
            .withFilteringConfig(config.getFilteringConfig())
            .withLlmConfig(config.getLlmConfig())
            .withMaskingConfig(config.getMaskingConfig())
            .withGroundingConfig(config.getGroundingConfig());
    return (T) new OrchestrationChatOptions(copyConfig);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return ((Map<String, T>) getLlmConfigNonNull().getModelParams()).get(param);
  }

  @Nonnull
  private LLMModuleConfig getLlmConfigNonNull() {
    return Objects.requireNonNull(
        config.getLlmConfig(),
        "LLM config is not set. Please set it: new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(...))");
  }

  @Nonnull
  @Override
  public List<FunctionCallback> getToolCallbacks() {
    return functionCallbacks;
  }

  @Override
  public void setToolCallbacks(@Nonnull final List<FunctionCallback> toolCallbacks) {
    setFunctionCallbacks(toolCallbacks);
  }

  @Nonnull
  @Override
  public List<FunctionCallback> getFunctionCallbacks() {
    return functionCallbacks;
  }

  @Override
  public void setFunctionCallbacks(@Nonnull final List<FunctionCallback> toolCallbacks) {
    this.functionCallbacks = toolCallbacks;
    final Template template =
        Objects.requireNonNullElse(
            (Template) config.getTemplateConfig(), Template.create().template());
    val tools =
        toolCallbacks.stream()
            .map(
                functionCallback ->
                    ChatCompletionTool.create()
                        .type(TypeEnum.FUNCTION)
                        .function(
                            FunctionObject.create()
                                .name(functionCallback.getName())
                                .description(functionCallback.getDescription())
                                .parameters(
                                    ModelOptionsUtils.jsonToMap(
                                        functionCallback.getInputTypeSchema()))))
            .toList();
    config = config.withTemplateConfig(template.tools(tools));
  }

  @Nonnull
  @Override
  public Set<String> getToolNames() {
    return Set.of();
  }

  @Override
  public void setToolNames(@Nonnull final Set<String> toolNames) {}

  @Nullable
  @Override
  public Boolean isInternalToolExecutionEnabled() {
    return null;
  }

  @Override
  public void setInternalToolExecutionEnabled(
      @Nullable final Boolean internalToolExecutionEnabled) {}

  @Nonnull
  @Override
  public Set<String> getFunctions() {
    return Set.of();
  }

  @Override
  public void setFunctions(@Nonnull final Set<String> functions) {
    //    val template =
    //        Objects.requireNonNullElse(
    //            (Template) config.getTemplateConfig(), Template.create().template());
    //    val tools =
    //        functionNames.stream()
    //            .map(
    //                functionName ->
    //                    ChatCompletionTool.create()
    //                        .type(TypeEnum.FUNCTION)
    //                        .function(FunctionObject.create().name(functionName)))
    //            .toList();
    //    config = config.withTemplateConfig(template.tools(tools));
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Nonnull
  @Override
  public Map<String, Object> getToolContext() {
    return Map.of();
  }

  @Override
  public void setToolContext(@Nonnull final Map<String, Object> tooContext) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
