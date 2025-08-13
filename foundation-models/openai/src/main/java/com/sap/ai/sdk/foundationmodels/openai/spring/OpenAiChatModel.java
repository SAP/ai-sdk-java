package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.springframework.ai.model.tool.ToolCallingChatOptions.isInternalToolExecutionEnabled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionResponseMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingChatOptions;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import reactor.core.publisher.Flux;

/**
 * OpenAI Chat Model implementation that interacts with the OpenAI API to generate chat completions.
 */
@RequiredArgsConstructor
public class OpenAiChatModel implements ChatModel {

  private final OpenAiClient client;

  @Nonnull
  private final DefaultToolCallingManager toolCallingManager =
      DefaultToolCallingManager.builder().build();

  @Override
  @Nonnull
  public ChatResponse call(@Nonnull final Prompt prompt) {
    val openAiRequest = toOpenAiRequest(prompt);
    var request = new OpenAiChatCompletionRequest(openAiRequest);

    if ((prompt.getOptions() instanceof DefaultToolCallingChatOptions options)) {
      request = request.withTools(extractTools(options));
    }

    val result = client.chatCompletion(request);
    val response = new ChatResponse(toGenerations(result));

    if (prompt.getOptions() != null
        && isInternalToolExecutionEnabled(prompt.getOptions())
        && response.hasToolCalls()) {
      val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
      // Send the tool execution result back to the model.
      return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
    }
    return response;
  }

  @Override
  @Nonnull
  public Flux<ChatResponse> stream(@Nonnull final Prompt prompt) {
    val openAiRequest = toOpenAiRequest(prompt);
    var request = new OpenAiChatCompletionRequest(openAiRequest);
    if ((prompt.getOptions() instanceof DefaultToolCallingChatOptions options)) {
      request = request.withTools(extractTools(options));
    }
    val stream = client.streamChatCompletionDeltas(request);
    final Flux<OpenAiChatCompletionDelta> flux =
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
    return flux.map(OpenAiChatModel::toChatResponse);
  }

  private List<ChatCompletionTool> extractTools(final DefaultToolCallingChatOptions options) {
    val tools = new ArrayList<ChatCompletionTool>();
    for (val toolCallback : options.getToolCallbacks()) {
      val toolDefinition = toolCallback.getToolDefinition();
      try {
        final Map<String, Object> params =
            new ObjectMapper().readValue(toolDefinition.inputSchema(), new TypeReference<>() {});
        val tool =
            new ChatCompletionTool()
                .type(ChatCompletionTool.TypeEnum.FUNCTION)
                .function(
                    new FunctionObject()
                        .name(toolDefinition.name())
                        .description(toolDefinition.description())
                        .parameters(params));
        tools.add(tool);
      } catch (JsonProcessingException ignored) {
      }
    }
    return tools;
  }

  private static ChatResponse toChatResponse(final OpenAiChatCompletionDelta delta) {
    val assistantMessage = new AssistantMessage(delta.getDeltaContent(), Map.of());
    return new ChatResponse(List.of(new Generation(assistantMessage)));
  }

  private List<OpenAiMessage> toOpenAiRequest(final Prompt prompt) {
    final List<OpenAiMessage> result = new ArrayList<>();
    for (final Message message : prompt.getInstructions()) {
      switch (message.getMessageType()) {
        case USER -> Option.of(message.getText()).peek(t -> result.add(OpenAiMessage.user(t)));
        case SYSTEM -> Option.of(message.getText()).peek(t -> result.add(OpenAiMessage.system(t)));
        case ASSISTANT -> addAssistantMessage(result, (AssistantMessage) message);
        case TOOL -> addToolMessages(result, (ToolResponseMessage) message);
      }
    }
    return result;
  }

  private static void addAssistantMessage(
      final List<OpenAiMessage> result, final AssistantMessage message) {
    if (message.getText() != null) {
      result.add(OpenAiMessage.assistant(message.getText()));
      return;
    }
    final Function<ToolCall, OpenAiToolCall> callTranslate =
        toolCall -> OpenAiToolCall.function(toolCall.id(), toolCall.name(), toolCall.arguments());
    val calls = message.getToolCalls().stream().map(callTranslate).toList();
    result.add(OpenAiMessage.assistant(calls));
  }

  private static void addToolMessages(
      final List<OpenAiMessage> result, final ToolResponseMessage message) {
    for (final ToolResponseMessage.ToolResponse response : message.getResponses()) {
      result.add(OpenAiMessage.tool(response.responseData(), response.id()));
    }
  }

  @Nonnull
  static List<Generation> toGenerations(@Nonnull final OpenAiChatCompletionResponse result) {
    return result.getOriginalResponse().getChoices().stream()
        .map(message -> toGeneration(message.getMessage()))
        .toList();
  }

  @Nonnull
  static Generation toGeneration(@Nonnull final ChatCompletionResponseMessage choice) {
    // no metadata for now
    val calls = new ArrayList<ToolCall>();
    for (final ChatCompletionMessageToolCall c : choice.getToolCalls()) {
      val fnc = c.getFunction();
      calls.add(new ToolCall(c.getId(), c.getType().getValue(), fnc.getName(), fnc.getArguments()));
    }
    val message = new AssistantMessage(choice.getContent(), Map.of(), calls);
    return new Generation(message);
  }
}
