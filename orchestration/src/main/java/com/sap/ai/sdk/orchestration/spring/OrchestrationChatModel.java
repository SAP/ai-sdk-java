package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.AssistantMessage;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.UserMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

/** Spring AI integration for the orchestration service. */
@Slf4j
@RequiredArgsConstructor
public class OrchestrationChatModel implements ChatModel {
  @Nonnull private final OrchestrationClient client = new OrchestrationClient();

  @Override
  public ChatResponse call(Prompt prompt) {
    val orchestrationPrompt = toOrchestrationPrompt(prompt);
    val response = client.chatCompletion(orchestrationPrompt, ((OrchestrationChatOptions) prompt.getOptions()).getConfig());
    return OrchestrationChatResponse.fromOrchestrationResponse(response.getOriginalResponse());
  }

  @Nonnull
  private OrchestrationPrompt toOrchestrationPrompt(@Nonnull final Prompt prompt) {
    val messages = toOrchestrationMessages(prompt.getInstructions());
    return new OrchestrationPrompt(Map.of(), messages);
  }

  // endregion

  @Nonnull
  private static com.sap.ai.sdk.orchestration.Message[] toOrchestrationMessages(
      @Nonnull final List<Message> messages) {
    final Function<Message, com.sap.ai.sdk.orchestration.Message> mapper =
        msg ->
            switch (msg.getMessageType()) {
              case SYSTEM:
                yield new SystemMessage(msg.getText());
              case USER:
                yield new UserMessage(msg.getText());
              case ASSISTANT:
                yield new AssistantMessage(msg.getText());
              case TOOL:
                throw new IllegalArgumentException("Tool messages are not supported");
            };
    return messages.stream().map(mapper).toArray(com.sap.ai.sdk.orchestration.Message[]::new);
  }
}
