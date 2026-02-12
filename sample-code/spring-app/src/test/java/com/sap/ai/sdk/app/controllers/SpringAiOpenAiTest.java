package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
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
    final var stream = service.streamChatCompletion().toStream();

    final var filledDeltaCount = new AtomicInteger(0);
    stream
        // foreach consumes all elements, closing the stream at the end
        .forEach(
        delta -> {
          log.info("delta: {}", delta);
          String text = delta.getResult().getOutput().getText();
          if (text != null && !text.isEmpty()) {
            filledDeltaCount.incrementAndGet();
          }
        });

    // the first two and the last delta don't have any content
    // see OpenAiChatCompletionDelta#getDeltaContent
    assertThat(filledDeltaCount.get()).isGreaterThan(0);
  }

  @Test
  void testToolCallingWithExecution() {
    ChatResponse response = service.toolCalling(true);
    assertThat(response.getResult().getOutput().getText()).contains("Potsdam", "Toulouse", "°C");
  }

  @Test
  void testToolCallingWithoutExecution() {
    ChatResponse response = service.toolCalling(false);
    List<AssistantMessage.ToolCall> toolCalls = response.getResult().getOutput().getToolCalls();
    assertThat(toolCalls).hasSize(2);
    AssistantMessage.ToolCall toolCall1 = toolCalls.get(0);
    AssistantMessage.ToolCall toolCall2 = toolCalls.get(1);
    assertThat(toolCall1.type()).isEqualTo("function");
    assertThat(toolCall2.type()).isEqualTo("function");
    assertThat(toolCall1.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall2.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall1.arguments())
        .matches("\\{\"arg0\": \\{\"location\": \"[^\"]*Potsdam[^\"]*\", \"unit\": \"C\"}}");
    assertThat(toolCall2.arguments())
        .matches("\\{\"arg0\": \\{\"location\": \"[^\"]*Toulouse[^\"]*\", \"unit\": \"C\"}}");
  }

  @Test
  void testChatMemory() {
    ChatResponse response = service.chatMemory();
    assertThat(response).isNotNull();
    String text = response.getResult().getOutput().getText();
    log.info(text);
    assertThat(text)
        .containsAnyOf(
            "French", "onion", "pastries", "cheese", "baguette", "coq au vin", "foie gras");
  }
}
