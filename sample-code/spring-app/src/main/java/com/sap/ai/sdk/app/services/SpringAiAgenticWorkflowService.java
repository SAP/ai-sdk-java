package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatOptions;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.stereotype.Service;

/** Service class for the AgenticWorkflow service */
@Service
@Slf4j
public class SpringAiAgenticWorkflowService {
  private final ChatModel client =
      new OpenAiChatModel(OpenAiClient.forModel(OpenAiModel.GPT_4O_MINI));

  /**
   * Simple agentic workflow using chain-like structure. The agent is generating a travel itinerary
   * for a given city.
   *
   * @param userInput the user input including the target city
   * @return a short travel itinerary
   */
  @Nonnull
  public ChatResponse runAgent(@Nonnull final String userInput) {

    //    Configure chat memory
    val repository = new InMemoryChatMemoryRepository();
    val memory = MessageWindowChatMemory.builder().chatMemoryRepository(repository).build();
    val advisor = MessageChatMemoryAdvisor.builder(memory).build();
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();

    //    Add (mocked) tools
    val options = new OpenAiChatOptions();
    options.setToolCallbacks(
        List.of(ToolCallbacks.from(new WeatherMethod(), new RestaurantMethod())));
    options.setInternalToolExecutionEnabled(true);

    //    Prompts for the chain workflow
    final List<String> systemPrompts =
        List.of(
            "You are a traveling planning agent for a single day trip. Where appropriate, use the provided tools. First, start by suggesting some restaurants for the mentioned city.",
            "Now, check the whether for the city.",
            "Finally, combine the suggested itinerary from this conversation into a short, one-sentence plan for the day trip.");

    //    Perform the chain workflow
    ChatResponse response = null;

    for (final String systemPrompt : systemPrompts) {

      // Combine the pre-defined prompt with the previous answer to get the new input
      val input = String.format("{%s}\n {%s}", systemPrompt, userInput);
      val prompt = new Prompt(input, options);

      // Make a call to the LLM with the new input
      response =
          Objects.requireNonNull(cl.prompt(prompt).call().chatResponse(), "Chat response is null.");
    }

    return response;
  }
}
