package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiEmbeddingResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

public class OpenAiSpringEmbeddingModel implements EmbeddingModel {

  @Override
  public EmbeddingResponse call(@Nonnull final EmbeddingRequest request) {

    var modelName = request.getOptions().getModel();
    var client = OpenAiClient.forModel(new OpenAiModel(modelName, null));
    var openAiRequest = createOpenAiEmbeddingRequest(request);
    var openAiResponse = client.embedding(openAiRequest);

    return createSpringAiEmbeddingResponse(openAiResponse);
  }

  @Override
  public float[] embed(@Nonnull final Document document) {
    if (document.isText()) {
      return embed(document.getText());
    }
    throw new UnsupportedOperationException(
        "Only text type document supported. Metadata contains " + document.getMetadata());
  }

  private OpenAiEmbeddingRequest createOpenAiEmbeddingRequest(
      @Nonnull final EmbeddingRequest request) {
    return new OpenAiEmbeddingRequest(request.getInstructions())
        .withDimensions(request.getOptions().getDimensions());
  }

  private EmbeddingResponse createSpringAiEmbeddingResponse(OpenAiEmbeddingResponse response) {
    var vectors = response.getEmbeddingVectors();
    var embeddings =
        IntStream.range(0, vectors.size()).mapToObj(i -> new Embedding(vectors.get(i), i)).toList();

    var openAiUsage = response.getOriginalResponse().getUsage();
    var usage = new DefaultUsage(openAiUsage.getPromptTokens(), null, openAiUsage.getTotalTokens());
    var metadata = new EmbeddingResponseMetadata(response.getOriginalResponse().getModel(), usage);

    return new EmbeddingResponse(embeddings, metadata);
  }
}
