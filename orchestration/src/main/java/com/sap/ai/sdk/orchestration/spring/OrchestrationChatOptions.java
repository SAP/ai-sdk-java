package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.orchestration.model.FunctionObject;
import com.sap.ai.sdk.orchestration.model.LLMModelDetails;
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
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;

/**
 * Configuration to be used for orchestration requests.
 *
 * @since 1.2.0
 */
@Data
public class OrchestrationChatOptions implements ToolCallingChatOptions {

  private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  @Nonnull private OrchestrationModuleConfig config;

  @Nonnull private List<ToolCallback> toolCallbacks = List.of();

  @Getter(AccessLevel.NONE)
  @Nullable
  private Boolean internalToolExecutionEnabled;

  @Nonnull private Set<String> toolNames = Set.of();

  @Nonnull private Map<String, Object> toolContext = Map.of();

  /**
   * Returns the model to use for the chat.
   *
   * @return the model to use for the chat
   * @see com.sap.ai.sdk.orchestration.OrchestrationAiModel
   */
  @Nonnull
  @Override
  public String getModel() {
    return getLlmConfigNonNull().getName();
  }

  /**
   * Returns the model version to use for the chat. "latest" by default.
   *
   * @return the model version to use for the chat.
   */
  @Nonnull
  public String getModelVersion() {
    return getLlmConfigNonNull().getVersion();
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
   * @param <T> option subtype
   * @return a copy of this {@link OrchestrationChatOptions}
   */
  @SuppressWarnings("unchecked") // The same suppress is in DefaultChatOptions
  @Nonnull
  public <T extends ChatOptions> T copy() {
    // note: this is a shallow copy
    val copyConfig =
        new OrchestrationModuleConfig()
            .withTemplateConfig(config.getTemplateConfig())
            .withFilteringConfig(config.getFilteringConfig())
            .withLlmConfig(config.getLlmConfig())
            .withMaskingConfig(config.getMaskingConfig())
            .withGroundingConfig(config.getGroundingConfig());
    val result = new OrchestrationChatOptions(copyConfig);
    result.setToolCallbacks(toolCallbacks);
    result.setInternalToolExecutionEnabled(internalToolExecutionEnabled);
    return (T) result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return ((Map<String, T>) getLlmConfigNonNull().getParams()).get(param);
  }

  /**
   * Setter method
   *
   * @param toolCallbacks tool callbacks to set int template config
   */
  public void setToolCallbacks(@Nonnull final List<ToolCallback> toolCallbacks) {
    this.toolCallbacks = toolCallbacks;
    final Template template =
        Objects.requireNonNullElse(
            (Template) config.getTemplateConfig(), Template.create().template());
    val tools = toolCallbacks.stream().map(OrchestrationChatOptions::toOrchestrationTool).toList();
    config = config.withTemplateConfig(template.tools(tools));
  }

  /**
   * Getter method
   *
   * @return if internal tool execution enabled
   */
  @Nullable
  public Boolean isInternalToolExecutionEnabled() {
    return this.internalToolExecutionEnabled;
  }

  @Nonnull
  @Override
  public Builder mutate() {
    return new Builder(this);
  }

  /**
   * Builder that preserves {@link OrchestrationChatOptions} through the Spring AI advisor chain.
   * Spring AI 2.x {@code ChatClient} calls {@code mutate().build()} to reconstruct the options
   * after passing through advisors; returning {@code OrchestrationChatOptions} here ensures the
   * type is not lost.
   *
   * @since 1.3.0
   */
  public static final class Builder implements ToolCallingChatOptions.Builder<Builder> {
    @Nonnull private final OrchestrationChatOptions source;
    @Nonnull private List<ToolCallback> toolCallbacks;
    @Nonnull private Map<String, Object> toolContext;

    private Builder(@Nonnull final OrchestrationChatOptions source) {
      this.source = source;
      this.toolCallbacks = source.getToolCallbacks();
      this.toolContext = source.getToolContext();
    }

    @Override
    @Nonnull
    public Builder clone() {
      return new Builder(source);
    }

    @Override
    @Nonnull
    public Builder combineWith(@Nonnull final ChatOptions.Builder<?> other) {
      if (other instanceof OrchestrationChatOptions.Builder that) {
        // Per-request builder overrides model-level defaults
        this.toolCallbacks = that.toolCallbacks;
        this.toolContext = that.toolContext;
        // Use the per-request source for all OrchestrationChatOptions-specific config
        return new Builder(that.source)
            .toolCallbacks(this.toolCallbacks)
            .toolContext(this.toolContext);
      }
      return this;
    }

    @Override
    @Nonnull
    public Builder toolCallbacks(@Nonnull final List<ToolCallback> callbacks) {
      this.toolCallbacks = callbacks;
      return this;
    }

    @Override
    @Nonnull
    public Builder toolCallbacks(@Nonnull final ToolCallback... callbacks) {
      this.toolCallbacks = List.of(callbacks);
      return this;
    }

    @Override
    @Nonnull
    public Builder toolContext(@Nonnull final Map<String, Object> ctx) {
      this.toolContext = ctx;
      return this;
    }

    @Override
    @Nonnull
    public Builder toolContext(@Nonnull final String key, @Nonnull final Object value) {
      val mutable = new java.util.HashMap<>(toolContext);
      mutable.put(key, value);
      this.toolContext = Map.copyOf(mutable);
      return this;
    }

    @Override
    @Nonnull
    public Builder model(@Nullable final String model) {
      return this;
    }

    @Override
    @Nonnull
    public Builder frequencyPenalty(@Nullable final Double v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder maxTokens(@Nullable final Integer v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder presencePenalty(@Nullable final Double v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder stopSequences(@Nullable final List<String> v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder temperature(@Nullable final Double v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder topK(@Nullable final Integer v) {
      return this;
    }

    @Override
    @Nonnull
    public Builder topP(@Nullable final Double v) {
      return this;
    }

    @Override
    @Nonnull
    public OrchestrationChatOptions build() {
      @SuppressWarnings("unchecked")
      final OrchestrationChatOptions result = source.<OrchestrationChatOptions>copy();
      result.setToolCallbacks(toolCallbacks);
      result.setToolContext(toolContext);
      return result;
    }
  }

  @Nonnull
  private LLMModelDetails getLlmConfigNonNull() {
    return Objects.requireNonNull(
        config.getLlmConfig(),
        "LLM config is not set. Please set it: new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(...))");
  }

  private static ChatCompletionTool toOrchestrationTool(@Nonnull final ToolCallback toolCallback) {
    val toolDef = toolCallback.getToolDefinition();
    try {
      final Map<String, Object> params =
          JACKSON.readValue(toolDef.inputSchema(), new TypeReference<>() {});
      return ChatCompletionTool.create()
          .type(TypeEnum.FUNCTION)
          .function(
              FunctionObject.create()
                  .name(toolDef.name())
                  .description(toolDef.description())
                  .parameters(params));
    } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Failed to parse tool input schema for tool: " + toolDef.name(), e);
    }
  }
}
