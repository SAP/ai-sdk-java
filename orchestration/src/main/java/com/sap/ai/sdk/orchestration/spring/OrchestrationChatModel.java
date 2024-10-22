package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

/** Spring AI integration for the orchestration service. */
@Slf4j
public class OrchestrationChatModel implements ChatModel {
  @Nonnull private final OrchestrationClient client;

  public OrchestrationChatModel(@Nonnull final OrchestrationClient client) {
    this.client = client;
  }

  @Override
  @Nonnull
  public ChatOptions getDefaultOptions() {
    return new OrchestrationChatOptions();
  }

  @Override
  public OrchestrationChatResponse call(@Nonnull final Prompt prompt) {
    var orchestrationPrompt = toOrchestrationPrompt(prompt);
    var response = client.chatCompletion(orchestrationPrompt);
    return OrchestrationChatResponse.fromOrchestrationResponse(response.originalResponseDto());
  }

  @Nonnull
  private static OrchestrationPrompt toOrchestrationPrompt(@Nonnull final Prompt prompt) {
    var messages = OrchestrationChatOptions.toOrchestrationMessages(prompt.getInstructions());

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
    // TODO: Should we build the LLM config out of the provided options instead?
    log.warn(
        "Prompt options are not of type {}. Ignoring provided options.",
        OrchestrationChatOptions.class.getSimpleName());
    return new OrchestrationChatOptions();
  }
}
