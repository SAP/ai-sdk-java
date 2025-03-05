package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.util.List;
import org.junit.jupiter.api.Test;

class SpringAiOpenAiTest {

  private final SpringAiOpenAiService service = new SpringAiOpenAiService();

  @Test
  void testEmbedWithEmbeddingRequest() {

    var response =
        service.embedWithEmbeddingRequest(
            List.of(
                "The quick brown fox jumps over the lazy dog.",
                "To be or not to be, that is the question."));

    assertThat(response).isNotNull();
    assertThat(response.getResults()).hasSize(2);
    assertThat(response.getResults().get(0).getOutput()).hasSize(128);
    assertThat(response.getMetadata().getUsage().getPromptTokens()).isNotNull();
    assertThat(response.getMetadata().getUsage().getTotalTokens()).isNotNull();
    assertThat(response.getMetadata().getModel())
        .isEqualTo(OpenAiModel.TEXT_EMBEDDING_3_SMALL.name());
  }
}
