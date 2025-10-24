package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import com.sap.ai.sdk.orchestration.AzureFilterThreshold;
import com.sap.ai.sdk.orchestration.OrchestrationClientException;
import com.sap.ai.sdk.orchestration.spring.OrchestrationSpringChatResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.model.ChatResponse;

@Slf4j
class SpringAiOrchestrationTest {

  SpringAiOrchestrationService service = new SpringAiOrchestrationService();

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
          if (!delta.getResult().getOutput().getText().isEmpty()) {
            filledDeltaCount.incrementAndGet();
          }
        });

    // the first two and the last delta don't have any content
    // see OpenAiChatCompletionDelta#getDeltaContent
    assertThat(filledDeltaCount.get()).isGreaterThan(0);
  }

  @Test
  void testTemplate() {
    ChatResponse response = service.template();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getText()).isNotEmpty();
  }

  @Test
  void testMasking() {
    ChatResponse response = service.masking();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getText()).isNotEmpty();
  }

  @Test
  void testInputFilteringStrict() {
    var policy = AzureFilterThreshold.ALLOW_SAFE;

    assertThatThrownBy(() -> service.inputFiltering(policy))
        .isInstanceOf(OrchestrationClientException.class)
        .hasMessageContaining(
            "Prompt filtered due to safety violations. Please modify the prompt and try again.")
        .hasMessageContaining("400 (Bad Request)");
  }

  @Test
  void testInputFilteringLenient() {
    var policy = AzureFilterThreshold.ALLOW_ALL;

    var response = service.inputFiltering(policy);

    assertThat(response.getResult().getMetadata().getFinishReason()).isEqualTo("stop");
    assertThat(response.getResult().getOutput().getText()).isNotEmpty();

    var filterResult =
        ((OrchestrationSpringChatResponse) response)
            .getOrchestrationResponse()
            .getOriginalResponse()
            .getIntermediateResults()
            .getInputFiltering();
    assertThat(filterResult.getMessage()).contains("skipped");
  }

  @Test
  void testOutputFilteringStrict() {
    var policy = AzureFilterThreshold.ALLOW_SAFE;

    var response = service.outputFiltering(policy);

    assertThat(response.getResult().getMetadata().getFinishReason()).isEqualTo("content_filter");
    assertThat(response.getResult().getOutput().getText()).isEmpty();

    var filterResult =
        ((OrchestrationSpringChatResponse) response)
            .getOrchestrationResponse()
            .getOriginalResponse()
            .getIntermediateResults()
            .getOutputFiltering();
    assertThat(filterResult.getMessage())
        .contains("Choice 0: LLM response filtered due to safety violations");
  }

  @Test
  void testOutputFilteringLenient() {
    var policy = AzureFilterThreshold.ALLOW_ALL;

    var response = service.outputFiltering(policy);

    assertThat(response.getResult().getMetadata().getFinishReason()).isEqualTo("stop");
    assertThat(response.getResult().getOutput().getText()).isNotEmpty();

    var filterResult =
        ((OrchestrationSpringChatResponse) response)
            .getOrchestrationResponse()
            .getOriginalResponse()
            .getIntermediateResults()
            .getOutputFiltering();
    assertThat(filterResult.getMessage()).contains("Choice 0: Output Filter was skipped");
  }

  @Test
  void testToolCallingWithoutExecution() {
    ChatResponse response = service.toolCalling(false);
    List<ToolCall> toolCalls = response.getResult().getOutput().getToolCalls();
    assertThat(toolCalls).hasSize(2);
    ToolCall toolCall1 = toolCalls.get(0);
    ToolCall toolCall2 = toolCalls.get(1);
    assertThat(toolCall1.type()).isEqualTo("function");
    assertThat(toolCall2.type()).isEqualTo("function");
    assertThat(toolCall1.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall2.name()).isEqualTo("getCurrentWeather");
    assertThat(toolCall1.arguments())
        .isEqualTo("{\"arg0\": {\"location\": \"Potsdam\", \"unit\": \"C\"}}");
    assertThat(toolCall2.arguments())
        .isEqualTo("{\"arg0\": {\"location\": \"Toulouse\", \"unit\": \"C\"}}");
  }

  @Test
  void testToolCallingWithExecution() {
    ChatResponse response = service.toolCalling(true);
    assertThat(response.getResult().getOutput().getText()).contains("Potsdam", "Toulouse", "°C");
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

  @Test
  void testResponseFormat() {
    var translation = service.responseFormat();
    assertThat(translation).isNotNull();
    assertThat(translation.translation()).isNotEmpty();
    assertThat(translation.language()).containsIgnoringCase("dutch");
  }

  @Test
  void testEmbedding() {
    final var embeddings = service.embed("Hello World!");
    assertThat(embeddings).isInstanceOf(float[].class);
    assertThat(embeddings.length).isGreaterThan(0);
  }
}
