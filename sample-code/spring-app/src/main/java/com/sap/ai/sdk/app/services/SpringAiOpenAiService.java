package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatOptions;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiSpringEmbeddingModel;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.stereotype.Service;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_4O_MINI;

/** Service class for Spring AI integration with OpenAI */
@Service
public class SpringAiOpenAiService {

  private final OpenAiSpringEmbeddingModel embeddingClient =
      new OpenAiSpringEmbeddingModel(OpenAiClient.forModel(OpenAiModel.TEXT_EMBEDDING_3_SMALL));
  private final ChatModel chatClient =
      new OpenAiChatModel(OpenAiClient.forModel(OpenAiModel.GPT_4O_MINI));

  /**
   * Embeds a list of strings using the OpenAI embedding model.
   *
   * @return an {@code EmbeddingResponse} containing the embeddings and metadata
   */
  @Nonnull
  public EmbeddingResponse embedStrings() {
    final var options = EmbeddingOptionsBuilder.builder().withDimensions(128).build();
    final var springAiRequest =
        new EmbeddingRequest(List.of("The quick brown fox jumps over the lazy dog."), options);

    return embeddingClient.call(springAiRequest);
  }

  /**
   * Chat request to OpenAI through the OpenAI service with a simple prompt.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse completion() {
    val options = new OpenAiChatOptions();
    val prompt = new Prompt("What is the capital of France?", options);
    return chatClient.call(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public ChatResponse streamChatCompletion() {
    val options = new OpenAiChatOptions();
    val prompt =
        new Prompt("Can you give me the first 100 numbers of the Fibonacci sequence?", options);
    return chatClient.call(prompt);
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
    val options = new OpenAiChatOptions();
    options.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));
    options.setInternalToolExecutionEnabled(internalToolExecutionEnabled);

    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", options);
    return chatClient.call(prompt);
  }

  /**
   * Chat request to OpenAI through the OpenAI service using chat memory.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse ChatMemory() {
    val repository = new InMemoryChatMemoryRepository();
    val memory = MessageWindowChatMemory.builder().chatMemoryRepository(repository).build();
    val advisor = MessageChatMemoryAdvisor.builder(memory).build();
    val cl = ChatClient.builder(chatClient).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?", new OpenAiChatOptions());
    val prompt2 = new Prompt("And what is the typical food there?", new OpenAiChatOptions());

    cl.prompt(prompt1).call().content();
    return Objects.requireNonNull(
            cl.prompt(prompt2).call().chatResponse(), "Chat response is null");

  }

  /**
   * Embeds the content of a document using the OpenAI embedding model.
   *
   * @return a float array representing the embedding of the document's content
   */
  @Nonnull
  public float[] embedDocument() {
    final var document = new Document("The quick brown fox jumps over the lazy dog.");
    return embeddingClient.embed(document);
  }
}
