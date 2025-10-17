package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GEMINI_2_5_FLASH;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O;
import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.orchestration.AzureContentFilter;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.OrchestrationEmbeddingModel;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringEmbeddingModel;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Service class for the Orchestration service */
@Service
public class SpringAiOrchestrationService {
  private final ChatModel client = new OrchestrationChatModel();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
  private final OrchestrationChatOptions defaultOptions = new OrchestrationChatOptions(config);

  @Nullable
  @Autowired(required = false)
  ToolCallbackProvider toolCallbackProvider;

  @Value("${spring.profiles.active:}")
  private String activeProfile;

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
   * Apply input filtering for a request to orchestration using the SpringAI integration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
   *     AI Core: Orchestration - Input Filtering</a>
   * @param policy the explicitness of content that should be allowed through the filter
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse inputFiltering(@Nonnull final AzureFilterThreshold policy)
      throws OrchestrationClientException {
    val filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);
    val opts =
        new OrchestrationChatOptions(
            config.withLlmConfig(GEMINI_2_5_FLASH).withInputFiltering(filterConfig));

    val prompt =
        new Prompt(
            "Please rephrase the following sentence for me: 'We shall spill blood tonight', said the operator in-charge.",
            opts);

    return client.call(prompt);
  }

  /**
   * Apply output filtering for a request to orchestration using the SpringAI integration.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
   *     AI Core: Orchestration - Output Filtering</a>
   * @param policy the explicitness of content that should be allowed through the filter
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse outputFiltering(@Nonnull final AzureFilterThreshold policy) {
    val filterConfig =
        new AzureContentFilter().hate(policy).selfHarm(policy).sexual(policy).violence(policy);
    val opts =
        new OrchestrationChatOptions(
            config.withLlmConfig(GEMINI_2_5_FLASH).withOutputFiltering(filterConfig));

    val prompt =
        new Prompt(
            "Please rephrase the following sentence for me: 'We shall spill blood tonight', said the operator in-charge.",
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
    val options = new OrchestrationChatOptions(config);
    options.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));
    options.setInternalToolExecutionEnabled(internalToolExecutionEnabled);

    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", options);
    return client.call(prompt);
  }

  /**
   * Example using an MCP client to use a file system tool. Enabled via dedicated Spring profile,
   * since it requires an actual MCP server to run.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse toolCallingMcp() {
    // check if spring profile is set to 'mcp'
    if (!activeProfile.equals("mcp")) {
      throw new IllegalStateException(
          "The 'mcp' Spring profile is not active. Set it, e.g. by passing a JVM parameter '-Dspring.profiles.active=mcp'.");
    }
    if (toolCallbackProvider == null) {
      throw new IllegalStateException(
          "No MCP clients were found. Ensure that you configured the clients correctly in the application.yaml file.");
    }
    // GPT-4o-mini doesn't work too well with the file system tool, so we use 4o here
    val options = new OrchestrationChatOptions(config.withLlmConfig(GPT_4O));
    options.setToolCallbacks(List.of(toolCallbackProvider.getToolCallbacks()));

    val sys =
        new SystemMessage(
            """
            Please read through the markdown files in my file system.
            Ensure to first query the allowed directories.
            Then use any `.md` files you find to answer the user's question.
            Do **NOT** query for `*.md` since that doesn't work, ensure to query for `.md` instead.""");
    val usr = new UserMessage("How can I use Spring AI with the SAP AI SDK?");

    val prompt = new Prompt(List.of(sys, usr), options);
    return client.call(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service using chat memory.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse chatMemory() {
    val repository = new InMemoryChatMemoryRepository();
    val memory = MessageWindowChatMemory.builder().chatMemoryRepository(repository).build();
    val advisor = MessageChatMemoryAdvisor.builder(memory).build();
    val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?", defaultOptions);
    val prompt2 = new Prompt("And what is the typical food there?", defaultOptions);

    cl.prompt(prompt1).call().content();
    return Objects.requireNonNull(
        cl.prompt(prompt2).call().chatResponse(), "Chat response is null");
  }

  /**
   * A simple record to demonstrate the response format feature of the orchestration service.
   *
   * @param translation the translated text
   * @param language the language of the translation
   */
  public record Translation(
      @JsonProperty(required = true) String translation,
      @JsonProperty(required = true) String language) {}

  /**
   * Perform a chat completion where the LLM response adheres to a JSON schema. Use a Java record or
   * class to define the expected format and automatically parse the response into it.
   *
   * <p>In this case, we expect a {@code Translation} of the input text into Dutch.
   *
   * @return The translated text.
   */
  @Nullable
  public Translation responseFormat() {
    val cl = ChatClient.builder(new OrchestrationChatModel()).build();

    val prompt =
        new Prompt(
            "How do I say 'AI is going to revolutionize the world' in dutch?", defaultOptions);
    return cl.prompt(prompt).call().entity(Translation.class);
  }

  /**
   * Create an embedding for a given text using the Orchestration service.
   *
   * @param inputText the text to embed
   * @return the embedding as a float array
   */
  @Nonnull
  public float[] embed(@Nonnull final String inputText) {
    val embedOptions =
        EmbeddingOptionsBuilder.builder()
            .withModel(OrchestrationEmbeddingModel.TEXT_EMBEDDING_3_SMALL.name())
            .build();
    return new OrchestrationSpringEmbeddingModel(embedOptions).embed(inputText);
  }
}
