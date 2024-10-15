package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
@AllArgsConstructor
public class OrchestrationPrompt implements OrchestrationConfig<OrchestrationPrompt> {
  @Nonnull List<ChatMessage> messages;
  @Nonnull Map<String, String> templateParameters;

  @Getter(AccessLevel.NONE)
  @Delegate
  @Nonnull
  DefaultOrchestrationConfig<OrchestrationPrompt> delegate = new DefaultOrchestrationConfig<>();

  @Nonnull
  @Override
  public OrchestrationPrompt instance() {
    return this;
  }

  public OrchestrationPrompt(@Nonnull final String message) {
    this(List.of(ChatMessage.create().role("user").content(message)), Map.of());
  }

  public OrchestrationPrompt(@Nonnull final List<ChatMessage> messagesHistory) {
    this(messagesHistory, Map.of());
  }

  public OrchestrationPrompt(@Nonnull final Map<String, String> inputParams) {
    this(List.of(), inputParams);
  }

  @Nonnull
  ModuleConfigs toModuleConfigDTO(@Nonnull final OrchestrationConfig<?> defaults) {
    // duplicate the prompt config so it isn't modified, to make sure this prompt can be reused
    var config = new DefaultOrchestrationConfig<>().copyFrom(this).copyFrom(defaults);
    return DefaultOrchestrationConfig.toModuleConfigDTO(config, messages);
  }
}
