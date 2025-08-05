package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.val;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;

@Data
public class OpenAiChatOptions implements ToolCallingChatOptions {

  @Nonnull private List<ToolCallback> toolCallbacks = List.of();

  private List<ChatCompletionTool> tools;

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

  private static ChatCompletionTool toOpenAiTool(ToolCallback toolCallback) {
    val toolDef = toolCallback.getToolDefinition();
    return new ChatCompletionTool()
        .type(TypeEnum.FUNCTION)
        .function(
            new FunctionObject()
                .name(toolDef.name())
                .description(toolDef.description())
                .parameters(ModelOptionsUtils.jsonToMap(toolDef.inputSchema())));
  }

  @Override
  public void setInternalToolExecutionEnabled(Boolean internalToolExecutionEnabled) {}

  @Override
  public String getModel() {
    return "";
  }

  @Override
  public Double getFrequencyPenalty() {
    return 0.0;
  }

  @Override
  public Integer getMaxTokens() {
    return 0;
  }

  @Override
  public Double getPresencePenalty() {
    return 0.0;
  }

  @Override
  public List<String> getStopSequences() {
    return List.of();
  }

  @Override
  public Double getTemperature() {
    return 0.0;
  }

  @Override
  public Integer getTopK() {
    return 0;
  }

  @Override
  public Double getTopP() {
    return 0.0;
  }

  @Override
  public <T extends ChatOptions> T copy() {
    OpenAiChatOptions copy = new OpenAiChatOptions();
    copy.setToolCallbacks(this.toolCallbacks);
    copy.setInternalToolExecutionEnabled(this.internalToolExecutionEnabled);
    copy.setTools(this.tools);
    copy.setToolNames(this.toolNames);
    copy.setToolContext(this.toolContext);
    return (T) copy;
  }
}
