package com.sap.ai.sdk.prompt.registry.spring;

import com.sap.ai.sdk.prompt.registry.model.ImageContent;
import com.sap.ai.sdk.prompt.registry.model.MultiChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.TextContent;
import java.util.List;
import java.util.stream.Collectors;
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
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  public static List<Message> promptTemplateToMessages(
      @Nonnull final PromptTemplateSubstitutionResponse promptResponse) {

    val res = promptResponse.getParsedPrompt();

    // TRANSFORM TEMPLATE TO SPRING AI MESSAGES
    return res.stream()
        .map(
            (PromptTemplate template) -> {
              if (template instanceof SingleChatTemplate message) {
                return fromRole(message.getRole(), message.getContent());
              }
              if (template instanceof MultiChatTemplate message) {
                return fromRole(message.getRole(), getMultiTemplateTextContent(message));
              }
              throw new IllegalArgumentException(
                  "Unsupported PromptTemplate type: " + template.getClass().getName());
            })
        .toList();
  }

  @Nonnull
  private static String getMultiTemplateTextContent(@Nonnull final MultiChatTemplate message) {
    return message.getContent().stream()
        .map(
            item -> {
              if (item instanceof TextContent textContent) {
                return textContent.getText();
              }
              if (item instanceof ImageContent) {
                throw new UnsupportedOperationException(
                    "MultiChatTemplate with image content is not supported by SpringAiConverter yet.");
              }
              throw new UnsupportedOperationException(
                  "Unsupported MultiChatContent type: " + item.getClass().getName());
            })
        .collect(Collectors.joining("\n"));
  }

  @Nonnull
  private static Message fromRole(@Nonnull final String role, @Nonnull final String content) {
    return switch (role) {
      case "system" -> new SystemMessage(content);
      case "user" -> new UserMessage(content);
      case "assistant" -> new AssistantMessage(content);
      default -> throw new IllegalArgumentException("Unknown role: " + role);
    };
  }
}
