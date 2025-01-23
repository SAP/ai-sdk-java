package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.SpringAiOrchestrationService;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;

@Slf4j
public class SpringAiOrchestrationTest {

  SpringAiOrchestrationService service = new SpringAiOrchestrationService();

  @Test
  void testCompletion() {
    ChatResponse response = service.completion();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).contains("Paris");
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
          if (!delta.getResult().getOutput().getContent().isEmpty()) {
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
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }

  @Test
  void testMasking() {
    ChatResponse response = service.masking();
    assertThat(response).isNotNull();
    assertThat(response.getResult().getOutput().getContent()).isNotEmpty();
  }
}
