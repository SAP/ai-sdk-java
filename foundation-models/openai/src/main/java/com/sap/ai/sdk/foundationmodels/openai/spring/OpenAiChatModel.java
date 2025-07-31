package com.sap.ai.sdk.foundationmodels.openai.spring;

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
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage.ToolResponse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

@RequiredArgsConstructor
public class OpenAiChatModel implements ChatModel {

  private final OpenAiClient client;

  @Nonnull
  private final DefaultToolCallingManager toolCallingManager =
      DefaultToolCallingManager.builder().build();

  @Override
  public ChatResponse call(Prompt prompt) {
    System.out.println(prompt.getOptions() instanceof OpenAiChatOptions options);
    if (prompt.getOptions() instanceof OpenAiChatOptions options) {
      System.out.println("entered the if statement in call method of OpenAiChatModel");

      var request =
          new OpenAiChatCompletionRequest(toOpenAiRequest(prompt)).withTools(options.getTools());
      val response = new ChatResponse(toGenerations(client.chatCompletion(request)));

      if (ToolCallingChatOptions.isInternalToolExecutionEnabled(prompt.getOptions())
          && response.hasToolCalls()) {
        val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
        // Send the tool execution result back to the model.
        return call(new Prompt(toolExecutionResult.conversationHistory(), prompt.getOptions()));
      }
      System.out.println("this is the response in call method of OpenAiChatModel "+ response);
      return response;
    }
    System.out.println("did not enter at all straight to the exception");
    throw new IllegalArgumentException(
        "Please add OpenAiChatOptions to the Prompt: new Prompt(\"message\", new OpenAiChatOptions(config))");
  }

  private List<OpenAiMessage> toOpenAiRequest(Prompt prompt) {
    return prompt.getInstructions().stream()
        .map(
            message ->
                switch (message.getMessageType()) {
                  case USER -> OpenAiMessage.user(message.getText());
                  case ASSISTANT -> {
                    AssistantMessage assistantMessage = (AssistantMessage) message;
                    yield assistantMessage.hasToolCalls()
                        ? new OpenAiAssistantMessage(
                            new OpenAiMessageContent(
                                List.of(new OpenAiTextItem(message.getText()))),
                            assistantMessage.getToolCalls().stream()
                                .map(
                                    toolCall ->
                                        (OpenAiToolCall)
                                            new OpenAiFunctionCall(
                                                toolCall.id(),
                                                toolCall.name(),
                                                toolCall.arguments()))
                                .toList())
                        : new OpenAiAssistantMessage(message.getText());
                  }
                  case SYSTEM -> OpenAiMessage.system(message.getText());
                  case TOOL -> {
                    ToolResponse first = ((ToolResponseMessage) message).getResponses().get(0);
                    yield OpenAiMessage.tool(first.responseData(), first.id());
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
    val toolCalls =
        choice.getToolCalls().stream()
            .map(
                toolCall ->
                    new ToolCall(
                        toolCall.getId(),
                        toolCall.getType().getValue(),
                        toolCall.getFunction().getName(),
                        toolCall.getFunction().getArguments()))
            .toList();
    AssistantMessage message = new AssistantMessage(choice.getContent(), Map.of(), toolCalls);
    return new Generation(message);
  }
}
