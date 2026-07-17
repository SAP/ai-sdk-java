package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.sap.ai.sdk.core.RealtimeParamTurnDetection;
import com.sap.ai.sdk.foundationmodels.openai.AudioInputChannel;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Slf4j
public class RealtimeApiTest {

  private static final double LOG_2 = Math.log(2.0);

  private static byte[] QUESTION_FIXTURE_PCM;

  @BeforeAll
  public static void setUp() {
    try (var fis = new FileInputStream("src/test/resources/fixtures/question.pcm")) {
      QUESTION_FIXTURE_PCM = fis.readAllBytes();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  @Timeout(value = 30, unit = TimeUnit.SECONDS)
  void testTextToSpeech() {
    var outputBuffer = new ByteArrayOutputStream(300000);
    var monitor = new CountDownLatch(1);
    var client = OpenAiClient.realtimeClient();

    try (TextInputChannel input =
        client.textToSpeech(
            (byteChunk, isLast) -> {
              outputBuffer.writeBytes(byteChunk);
              if (isLast) {
                monitor.countDown();
              }
            },
            RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)) {
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

    var metrics = pcm16AudioMetrics(outputBuffer.toByteArray());

    // root mean squire (measures the deviation from an average value)
    assertThat(metrics.rms).isGreaterThan(500d);
    // asserts that variety of deviation is sufficient (not a trivial repeating pattern)
    assertThat(metrics.entropy).isGreaterThan(4);
  }

  @Test
  @Timeout(value = 60, unit = TimeUnit.SECONDS)
  void testSpeechToSpeech() {
    var outputBuffer = new ByteArrayOutputStream(300000);
    var monitor = new CountDownLatch(1);
    var client = OpenAiClient.realtimeClient();

    try (AudioInputChannel input =
        client.speechToSpeech(
            (byteChunk, isLast) -> {
              outputBuffer.writeBytes(byteChunk);
              if (isLast) {
                monitor.countDown();
              }
            },
            RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)) {
      input.inputAudio(QUESTION_FIXTURE_PCM);
      monitor.await();
    } catch (Exception e) {
      if (!(e instanceof InterruptedException)) {
        fail(e);
      }
      // do nothing, test has either been interrupted by user (intended) or by jupiter if timeout is
      // reached
      return;
    }

    try (var fis = new FileOutputStream("/tmp/response.pcm")) {
      fis.write(outputBuffer.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private record AudioMetrics(double rms, double entropy) {}
  ;

  /**
   * Audio quality metrics for signed 16-bit little-endian PCM, computed on the mean-subtracted
   * (DC-offset-removed) signal so they reflect only the varying, audible part of the audio.
   *
   * @param pcm - The raw PCM audio buffer.
   * @return The RMS amplitude and Shannon entropy (bits) of the zero-mean samples.
   */
  AudioMetrics pcm16AudioMetrics(byte[] pcm) {
    if (pcm.length < 1) {
      return new AudioMetrics(0, 0);
    }
    var sum = 0L;
    for (var i = 0; i < pcm.length; i += 2) {
      sum += (pcm[i]) | ((pcm[i + 1]) << 8);
    }
    var mean = sum / (pcm.length / 2);

    var sumOfSquares = 0d;
    var counts = new char[Character.MAX_VALUE];
    for (var i = 0; i < pcm.length; i += 2) {
      var residual = ((pcm[i]) | ((pcm[i + 1]) << 8)) - mean;
      sumOfSquares += residual * residual;
      var key = (int) (residual + Short.MAX_VALUE + 1);
      counts[key]++;
    }

    var rms = Math.sqrt(sumOfSquares / (double) (pcm.length / 2));
    var entropy = 0d;
    for (var i = 0; i < counts.length; i++) {
      var p = counts[i] / counts.length;
      entropy -= p * log2(p);
    }

    return new AudioMetrics(rms, entropy);
  }

  private static double log2(double logNumber) {
    return Math.log(logNumber) / LOG_2;
  }
}
