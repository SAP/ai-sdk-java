package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.FREQUENCY_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.MAX_TOKENS;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.PRESENCE_PENALTY;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TEMPERATURE;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.Parameter.TOP_P;
import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.orchestration.model.FunctionObject;
import com.sap.ai.sdk.orchestration.model.LLMModelDetails;
import com.sap.ai.sdk.orchestration.model.Template;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
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
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions implements ToolCallingChatOptions {

  private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  @Nonnull private OrchestrationModuleConfig config;

  @Nonnull private List<ToolCallback> toolCallbacks = List.of();

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

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return ((Map<String, T>) getLlmConfigNonNull().getParams()).get(param);
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
   * @since 1.25.0
   */
  public static final class Builder implements ToolCallingChatOptions.Builder<Builder> {
    @Nonnull private final OrchestrationChatOptions source;
    @Nonnull private List<ToolCallback> toolCallbacks;
    @Nonnull private Set<String> toolNames;
    @Nonnull private Map<String, Object> toolContext;
    @Nullable private String modelName;
    @Nonnull private final Map<String, Object> paramOverrides = new LinkedHashMap<>();
    @Nonnull private OrchestrationModuleConfig config;

    private Builder(@Nonnull final OrchestrationChatOptions source) {
      this.source = source;
      this.toolCallbacks = source.getToolCallbacks();
      this.toolNames = source.getToolNames();
      this.toolContext = source.getToolContext();
      this.config = source.getConfig();
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
        final Builder result = new Builder(that.source);
        result.toolCallbacks(this.toolCallbacks).toolContext(this.toolContext);
        result.toolNames = that.toolNames;
        result.modelName = that.modelName;
        result.paramOverrides.putAll(that.paramOverrides);
        return result;
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
      val mutable = new HashMap<>(toolContext);
      mutable.put(key, value);
      this.toolContext = Map.copyOf(mutable);
      return this;
    }

    @Override
    @Nonnull
    public Builder model(@Nullable final String model) {
      this.modelName = model;
      return this;
    }

    @Override
    @Nonnull
    public Builder frequencyPenalty(@Nullable final Double v) {
      paramOverrides.put(FREQUENCY_PENALTY.getName(), v);
      return this;
    }

    @Override
    @Nonnull
    public Builder maxTokens(@Nullable final Integer v) {
      paramOverrides.put(MAX_TOKENS.getName(), v);
      return this;
    }

    @Override
    @Nonnull
    public Builder presencePenalty(@Nullable final Double v) {
      paramOverrides.put(PRESENCE_PENALTY.getName(), v);
      return this;
    }

    @Override
    @Nonnull
    public Builder stopSequences(@Nullable final List<String> v) {
      paramOverrides.put("stop_sequences", v);
      return this;
    }

    @Override
    @Nonnull
    public Builder temperature(@Nullable final Double v) {
      paramOverrides.put(TEMPERATURE.getName(), v);
      return this;
    }

    @Override
    @Nonnull
    public Builder topK(@Nullable final Integer v) {
      paramOverrides.put("top_k", v);
      return this;
    }

    @Override
    @Nonnull
    public Builder topP(@Nullable final Double v) {
      paramOverrides.put(TOP_P.getName(), v);
      return this;
    }

    private Builder toolNames(@Nonnull final Set<String> toolNames) {
      this.toolNames = toolNames;
      return this;
    }

    private Builder config(@Nonnull final OrchestrationModuleConfig config) {
      this.config = config;
      return this;
    }

    @Override
    @Nonnull
    public OrchestrationChatOptions build() {
      val copyConfig =
          new OrchestrationModuleConfig()
              .withTemplateConfig(source.config.getTemplateConfig())
              .withFilteringConfig(source.config.getFilteringConfig())
              .withLlmConfig(source.config.getLlmConfig())
              .withMaskingConfig(source.config.getMaskingConfig())
              .withGroundingConfig(source.config.getGroundingConfig());
      val result = new OrchestrationChatOptions(copyConfig);

      if (modelName != null || !paramOverrides.isEmpty()) {
        final LLMModelDetails existingLlm = result.getLlmConfigNonNull();
        final Map<String, Object> mergedParams = new LinkedHashMap<>();
        if (existingLlm.getParams() != null) {
          mergedParams.putAll(existingLlm.getParams());
        }
        mergedParams.putAll(paramOverrides);
        final LLMModelDetails newLlm =
            LLMModelDetails.create()
                .name(modelName != null ? modelName : existingLlm.getName())
                .version(existingLlm.getVersion())
                .params(mergedParams);
        result.config = result.getConfig().withLlmConfig(newLlm);
      }

      result.toolCallbacks = toolCallbacks;
      result.toolContext = toolContext;
      result.toolNames = toolNames;
      return result;
    }
  }

  @Nonnull
  private LLMModelDetails getLlmConfigNonNull() {
    return Objects.requireNonNull(
        config.getLlmConfig(),
        "LLM config is not set. Please set it: new OrchestrationChatOptions(new OrchestrationModuleConfig().withLlmConfig(...))");
  }

  /**
   * Returns the config with any tool callbacks converted and injected into the template.
   *
   * @return the config enriched with tool definitions from {@link #getToolCallbacks()}
   */
  @Nonnull
  public OrchestrationModuleConfig getConfigWithCallbacks() {
    if (toolCallbacks.isEmpty()) {
      return config;
    }
    final List<ChatCompletionTool> converted =
        toolCallbacks.stream().map(OrchestrationChatOptions::toOrchestrationTool).toList();
    final var existingTemplate = config.getTemplateConfig() instanceof Template t ? t : null;
    final var mergedTools = new ArrayList<ChatCompletionTool>();
    if (existingTemplate != null && existingTemplate.getTools() != null) {
      mergedTools.addAll(existingTemplate.getTools());
    }
    mergedTools.addAll(converted);
    final Template newTemplate = Template.create().template(List.of()).tools(mergedTools);
    if (existingTemplate != null) {
      if (existingTemplate.getTemplate() != null) {
        newTemplate.template(existingTemplate.getTemplate());
      }
      if (existingTemplate.getDefaults() != null) {
        newTemplate.defaults(existingTemplate.getDefaults());
      }
    }
    return config.withTemplateConfig(newTemplate);
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
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Failed to parse tool input schema for tool: " + toolDef.name(), e);
    }
  }
}
