package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class OrchestrationPrompt {
  @Nonnull
  OrchestrationConfig config;
  @Nullable List<ChatMessage> messages;
  @Nullable Map<String, String> templateParameters;

  public OrchestrationPrompt(String message) {
    this(
        new OrchestrationConfig(),
        List.of(ChatMessage.create().role("user").content(message)),
        null);
  }

  public OrchestrationPrompt(List<ChatMessage> messages, Map<String, String> templateParameters) {
    this(new OrchestrationConfig(), messages, templateParameters);
  }

  public OrchestrationPrompt(List<ChatMessage> messagesHistory) {
    this(new OrchestrationConfig(), messagesHistory, null);
  }


  public OrchestrationPrompt(Map<String, String> inputParams) {
    this(new OrchestrationConfig(), null, inputParams);
  }
}
