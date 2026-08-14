package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.RealtimeAudioFormats;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TextToSpeechRealtimeClientUnitTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private AudioOutputChannel outputConsumerMock;
  private WebSocket webSocketMock;

  @BeforeEach
  void setUp() {
    outputConsumerMock = mock(AudioOutputChannel.class);
    webSocketMock = mock(WebSocket.class);
    when(webSocketMock.sendText(any(), anyBoolean()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
    when(webSocketMock.sendClose(anyInt(), anyString()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
    when(webSocketMock.sendPing(any()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
  }

  private TextToSpeechRealtimeClient build(final RealtimeParam... params) {
    return new TextToSpeechRealtimeClient(
        mock(HttpClient.class),
        CompletableFuture.completedFuture(webSocketMock),
        mock(Timer.class),
        outputConsumerMock,
        params);
  }

  private List<String> captureAllSentTexts() {
    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    try {
      org.mockito.Mockito.verify(webSocketMock, atLeastOnce())
          .sendText(captor.capture(), anyBoolean());
      return captor.getAllValues().stream().map(CharSequence::toString).toList();
    } catch (final org.mockito.exceptions.verification.WantedButNotInvoked e) {
      return List.of();
    }
  }

  @Test
  void defaultTurnDetectionIsEager() {
    assertThat(build().eagerTurnDetection).isTrue();
  }

  @Test
  void turnDetectionCanBeOverriddenToNonEagerByParam() {
    assertThat(build(RealtimeParamTurnDetection.BY_MODEL_AUTO).eagerTurnDetection).isFalse();
  }

  @Test
  void outputConsumerIsStoredFromConstructor() {
    assertThat(build().outputConsumer).isSameAs(outputConsumerMock);
  }

  @Test
  void defaultSystemPromptIsSetByConstructor() {
    assertThat(build().systemPrompt).isNotEmpty();
  }

  @Test
  void callerSystemPromptOverridesDefaultBecauseItComesLast() {
    final var custom = "My custom prompt.";
    assertThat(build(new RealtimeParamSystemPrompt(custom)).systemPrompt).isEqualTo(custom);
  }

  @Test
  void inputConfigHasPcm24000Format() {
    final var format = build().inputConfig().format().orElseThrow().audioPcm().orElseThrow();

    assertThat(format.type().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM);
    assertThat(format.rate().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Rate._24000);
  }

  @Test
  void inputConfigHasNoTurnDetection() {
    assertThat(build().inputConfig().turnDetection()).isEmpty();
  }

  @Test
  void sendTextSendsConversationItemCreateEvent() {
    build().sendText("Hello world");

    final var sent = captureAllSentTexts();
    assertThat(sent).isNotEmpty();
    final var conversationCreate =
        sent.stream()
            .map(
                s -> {
                  try {
                    return MAPPER.readTree(s);
                  } catch (Exception e) {
                    throw new RuntimeException(e);
                  }
                })
            .filter(n -> "conversation.item.create".equals(n.get("type").asText()))
            .findFirst();
    assertThat(conversationCreate).isPresent();
    assertThat(conversationCreate.get().at("/item/content/0/text").asText())
        .isEqualTo("Hello world");
  }

  @Test
  void sendTextSendsResponseCreateWhenEager() throws Exception {
    build().sendText("Hello");

    final var sent = captureAllSentTexts();
    final var types =
        sent.stream()
            .map(
                s -> {
                  try {
                    return MAPPER.readTree(s).get("type").asText();
                  } catch (Exception e) {
                    throw new RuntimeException(e);
                  }
                })
            .toList();
    assertThat(types).contains("conversation.item.create", "response.create");
  }

  @Test
  void sendTextDoesNotSendResponseCreateWhenNotEager() throws Exception {
    build(RealtimeParamTurnDetection.BY_MODEL_AUTO).sendText("Hello");

    final var sent = captureAllSentTexts();
    final var types =
        sent.stream()
            .map(
                s -> {
                  try {
                    return MAPPER.readTree(s).get("type").asText();
                  } catch (Exception e) {
                    throw new RuntimeException(e);
                  }
                })
            .toList();
    assertThat(types).contains("conversation.item.create");
    assertThat(types).doesNotContain("response.create");
  }

  @Test
  void sendTextPreservesFullTextInPayload() throws Exception {
    final var longText = "a".repeat(500);
    build().sendText(longText);

    final var sent = captureAllSentTexts();
    final var conversationCreate =
        sent.stream()
            .map(
                s -> {
                  try {
                    return MAPPER.readTree(s);
                  } catch (Exception e) {
                    throw new RuntimeException(e);
                  }
                })
            .filter(n -> "conversation.item.create".equals(n.get("type").asText()))
            .findFirst()
            .orElseThrow();
    assertThat(conversationCreate.at("/item/content/0/text").asText()).isEqualTo(longText);
  }
}
