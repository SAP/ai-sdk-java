package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.prompt.registry.PromptClient;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.Template;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.Map;
import java.util.UUID;

public class OrchestrationSpringUtil {
  private OrchestrationSpringUtil() {
    // Utility class, no instantiation allowed
  }

  public static Prompt getPromptTemplate(String templateName, Map<String, Object> inputParams) {
    var templateMessages =
        new PromptClient()
            .parsePromptTemplateByNameVersion(
                "categorization",
                "1.0.0",
                "PROMPT-CHART",
                "default",
                false,
                PromptTemplateSubstitutionRequest.create()
                    .inputParams(Map.of("current_timestamp", System.currentTimeMillis())))
            .getParsedPrompt();

    // TRANSFORM TEMPLATE TO SPRING AI MESSAGES
    var messages =
        templateMessages.stream()
            .map(
                (Template t) -> {
                  SingleChatTemplate message = (SingleChatTemplate) t;
                  return (Message)
                      switch (message.getRole()) {
                        case "system" -> new SystemMessage(message.getContent());
                        case "user" -> new UserMessage(message.getContent());
                        case "assistant" -> new AssistantMessage(message.getContent());
                        default ->
                            throw new IllegalArgumentException(
                                "Unknown role: " + message.getRole());
                      };
                })
            .toList();

    return new Prompt(messages);
  }
}
