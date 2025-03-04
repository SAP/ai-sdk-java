package com.sap.ai.sdk.foundationmodels.openai.spring;

import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreate200Response;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.EmbeddingsCreateRequestInput;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

@NoArgsConstructor
public class OpenAiSpringEmbeddingModel implements EmbeddingModel {

  @Override
  public EmbeddingResponse call(@Nonnull final EmbeddingRequest request) {
    var modelName =
        Objects.requireNonNull(
            request.getOptions().getModel(), "Model name is required if client is not set");
    var client = OpenAiClient.forModel(new OpenAiModel(modelName, null));

    var openAiRequest = createEmbeddingCreateRequest(request);
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

  private EmbeddingsCreateRequest createEmbeddingCreateRequest(
      @Nonnull final EmbeddingRequest request) {
    return new EmbeddingsCreateRequest()
        .dimensions(request.getOptions().getDimensions())
        .input(EmbeddingsCreateRequestInput.create(request.getInstructions()));
  }

  private EmbeddingResponse createSpringAiEmbeddingResponse(EmbeddingsCreate200Response response) {
    var embeddings =
        IntStream.range(0, response.getData().size())
            .mapToObj(i -> new Embedding(response.getData().get(i).getEmbedding(), i))
            .toList();

    var openAiUsage = response.getUsage();
    var usage = new DefaultUsage(openAiUsage.getPromptTokens(), null, openAiUsage.getTotalTokens());
    var metadata = new EmbeddingResponseMetadata(response.getModel(), usage);

    return new EmbeddingResponse(embeddings, metadata);
  }
}
