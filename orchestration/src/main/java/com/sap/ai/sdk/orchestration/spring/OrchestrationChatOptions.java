package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.DefaultOrchestrationConfig;
import com.sap.ai.sdk.orchestration.LlmConfig;
import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.TemplateConfig;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;

/** Configuration to be used for orchestration requests. */
@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions
    implements ChatOptions, OrchestrationConfig<OrchestrationChatOptions> {
  private interface IDelegate extends OrchestrationConfig<OrchestrationChatOptions> {}

  @Getter(AccessLevel.NONE)
  @Nonnull
  @Delegate(types = IDelegate.class)
  private final DefaultOrchestrationConfig<OrchestrationChatOptions> delegate =
      DefaultOrchestrationConfig.asDelegateFor(this);

  @Getter(AccessLevel.PUBLIC)
  @Nonnull
  private Map<String, String> templateParameters = Map.of();

  @Nonnull
  public OrchestrationChatOptions withTemplateParameters(
      @Nonnull final Map<String, String> templateParameters) {
    this.templateParameters = templateParameters;
    return this;
  }

  @Nonnull
  public OrchestrationChatOptions withTemplate(@Nonnull final List<Message> template) {
    delegate.withTemplate(TemplateConfig.fromMessages(toChatMessages(template)));
    return this;
  }

  @Nonnull
  static List<ChatMessage> toChatMessages(@Nonnull final List<Message> messages) {
    return messages.stream()
        .map(m -> ChatMessage.create().role(m.getMessageType().getValue()).content(m.getContent()))
        .toList();
  }

  // region satisfy the ChatOptions interface, delegating to the LLM config
  @Nullable
  @Override
  public String getModel() {
    return delegate.getLlmConfig().map(AiModel::name).getOrNull();
  }

  @Nullable
  String getModelVersion() {
    return delegate.getLlmConfig().map(AiModel::version).getOrNull();
  }

  @Nullable
  @Override
  public Double getFrequencyPenalty() {
    return getLlmConfigParam("frequencyPenalty");
  }

  @Nullable
  @Override
  public Integer getMaxTokens() {
    return getLlmConfigParam("maxTokens");
  }

  @Nullable
  @Override
  public Double getPresencePenalty() {
    return getLlmConfigParam("presencePenalty");
  }

  @Nullable
  @Override
  public List<String> getStopSequences() {
    return getLlmConfigParam("stopSequences");
  }

  @Nullable
  @Override
  public Double getTemperature() {
    return getLlmConfigParam("temperature");
  }

  @Nullable
  @Override
  public Integer getTopK() {
    return getLlmConfigParam("topK");
  }

  @Nullable
  @Override
  public Double getTopP() {
    return getLlmConfigParam("topP");
  }

  @Override
  public OrchestrationChatOptions copy() {
    var copy = new OrchestrationChatOptions();
    copy.delegate.copyFrom(this.delegate);
    copy.templateParameters.putAll(this.templateParameters);
    return copy;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return delegate
        .getLlmConfig()
        .filter(LlmConfig.class::isInstance)
        .map(LlmConfig.class::cast)
        .map(LlmConfig::parameters)
        .map(m -> (T) m.get(param))
        .getOrNull();
  }
  // endregion
}
