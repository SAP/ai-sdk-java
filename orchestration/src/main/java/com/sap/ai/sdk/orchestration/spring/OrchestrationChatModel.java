package com.sap.ai.sdk.orchestration.spring;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.toCompletionPostRequest;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.AssistantMessage;
import com.sap.ai.sdk.orchestration.OrchestrationChatCompletionDelta;
import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.OrchestrationPrompt;
import com.sap.ai.sdk.orchestration.SystemMessage;
import com.sap.ai.sdk.orchestration.UserMessage;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.AbstractToolCallSupport;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallback;
import reactor.core.publisher.Flux;

/**
 * Spring AI integration for the orchestration service.
 *
 * @since 1.2.0
 */
@Beta
@Slf4j
public class OrchestrationChatModel extends AbstractToolCallSupport implements ChatModel {
  @Nonnull private OrchestrationClient client;

  /**
   * Default constructor.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel() {
    super(name -> new FunctionCallback() {
      @Override
      public String getName()
      {
        return "getWeather";
      }

      @Override
      public String getDescription()
      {
        return "Get the weather in location";
      }

      @Override
      public String getInputTypeSchema()
      {
        return "location";
      }

      @Override
      public String call( String functionInput )
      {
        return "The weather is sunny in " + functionInput;
      }
    });
    this.client = new OrchestrationClient();
  }

  /**
   * Constructor with a custom client.
   *
   * @since 1.2.0
   */
  public OrchestrationChatModel(OrchestrationClient client) {
    super(null);
    this.client = client;
  }

  @Nonnull
  @Override
  public ChatResponse call(@Nonnull final Prompt prompt) {
    return internalCall(prompt, null);
  }

  public ChatResponse internalCall(Prompt prompt, ChatResponse previousChatResponse) {

    if (prompt.getOptions() instanceof OrchestrationChatOptions options) {
      val orchestrationPrompt = toOrchestrationPrompt(prompt);
      val response =
          new OrchestrationSpringChatResponse(
              client.chatCompletion(orchestrationPrompt, options.getConfig()));

      if (!isProxyToolCalls(prompt, options)
          && isToolCall(response, Set.of("tool_calls", "stop"))) {
        var toolCallConversation = handleToolCalls(prompt, response);
        // Recursively call the call method with the tool call message
        // conversation that contains the call responses.
        return internalCall(new Prompt(toolCallConversation, prompt.getOptions()), response);
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
                yield new AssistantMessage(msg.getText());
              case TOOL:
                throw new IllegalArgumentException("Tool messages are not supported");
            };
    return messages.stream().map(mapper).toArray(com.sap.ai.sdk.orchestration.Message[]::new);
  }
}
