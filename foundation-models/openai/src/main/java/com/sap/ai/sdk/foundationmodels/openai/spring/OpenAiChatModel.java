package com.sap.ai.sdk.foundationmodels.openai.spring;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage.tool;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
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
    System.out.println("I entered OpenAiChatModel.call() with tools: " + options.getTools());
    val openAiRequest = toOpenAiRequest(prompt);
    val request = new OpenAiChatCompletionRequest(openAiRequest).withTools(options.getTools());
    val result = client.chatCompletion(request);
    val response = new ChatResponse(toGenerations(result));

    System.out.println("I entered OpenAiChatModel.call() with response: " + response);

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
      // if(((message.getMessageType() == MessageType.USER || message.getMessageType()
      // ==MessageType.ASSISTANT || message.getMessageType() ==MessageType.SYSTEM ) &&
      // message.getText() != null) || (message.getMessageType() == MessageType.TOOL)) {
      switch (message.getMessageType()) {
        case USER -> result.add(OpenAiMessage.user(message.getText()));
        case ASSISTANT -> result.add(toAssistantMessage((AssistantMessage) message));
        case SYSTEM -> result.add(OpenAiMessage.system(message.getText()));
        case TOOL -> result.addAll(toToolMessages((ToolResponseMessage) message));
      }
      // }
    }
    return result;
  }

  private static OpenAiAssistantMessage toAssistantMessage(AssistantMessage message) {
    if (!message.hasToolCalls()) {
      return OpenAiMessage.assistant(message.getText());
    }
    final Function<ToolCall, OpenAiToolCall> callTranslate =
        toolCall -> new OpenAiFunctionCall(toolCall.id(), toolCall.name(), toolCall.arguments());
    val content = new OpenAiMessageContent(List.of(new OpenAiTextItem(message.getText())));
    val calls = message.getToolCalls().stream().map(callTranslate).toList();
    return new OpenAiAssistantMessage(content, calls);
  }

  private static List<? extends OpenAiMessage> toToolMessages(ToolResponseMessage message) {
    return message.getResponses().stream().map(r -> tool(r.responseData(), r.id())).toList();
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
