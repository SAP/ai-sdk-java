package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatModel;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiChatOptions;
import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiSpringEmbeddingModel;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

/** Service class for Spring AI integration with OpenAI */
@Service
public class SpringAiOpenAiService {

  private final OpenAiSpringEmbeddingModel embeddingClient =
      new OpenAiSpringEmbeddingModel(OpenAiClient.forModel(OpenAiModel.TEXT_EMBEDDING_3_SMALL));
  private final OpenAiChatModel chatClient =
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
    var prompt = new Prompt("What is the capital of France?", new OpenAiChatOptions());
    return chatClient.call(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public ChatResponse streamChatCompletion() {
    val prompt =
        new Prompt(
            "Can you give me the first 100 numbers of the Fibonacci sequence?",
            new OpenAiChatOptions());
    return chatClient.call(prompt);
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
