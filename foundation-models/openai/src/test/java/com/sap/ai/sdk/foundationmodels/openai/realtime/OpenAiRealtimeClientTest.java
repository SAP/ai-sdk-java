package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.foundationmodels.openai.AudioInputChannel;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenAiRealtimeClientTest {

  private OpenAiRealtimeClient client;

  @BeforeEach
  void setUp() {
    client = OpenAiClient.realtimeClient();
  }

  @Test
  void textToSpeech() {
    try (TextInputChannel ic = client.textToSpeech((a, b) -> {})) {
      assertThat(ic).isNotNull();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void speechToSpeech() {
    try (AudioInputChannel ic = client.speechToSpeech((a, b) -> {})) {
      assertThat(ic).isNotNull();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
