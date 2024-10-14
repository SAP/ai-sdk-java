package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.OrchestrationConfigDelegate;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
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
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.NONE)
public class OrchestrationChatOptions
    implements ChatOptions, OrchestrationConfig<OrchestrationChatOptions> {
  @Delegate @Nonnull
  private final OrchestrationConfigDelegate<OrchestrationChatOptions> delegate =
      new OrchestrationConfigDelegate<>(this);

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
    delegate.withTemplate(TemplatingModuleConfig.create().template(toChatMessages(template)));
    return this;
  }

  static List<ChatMessage> toChatMessages(@Nonnull final List<Message> messages) {
    return messages.stream()
        .map(m -> ChatMessage.create().role(m.getMessageType().getValue()).content(m.getContent()))
        .toList();
  }

  // region satisfy the ChatOptions interface, delegating to the LLM config
  @Nullable
  @Override
  public String getModel() {
    return delegate.getLlmConfig().map(LLMModuleConfig::getModelName).getOrNull();
  }

  @Nullable
  String getModelVersion() {
    return delegate.getLlmConfig().map(LLMModuleConfig::getModelVersion).getOrNull();
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
    // TODO: implement, needed for chat memory apparently
    throw new RuntimeException("Not implemented");
  }

  @SuppressWarnings("unchecked")
  @Nullable
  private <T> T getLlmConfigParam(@Nonnull final String param) {
    return delegate
        .getLlmConfig()
        .map(LLMModuleConfig::getModelParams)
        .map(it -> (Map<String, Object>) it)
        .map(m -> (T) m.get(param))
        .getOrNull();
  }
  // endregion
}
