package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionConfig;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import io.vavr.control.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;

/** OpenAI Chat Options for configuring tool callbacks and execution settings. */
@Data
@NoArgsConstructor
public class OpenAiChatOptions implements ToolCallingChatOptions {

  @Nonnull private OpenAiChatCompletionConfig config;

  @Nonnull private List<ToolCallback> toolCallbacks = List.of();

  @Nonnull private List<ChatCompletionTool> tools = List.of();

  @Getter(AccessLevel.NONE)
  @Nullable
  private Boolean internalToolExecutionEnabled;

  @Nonnull private Set<String> toolNames = Set.of();

  @Nonnull private Map<String, Object> toolContext = Map.of();

  @Override
  public void setToolCallbacks(@Nonnull final List<ToolCallback> toolCallbacks) {
    this.toolCallbacks = toolCallbacks;
    tools = toolCallbacks.stream().map(OpenAiChatOptions::toOpenAiTool).toList();
  }

  @Nullable
  @Override
  public Boolean getInternalToolExecutionEnabled() {
    return this.internalToolExecutionEnabled;
  }

  private static ChatCompletionTool toOpenAiTool(final ToolCallback toolCallback) {
    val toolDef = toolCallback.getToolDefinition();
    val functionobject =
        new FunctionObject()
            .name(toolDef.name())
            .description(toolDef.description())
            .parameters(ModelOptionsUtils.jsonToMap(toolDef.inputSchema()));
    return new ChatCompletionTool().type(TypeEnum.FUNCTION).function(functionobject);
  }

  @Override
  public void setInternalToolExecutionEnabled(
      @Nullable final Boolean internalToolExecutionEnabled) {}

  @Override
  @Nonnull
  public String getModel() {
    throw new UnsupportedOperationException(
        "Model declaration not supported in OpenAI integration.");
  }

  @Override
  @Nullable
  public Double getFrequencyPenalty() {
    return Option.of(config.getFrequencyPenalty()).map(BigDecimal::doubleValue).getOrNull();
  }

  @Override
  @Nullable
  public Integer getMaxTokens() {
    return config.getMaxTokens();
  }

  @Override
  @Nullable
  public Double getPresencePenalty() {
    return Option.of(config.getPresencePenalty()).map(BigDecimal::doubleValue).getOrNull();
  }

  @Override
  @Nullable
  public List<String> getStopSequences() {
    return config.getStop();
  }

  @Override
  @Nullable
  public Double getTemperature() {
    return Option.of(config.getTemperature()).map(BigDecimal::doubleValue).getOrNull();
  }

  @Override
  @Nullable // this is available here but not in OpenAiChatCompletionConfig so added it there ?
  public Integer getTopK() {
    return config.getTopK();
  }

  @Override
  @Nullable
  public Double getTopP() {
    return Option.of(config.getTopP()).map(BigDecimal::doubleValue).getOrNull();
  }

  @Override
  @Nonnull
  public <T extends ChatOptions> T copy() {
    final OpenAiChatOptions copy = new OpenAiChatOptions();
    copy.setToolCallbacks(this.toolCallbacks);
    copy.setInternalToolExecutionEnabled(this.internalToolExecutionEnabled);
    copy.setTools(this.tools);
    copy.setToolNames(this.toolNames);
    copy.setToolContext(this.toolContext);
    return (T) copy;
  }
}
