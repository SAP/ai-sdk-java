package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;

import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.stereotype.Service;

@Service
public class SpringAiAgenticWorkflowService {
  private final ChatModel client = new OrchestrationChatModel();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

  @Nonnull
  public ChatResponse chain(String userInput) {

    //    Configure chat memory
    val memory = new InMemoryChatMemory();
    val advisor = new MessageChatMemoryAdvisor(memory);
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();

    //    Add (mocked) tools
    val options = new OrchestrationChatOptions(config);
    options.setToolCallbacks(
        List.of(ToolCallbacks.from(new WeatherMethod(), new RestaurantMethod())));
    options.setInternalToolExecutionEnabled(true);

    //    Prompts for the chain workflow
    List<String> systemPrompts =
        List.of(
            "You are a traveling planning agent for a single day trip. Where appropriate, use the provided tools. First, start by suggesting some restaurants for the mentioned city.",
            "Now, check the whether for the city.",
            "Finally, combine the suggested itinerary from this conversation into a short, one-sentence plan for the day trip.");

    //    Perform the chain workflow
    int step = 0;
    String responseText = userInput;
    ChatResponse response = null;

    System.out.printf("\nSTEP %s:\n %s%n", step++, responseText);

    for (String systemPrompt : systemPrompts) {

      // 1. Compose the input using the response from the previous step.
      String input = String.format("{%s}\n {%s}", systemPrompt, responseText);
      val prompt = new Prompt(input, options);

      // 2. Call the chat client with the new input and get the new response.
      response =
          Objects.requireNonNull(
              cl.prompt(prompt).call().chatResponse(), "Chat response is null in step " + step);
      responseText = response.getResult().getOutput().getText();

      System.out.printf("\nSTEP %s:\n %s%n", step++, responseText);
    }

    return response;
  }
}
