package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;

class SpringAiOpenAiTest {

  private final SpringAiOpenAiService service = new SpringAiOpenAiService();
  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(SpringAiOrchestrationTest.class);

  @Test
  void testEmbedStrings() {

    var response = service.embedStrings();

    assertThat(response).isNotNull();
    assertThat(response.getResults()).hasSize(1);
    assertThat(response.getResults().get(0).getOutput()).hasSize(128);
    assertThat(response.getMetadata().getUsage().getPromptTokens()).isNotNull();
    assertThat(response.getMetadata().getUsage().getTotalTokens()).isNotNull();
    assertThat(response.getMetadata().getModel())
        .isEqualTo(OpenAiModel.TEXT_EMBEDDING_3_SMALL.name());
  }

  @Test
  void testCompletion() {
    ChatResponse response = service.completion();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getText()).contains("Paris");
  }

  @Test
  void testStreamChatCompletion() {
    ChatResponse response = service.streamChatCompletion();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getText()).isNotEmpty();
  }

  @Test
  void testToolCallingWithExecution() {
    ChatResponse response = service.toolCalling(true);
    assertThat(response.getResult().getOutput().getText()).contains("Potsdam", "Toulouse", "°C");
  }

  @Test
  void testChatMemory() {
    ChatResponse response = service.ChatMemory();
    assertThat(response).isNotNull();
    String text = response.getResult().getOutput().getText();
    log.info(text);
    assertThat(text)
        .containsAnyOf(
            "French", "onion", "pastries", "cheese", "baguette", "coq au vin", "foie gras");
  }
}
