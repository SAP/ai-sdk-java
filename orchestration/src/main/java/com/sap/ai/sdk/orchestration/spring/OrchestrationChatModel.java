package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.toCompletionPostRequest;
import static com.sap.ai.sdk.orchestration.model.MessageToolCall.TypeEnum.FUNCTION;

import com.sap.ai.sdk.orchestration.AssistantMessage;
import com.sap.ai.sdk.orchestration.OrchestrationChatCompletionDelta;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.ToolMessage;
import com.sap.ai.sdk.orchestration.UserMessage;
import com.sap.ai.sdk.orchestration.model.MessageToolCall;
import com.sap.ai.sdk.orchestration.model.MessageToolCallFunction;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import reactor.core.publisher.Flux;

/**
 * Spring AI integration for the orchestration service.
 *
 * @since 1.2.0
 */
@Slf4j
public class OrchestrationChatModel implements ChatModel {
  @Nonnull private final OrchestrationClient client;

  @Nonnull
  private final DefaultToolCallingManager toolCallingManager =
      DefaultToolCallingManager.builder().build();

  /**
   * Default constructor.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel() {
    this(new OrchestrationClient());
  }

  /**
   * Constructor with a custom client.
   *
   * @param client The custom client to use.
   * @since 1.2.0
   */
  public OrchestrationChatModel(@Nonnull final OrchestrationClient client) {
    this.client = client;
  }

  @Nonnull
  @Override
  public ChatResponse call(@Nonnull final Prompt prompt) {
    if (prompt.getOptions() instanceof OrchestrationChatOptions options) {

      val orchestrationPrompt = toOrchestrationPrompt(prompt);
      val response =
          new OrchestrationSpringChatResponse(
              client.chatCompletion(orchestrationPrompt, options.getConfig()));

      if (ToolCallingChatOptions.isInternalToolExecutionEnabled(prompt.getOptions())
          && response.hasToolCalls()) {
        val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
        // Send the tool execution result back to the model.
        return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
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
    final Function<Message, List<com.sap.ai.sdk.orchestration.Message>> mapper =
        msg ->
            switch (msg.getMessageType()) {
              case SYSTEM:
                yield List.of(new SystemMessage(msg.getText()));
              case USER:
                yield List.of(new UserMessage(msg.getText()));
              case ASSISTANT:
                val assistantMessage = new AssistantMessage(msg.getText());
                val springToolCalls =
                    ((org.springframework.ai.chat.messages.AssistantMessage) msg).getToolCalls();
                if (springToolCalls != null && !springToolCalls.isEmpty()) {
                  final List<MessageToolCall> sdkToolCalls =
                      springToolCalls.stream()
                          .map(OrchestrationChatModel::toOrchestrationToolCall)
                          .toList();
                  yield List.of(assistantMessage.withToolCalls(sdkToolCalls));
                }
                yield List.of(assistantMessage);
              case TOOL:
                val toolResponses = ((ToolResponseMessage) msg).getResponses();
                yield toolResponses.stream()
                    .map(
                        r ->
                            (com.sap.ai.sdk.orchestration.Message)
                                new ToolMessage(r.id(), r.responseData()))
                    .toList();
            };
    return messages.stream()
        .map(mapper)
        .flatMap(List::stream)
        .toArray(com.sap.ai.sdk.orchestration.Message[]::new);
  }

  @Nonnull
  private static MessageToolCall toOrchestrationToolCall(@Nonnull final ToolCall toolCall) {
    return MessageToolCall.create()
        .id(toolCall.id())
        .type(FUNCTION)
        .function(
            MessageToolCallFunction.create().name(toolCall.name()).arguments(toolCall.arguments()));
  }
}
