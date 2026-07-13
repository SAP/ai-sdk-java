package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.sap.ai.sdk.core.model.SpeechOutputParamTurnDetection;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Slf4j
public class RealtimeApiTest {

  @Test
  @Timeout(value = 30, unit = TimeUnit.SECONDS)
  void testSpeechToSpeech() {
    var outputBuffer = new ByteArrayOutputStream(300000);
    var monitor = new CountDownLatch(1);
    var client = OpenAiClient.forModel(OpenAiModel.GPT_REALTIME);

    try (TextInputChannel input =
        client.textToSpeech(
            (byteChunk, last) -> {
              outputBuffer.writeBytes(byteChunk);
              if (last) {
                monitor.countDown();
              }
            },
            SpeechOutputParamTurnDetection.EACH_CALL_IS_A_TURN)) {
      input.sendText("Ordnung muss sein!");
      monitor.await();
    } catch (Exception e) {
      if (!(e instanceof InterruptedException)) {
        fail(e);
      }
      // do nothing, test has either been interrupted by user (intended) or by jupiter if timeout is
      // reached
      return;
    }
    assertThat(monitor.getCount()).isEqualTo(0);
    assertThat(outputBuffer.size()).isGreaterThan(0);
  }
}
