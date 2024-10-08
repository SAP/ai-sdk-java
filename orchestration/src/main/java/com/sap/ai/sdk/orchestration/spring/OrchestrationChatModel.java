package com.sap.ai.sdk.orchestration.spring;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;

import javax.annotation.Nonnull;

import static com.sap.ai.sdk.orchestration.spring.ChatResponseFactory.toChatResponse;

/** Spring AI integration for the orchestration service. */
@Slf4j
public class OrchestrationChatModel implements ChatModel {
  private final OrchestrationClient delegate;

  public OrchestrationChatModel( OrchestrationClient client) {
    delegate = client;
  }

  @Override
  @Nonnull
  public ChatOptions getDefaultOptions() {
    return new OrchestrationChatOptions();
  }

  @Override
  public ChatResponse call(@Nonnull final Prompt prompt) {
    var opts = getOrchestrationConfig(prompt);
    opts = opts.withTemplate(mergePromptIntoTemplateConfig(prompt, opts));

    var response = delegate.chatCompletion(new OrchestrationPrompt(opts, null, null));
    return toChatResponse(response);
  }

  /*
   * Currently, we have to merge the prompt into the template configuration.
   * This works around the limitation that the template config isn't optional.
   * This comes at the risk that the prompt unintentionally contains the templating pattern "{{? .. }}".
   * In this case, the request will fail, since the templating module will try to resolve the parameter.
   * To be fixed with https://github.tools.sap/AI/llm-orchestration/issues/662
   */
  private static TemplatingModuleConfig mergePromptIntoTemplateConfig(
      @Nonnull Prompt prompt, OrchestrationConfig opts) {
    // TODO test
    final var template =
        opts.getTemplate() != null
            ? opts.getTemplate()
            : TemplatingModuleConfig.create().template();
    final var messagesWithPrompt = new ArrayList<>(template.getTemplate());

    prompt.getInstructions().stream()
        .map(m -> ChatMessage.create().role(m.getMessageType().getValue()).content(m.getContent()))
        .collect(Collectors.toCollection(() -> messagesWithPrompt));

    return TemplatingModuleConfig.create()
        .template(messagesWithPrompt)
        .defaults(template.getDefaults());
  }

  private static OrchestrationConfig getOrchestrationConfig(@Nonnull final Prompt prompt) {
    if (prompt.getOptions() == null) {
      return new OrchestrationConfig();
    }
    if (prompt.getOptions() instanceof OrchestrationChatOptions orchestratedChatOptions) {
      return orchestratedChatOptions.getDelegate();
    }
    // TODO test
    log.warn(
        "Prompt options are not of type {}. Ignoring provided options.",
        OrchestrationChatOptions.class.getSimpleName());
    return new OrchestrationConfig();
  }
}
