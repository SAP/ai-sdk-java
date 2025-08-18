package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;

import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.prompt.registry.PromptClient;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.Template;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

/** Utility class for orchestration-related operations in a Spring context. */
public class OrchestrationSpringUtil {
  private static final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
  private static final OrchestrationChatOptions defaultOptions =
      new OrchestrationChatOptions(config);

  private OrchestrationSpringUtil() {
    // Utility class, no instantiation allowed
  }

  /**
   * Get a prompt template by name and input parameters.
   *
   * @param templateName the name of the prompt template
   * @param inputParams a map of input parameters to substitute in the template
   * @return a Prompt object containing the messages from the template
   */
  @Nonnull
  public static Prompt getPromptTemplate(
      @Nonnull final String templateName, @Nonnull final Map<String, Object> inputParams) {
    val templateMessages =
        new PromptClient()
            .parsePromptTemplateByNameVersion(
                "MyScenario",
                "1.0.0",
                templateName,
                "default",
                false,
                PromptTemplateSubstitutionRequest.create().inputParams(inputParams))
            .getParsedPrompt();

    // TRANSFORM TEMPLATE TO SPRING AI MESSAGES
    val messages =
        templateMessages.stream()
            .map(
                (Template t) -> {
                  final SingleChatTemplate message = (SingleChatTemplate) t;
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
    return new Prompt(messages, defaultOptions);
  }
}
