package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOpenAiService;
import org.junit.jupiter.api.Test;

class SpringAiOpenAiTest {

  private SpringAiOpenAiService service = new SpringAiOpenAiService();

  @Test
  void testEmbeddingWithStrings() {

    var response = service.embeddingWithStrings("text-embedding-3-small");

    assertThat(response).isNotNull();
    assertThat(response.getResults()).hasSize(2);
  }
}
