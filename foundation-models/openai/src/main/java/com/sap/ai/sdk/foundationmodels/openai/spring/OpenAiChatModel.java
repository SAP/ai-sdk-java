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
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionResponseMessage;
import io.vavr.control.Option;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
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

    val request =
        new OpenAiChatCompletionRequest(toOpenAiRequest(prompt)).withTools(options.getTools());
    val response = new ChatResponse(toGenerations(client.chatCompletion(request)));

    if (isInternalToolExecutionEnabled(prompt.getOptions()) && response.hasToolCalls()) {
      val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
      // Send the tool execution result back to the model.
      return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
    }
    return response;
  }

  private List<OpenAiMessage> toOpenAiRequest(final Prompt prompt) {
    return prompt.getInstructions().stream()
        .flatMap(
            message ->
                switch (message.getMessageType()) {
                  case USER ->
                      Stream.of(
                          OpenAiMessage.user(
                              Option.of(message.getText()).getOrElse(message.getText())));
                  case ASSISTANT -> {
                    val assistantMessage = (AssistantMessage) message;
                    yield Stream.of(
                        assistantMessage.hasToolCalls()
                            ? new OpenAiAssistantMessage(
                                new OpenAiMessageContent(
                                    List.of(
                                        new OpenAiTextItem(
                                            Option.of(message.getText())
                                                .getOrElse(message.getText())))),
                                assistantMessage.getToolCalls().stream()
                                    .map(
                                        toolCall ->
                                            (OpenAiToolCall)
                                                new OpenAiFunctionCall(
                                                    toolCall.id(),
                                                    toolCall.name(),
                                                    toolCall.arguments()))
                                    .toList())
                            : new OpenAiAssistantMessage(
                                Option.of(message.getText()).getOrElse(message.getText())));
                  }
                  case SYSTEM -> Stream.of(OpenAiMessage.system(message.getText()));
                  case TOOL -> {
                    val responses = ((ToolResponseMessage) message).getResponses();
                    yield responses.stream()
                        .map(resp -> OpenAiMessage.tool(resp.responseData(), resp.id()));
                  }
                })
        .toList();
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
