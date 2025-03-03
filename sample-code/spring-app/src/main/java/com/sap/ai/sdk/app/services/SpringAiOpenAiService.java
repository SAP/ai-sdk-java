package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.foundationmodels.openai.spring.OpenAiSpringEmbeddingModel;
import java.util.List;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

@Service
public class SpringAiOpenAiService {
  public EmbeddingResponse embeddingWithStrings(String modelName) {

    var options =
        EmbeddingOptionsBuilder.builder().withModel(modelName).withDimensions(128).build();

    var springAiRequest =
        new EmbeddingRequest(
            List.of(
                "The quick brown fox jumps over the lazy dog.",
                "To be or not to be, that is the question."),
            options);
    return new OpenAiSpringEmbeddingModel().call(springAiRequest);
  }
}
