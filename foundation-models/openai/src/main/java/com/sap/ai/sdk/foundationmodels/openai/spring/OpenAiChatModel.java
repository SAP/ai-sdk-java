package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.springframework.ai.model.tool.ToolCallingChatOptions.isInternalToolExecutionEnabled;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiFunctionCall;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiTextItem;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionResponseMessage;
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
import org.springframework.ai.model.tool.DefaultToolCallingManager;

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
    if (!(prompt.getOptions() instanceof OpenAiChatOptions options)) {
      throw new IllegalArgumentException(
          "Please add OpenAiChatOptions to the Prompt: new Prompt(\"message\", new OpenAiChatOptions(config))");
    }
    val openAiRequest = toOpenAiRequest(prompt);
    val request = new OpenAiChatCompletionRequest(openAiRequest).withTools(options.getTools());
    val result = client.chatCompletion(request);
    val response = new ChatResponse(toGenerations(result));

    if (isInternalToolExecutionEnabled(prompt.getOptions()) && response.hasToolCalls()) {
      val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
      // Send the tool execution result back to the model.
      return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
    }
    return response;
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
    if (message.getText() == null) {
      return;
    }
    if (!message.hasToolCalls()) {
      result.add(OpenAiMessage.assistant(message.getText()));
      return;
    }
    final Function<ToolCall, OpenAiToolCall> callTranslate =
        toolCall -> new OpenAiFunctionCall(toolCall.id(), toolCall.name(), toolCall.arguments());
    val content = new OpenAiMessageContent(List.of(new OpenAiTextItem(message.getText())));
    val calls = message.getToolCalls().stream().map(callTranslate).toList();
    result.add(new OpenAiAssistantMessage(content, calls));
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
