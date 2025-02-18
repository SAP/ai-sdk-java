package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.toCompletionPostRequest;
import static com.sap.ai.sdk.orchestration.model.ResponseMessageToolCall.TypeEnum.FUNCTION;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.AssistantMessage;
import com.sap.ai.sdk.orchestration.OrchestrationChatCompletionDelta;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.ToolMessage;
import com.sap.ai.sdk.orchestration.UserMessage;
import com.sap.ai.sdk.orchestration.model.ResponseMessageToolCall;
import com.sap.ai.sdk.orchestration.model.ResponseMessageToolCallFunction;
import io.micrometer.observation.ObservationRegistry;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.resolution.DelegatingToolCallbackResolver;
import reactor.core.publisher.Flux;

/**
 * Spring AI integration for the orchestration service.
 *
 * @since 1.2.0
 */
@Beta
@Slf4j
public class OrchestrationChatModel extends DefaultToolCallingManager implements ChatModel {
  @Nonnull private final OrchestrationClient client;

  /**
   * Default constructor.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel() {
    super(
        ObservationRegistry.NOOP,
        new DelegatingToolCallbackResolver(List.of()),
        DefaultToolExecutionExceptionProcessor.builder().build());
    this.client = new OrchestrationClient();
  }

  /**
   * Constructor with a custom client.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel(@Nonnull final OrchestrationClient client) {
    super(
        ObservationRegistry.NOOP,
        new DelegatingToolCallbackResolver(List.of()),
        DefaultToolExecutionExceptionProcessor.builder().build());
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
        val toolExecutionResult = this.executeToolCalls(prompt, response);
        if (toolExecutionResult.returnDirect()) {
          // Return tool execution result directly to the client.
          return ChatResponse.builder()
              .from(response)
              .generations(ToolExecutionResult.buildGenerations(toolExecutionResult))
              .build();
        } else {
          // Send the tool execution result back to the model.
          return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
        }
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
                val springToolCalls =
                    ((org.springframework.ai.chat.messages.AssistantMessage) msg).getToolCalls();
                if (springToolCalls != null) {
                  final List<ResponseMessageToolCall> sdkToolCalls =
                      springToolCalls.stream()
                          .map(
                              toolCall ->
                                  ResponseMessageToolCall.create()
                                      .id(toolCall.id())
                                      .type(FUNCTION)
                                      .function(
                                          ResponseMessageToolCallFunction.create()
                                              .name(toolCall.name())
                                              .arguments(toolCall.arguments())))
                          .toList();
                  yield List.of(new AssistantMessage(sdkToolCalls));
                }
                yield List.of(new AssistantMessage(msg.getText()));
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
}
