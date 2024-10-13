package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.spring.ChatResponseFactory.toChatResponse;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

/** Spring AI integration for the orchestration service. */
@Slf4j
public class OrchestrationChatModel implements ChatModel {
  private final OrchestrationClient client;

  public OrchestrationChatModel(OrchestrationClient client) {
    this.client = client;
  }

  @Override
  @Nonnull
  public ChatOptions getDefaultOptions() {
    return new OrchestrationChatOptions();
  }

  @Override
  public ChatResponse call(@Nonnull final Prompt prompt) {
    var orchestrationPrompt = toOrchestrationPrompt(prompt);
    var response = client.chatCompletion(orchestrationPrompt);
    return toChatResponse(response);
  }

  @Nonnull
  private static OrchestrationPrompt toOrchestrationPrompt(@Nonnull final Prompt prompt) {
    var messages =
        prompt.getInstructions().stream()
            .map(
                m ->
                    ChatMessage.create()
                        .role(m.getMessageType().getValue())
                        .content(m.getContent()))
            .toList();

    var opts = getChatOptions(prompt);
    var orchestrationPrompt = new OrchestrationPrompt(messages, opts.getTemplateParameters());

    opts.getLlmConfig().forEach(orchestrationPrompt::withLlmConfig);
    opts.getTemplate().forEach(orchestrationPrompt::withTemplate);
    opts.getMaskingConfig().forEach(orchestrationPrompt::withMaskingConfig);
    opts.getInputContentFilter().forEach(orchestrationPrompt::withInputContentFilter);
    opts.getOutputContentFilter().forEach(orchestrationPrompt::withOutputContentFilter);

    return orchestrationPrompt;
  }

  @Nonnull
  private static OrchestrationChatOptions getChatOptions(@Nonnull final Prompt prompt) {
    if (prompt.getOptions() == null) {
      return new OrchestrationChatOptions();
    }
    if (prompt.getOptions() instanceof OrchestrationChatOptions opts) {
      return opts;
    }
    // TODO: Should be build the LLM config out of the provided options instead?
    log.warn(
        "Prompt options are not of type {}. Ignoring provided options.",
        OrchestrationChatOptions.class.getSimpleName());
    return new OrchestrationChatOptions();
  }
}
