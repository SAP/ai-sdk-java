package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;

import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.Map;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

/** Service class for the Orchestration service */
@Service
public class SpringAiOrchestrationService {
  private final ChatModel client = new OrchestrationChatModel();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO);
  private final OrchestrationChatOptions defaultOptions = new OrchestrationChatOptions(config);

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the assistant response object
   */
  public ChatResponse completion() {
    val prompt = new Prompt("What is the capital of France?", defaultOptions);

    return client.call(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @return the assistant response object
   */
  public ChatResponse template() {
    val template = new PromptTemplate("{input}");
    val prompt = template.create(Map.of("input", "Hello World!"), defaultOptions);

    return client.call(prompt);
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the assistant response object
   */
  public ChatResponse masking() {
    val masking =
        DpiMasking.anonymization()
            .withEntities(DPIEntities.EMAIL, DPIEntities.ADDRESS, DPIEntities.LOCATION);

    val opts = new OrchestrationChatOptions(config.withMaskingConfig(masking));
    val prompt =
        new Prompt(
            "Please write 'Hello World!' to me via email. My email address is foo.bar@baz.ai",
            opts);

    return client.call(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using message history.
   *
   * @return the assistant response object
   */
  public ChatResponse chatMemory() {
    val memory = new InMemoryChatMemory();
    val advisor = new MessageChatMemoryAdvisor(memory);
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?", defaultOptions);
    val prompt2 = new Prompt("And what is the typical food there?", defaultOptions);

    cl.prompt(prompt1).call();
    return cl.prompt(prompt2).call().chatResponse();
  }
}
