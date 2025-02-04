package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.toCompletionPostRequest;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.AssistantMessage;
import com.sap.ai.sdk.orchestration.OrchestrationChatCompletionDelta;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.ToolMessage;
import com.sap.ai.sdk.orchestration.UserMessage;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.AbstractToolCallSupport;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallingOptions;
import reactor.core.publisher.Flux;

/**
 * Spring AI integration for the orchestration service.
 *
 * @since 1.2.0
 */
@Beta
@Slf4j
public class OrchestrationChatModel extends AbstractToolCallSupport implements ChatModel {
  @Nonnull private final OrchestrationClient client;

  /**
   * Default constructor.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel() {
    super(null);
    this.client = new OrchestrationClient();
  }

  /**
   * Constructor with a custom client.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel(@Nonnull final OrchestrationClient client) {
    super(null);
    this.client = client;
  }

  @Nonnull
  @Override
  public ChatResponse call(@Nonnull final Prompt prompt) {
    if (prompt.getOptions() instanceof OrchestrationChatOptions options) {

      if (options.getFunctionCallbacks() != null) {
        runtimeFunctionCallbackConfigurations(
            FunctionCallingOptions.builder()
                .functionCallbacks(options.getFunctionCallbacks())
                .build());
      }

      val orchestrationPrompt = toOrchestrationPrompt(prompt);
      val response =
          new OrchestrationSpringChatResponse(
              client.chatCompletion(orchestrationPrompt, options.getConfig()));

      if (!isProxyToolCalls(prompt, options)
          && isToolCall(response, Set.of("tool_calls", "stop"))) {
        val toolCallConversation = handleToolCalls(prompt, response);
        // Recursively call the call method with the tool call message
        // conversation that contains the call responses.
        return call(new Prompt(toolCallConversation, prompt.getOptions()));
      }
      return response;
    }
    throw new IllegalArgumentException(
        "Please add OrchestrationChatOptions to the Prompt: new Prompt(\"message\", new OrchestrationChatOptions(config))");
  }

  @Override
  @Nonnull
  public Flux<ChatResponse> stream(@Nonnull final Prompt prompt) {

    if (prompt.getOptions() instanceof OrchestrationChatOptions options) {

      val orchestrationPrompt = toOrchestrationPrompt(prompt);
      val request = toCompletionPostRequest(orchestrationPrompt, options.getConfig());
      val stream = client.streamChatCompletionDeltas(request);

      final Flux<OrchestrationChatCompletionDelta> flux =
          Flux.generate(
              stream::iterator,
              (iterator, sink) -> {
                if (iterator.hasNext()) {
                  sink.next(iterator.next());
                } else {
                  sink.complete();
                }
                return iterator;
              });
      return flux.map(OrchestrationSpringChatDelta::new);
    }
    throw new IllegalArgumentException(
        "Please add OrchestrationChatOptions to the Prompt: new Prompt(\"message\", new OrchestrationChatOptions(config))");
  }

  @Nonnull
  private OrchestrationPrompt toOrchestrationPrompt(@Nonnull final Prompt prompt) {
    val messages = toOrchestrationMessages(prompt.getInstructions());
    return new OrchestrationPrompt(Map.of(), messages);
  }

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
                final List<ToolCall> toolCalls =
                    ((org.springframework.ai.chat.messages.AssistantMessage) msg).getToolCalls();
                if (toolCalls != null) {
                  val toolCall = toolCalls.get(0);
                  yield new AssistantMessage(
                      new AssistantMessage.ToolCall(
                          toolCall.id(), toolCall.type(), toolCall.name(), toolCall.arguments()));
                }
                yield new AssistantMessage(msg.getText());
              case TOOL:
                val responses = ((ToolResponseMessage) msg).getResponses();
                val response = responses.get(0);
                yield new ToolMessage(response.id(), response.responseData());
            };
    return messages.stream().map(mapper).toArray(com.sap.ai.sdk.orchestration.Message[]::new);
  }
}
