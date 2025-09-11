package com.sap.ai.sdk.prompt.registry.spring;

import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

/** Utility class for prompt registry related operations in a Spring context. */
public class SpringAiConverter {

  private SpringAiConverter() {
    // Utility class, no instantiation allowed
  }

  /**
   * Get a SpringAI list of messages from a Prompt Registry Response.
   *
   * @param promptResponse the response from Prompt Registry.
   * @return list of SpringAI messages.
   */
  @Nonnull
  public static List<Message> promptTemplateToMessages(
      @Nonnull final PromptTemplateSubstitutionResponse promptResponse) {

    val res = promptResponse.getParsedPrompt();

    // TRANSFORM TEMPLATE TO SPRING AI MESSAGES
    return res.stream()
        .map(
            (PromptTemplate t) -> {
              final SingleChatTemplate message = (SingleChatTemplate) t;
              return (Message)
                  switch (message.getRole()) {
                    case "system" -> new SystemMessage(message.getContent());
                    case "user" -> new UserMessage(message.getContent());
                    case "assistant" -> new AssistantMessage(message.getContent());
                    default ->
                        throw new IllegalArgumentException("Unknown role: " + message.getRole());
                  };
            })
        .toList();
  }
}
