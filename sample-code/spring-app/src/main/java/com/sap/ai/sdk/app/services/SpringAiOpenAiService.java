package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiSpringEmbeddingModel;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.model.tool.DefaultToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Service class for Spring AI integration with OpenAI */
@Service
public class SpringAiOpenAiService {

  private final OpenAiSpringEmbeddingModel embeddingClient =
      new OpenAiSpringEmbeddingModel(OpenAiClient.forModel(OpenAiModel.TEXT_EMBEDDING_3_SMALL));
  private final ChatModel chatClient =
      new OpenAiChatModel(OpenAiClient.forModel(OpenAiModel.GPT_5_MINI));

  /**
   * Embeds a list of strings using the OpenAI embedding model.
   *
   * @return an {@code EmbeddingResponse} containing the embeddings and metadata
   */
  @Nonnull
  public EmbeddingResponse embedStrings() {
    final var options = EmbeddingOptions.builder().dimensions(128).build();
    final var springAiRequest =
        new EmbeddingRequest(List.of("The quick brown fox jumps over the lazy dog."), options);

    return embeddingClient.call(springAiRequest);
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

  /**
   * Chat request to OpenAI through the OpenAI service with a simple prompt.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse completion() {
    val prompt = new Prompt("What is the capital of France?");
    return chatClient.call(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public Flux<ChatResponse> streamChatCompletion() {
    val prompt = new Prompt("Can you give me the first 100 numbers of the Fibonacci sequence?");
    return chatClient.stream(prompt);
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
    val options = new DefaultToolCallingChatOptions();
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
  public ChatResponse chatMemory() {
    val repository = new InMemoryChatMemoryRepository();
    val memory = MessageWindowChatMemory.builder().chatMemoryRepository(repository).build();
    val advisor = MessageChatMemoryAdvisor.builder(memory).build();
    val cl = ChatClient.builder(chatClient).defaultAdvisors(advisor).build();
    val prompt1 = new Prompt("What is the capital of France?");
    val prompt2 = new Prompt("And what is the typical food there?");

    cl.prompt(prompt1).call().content();
    return Objects.requireNonNull(
        cl.prompt(prompt2).call().chatResponse(), "Chat response is null");
  }
}
