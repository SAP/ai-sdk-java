package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;

import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Service class for the Orchestration service */
@Service
public class SpringAiOrchestrationService {
  private final ChatModel client = new OrchestrationChatModel();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
  private final OrchestrationChatOptions defaultOptions = new OrchestrationChatOptions(config);

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse completion() {
    val prompt = new Prompt("What is the capital of France?", defaultOptions);

    return client.call(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public Flux<ChatResponse> streamChatCompletion() {
    val prompt =
        new Prompt(
            "Can you give me the first 100 numbers of the Fibonacci sequence?", defaultOptions);
    return client.stream(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse template() {
    val template = new PromptTemplate("{input}");
    val prompt = template.create(Map.of("input", "What is the capital of France?"), defaultOptions);

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
  @Nonnull
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
   * Turn a method into a tool by annotating it with @Tool. <a
   * href="https://docs.spring.io/spring-ai/reference/api/tools.html#_methods_as_tools">Spring AI
   * Tool Method Declarative Specification</a>
   *
   * @param internalToolExecutionEnabled whether the internal tool execution is enabled
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse toolCalling(final boolean internalToolExecutionEnabled) {
    final OrchestrationChatOptions options = new OrchestrationChatOptions(config);
    options.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));
    options.setInternalToolExecutionEnabled(internalToolExecutionEnabled);

    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", options);
    return client.call(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using chat memory.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse chatMemory() {
    val memory = new InMemoryChatMemory();
    val advisor = new MessageChatMemoryAdvisor(memory);
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?", defaultOptions);
    val prompt2 = new Prompt("And what is the typical food there?", defaultOptions);

    cl.prompt(prompt1).call().content();
    return Objects.requireNonNull(
        cl.prompt(prompt2).call().chatResponse(), "Chat response is null");
  }
}
