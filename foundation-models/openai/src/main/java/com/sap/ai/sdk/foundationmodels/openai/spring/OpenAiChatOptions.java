package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool.TypeEnum;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

@Data
public class OpenAiChatOptions implements ToolCallingChatOptions {

  private List<FunctionCallback> functionCallbacks;

  private List<ChatCompletionTool> tools;

  @Getter(AccessLevel.NONE)
  private Boolean internalToolExecutionEnabled;

  private Set<String> toolNames;

  private Map<String, Object> toolContext;

  @Nonnull
  @Override
  public List<FunctionCallback> getToolCallbacks() {
    return functionCallbacks;
  }

  @Override
  @Deprecated
  public void setFunctionCallbacks(@Nonnull final List<FunctionCallback> toolCallbacks) {
    setToolCallbacks(toolCallbacks);
  }

  @Override
  public void setToolCallbacks(@Nonnull final List<FunctionCallback> toolCallbacks) {
    this.functionCallbacks = toolCallbacks;
    tools = toolCallbacks.stream().map(OpenAiChatOptions::toOpenAiTool).toList();
  }

  private static ChatCompletionTool toOpenAiTool(FunctionCallback functionCallback) {
    return new ChatCompletionTool()
        .type(TypeEnum.FUNCTION)
        .function(
            new FunctionObject()
                .name(functionCallback.getName())
                .description(functionCallback.getDescription())
                .parameters(ModelOptionsUtils.jsonToMap(functionCallback.getInputTypeSchema())));
  }

  @Override
  public Boolean isInternalToolExecutionEnabled() {
    return true;
  }

  @Override
  public void setInternalToolExecutionEnabled(Boolean internalToolExecutionEnabled) {}

  @Override
  public Set<String> getFunctions() {
    return Set.of();
  }

  @Override
  public void setFunctions(Set<String> functions) {}

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
    return null;
  }
}
