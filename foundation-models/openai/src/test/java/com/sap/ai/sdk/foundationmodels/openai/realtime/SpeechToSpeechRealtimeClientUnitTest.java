package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SpeechToSpeechRealtimeClientUnitTest {

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

  private SpeechToSpeechRealtimeClient build(final RealtimeParam... params) {
    return new SpeechToSpeechRealtimeClient(
        mock(HttpClient.class),
        CompletableFuture.completedFuture(webSocketMock),
        mock(Timer.class),
        outputConsumerMock,
        params);
  }

  private List<String> captureAllSentTexts() {
    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    try {
      verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), anyBoolean());
      return captor.getAllValues().stream().map(CharSequence::toString).toList();
    } catch (final org.mockito.exceptions.verification.WantedButNotInvoked e) {
      return List.of();
    }
  }

  @Test
  void defaultTurnDetectionIsNotEager() {
    assertThat(build().eagerTurnDetection).isFalse();
  }

  @Test
  void turnDetectionBecomesEagerWhenParamProvided() {
    assertThat(build(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN).eagerTurnDetection).isTrue();
  }

  @Test
  void turnDetectionRemainsNonEagerWhenByModelAutoProvided() {
    assertThat(build(RealtimeParamTurnDetection.BY_MODEL_AUTO).eagerTurnDetection).isFalse();
  }

  @Test
  void outputConsumerIsStoredFromConstructor() {
    assertThat(build().outputConsumer).isSameAs(outputConsumerMock);
  }

  @Test
  void inputConfigHasPcm24000Format() {
    final var format = build().inputConfig().format().orElseThrow().audioPcm().orElseThrow();

    assertThat(format.type().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM);
    assertThat(format.rate().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Rate._24000);
  }

  @Test
  void inputConfigHasSemanticVadTurnDetectionWhenNotEager() {
    final var config = build(RealtimeParamTurnDetection.BY_MODEL_AUTO).inputConfig();

    assertThat(config.turnDetection().orElseThrow().semanticVad()).isPresent();
  }

  @Test
  void inputConfigHasNoTurnDetectionWhenEager() {
    final var config = build(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN).inputConfig();

    assertThat(config.turnDetection()).isEmpty();
  }

  @Test
  void inputAudioDoesNothingForEmptyArray() {
    build().inputAudio(new byte[0]);

    verify(webSocketMock, never()).sendText(any(), anyBoolean());
  }

  @Test
  void inputAudioSendsOneAppendEventForSmallChunk() throws Exception {
    final byte[] audio = {0x01, 0x02, 0x03}; // simple test fixture
    build().inputAudio(audio);

    final var sent = captureAllSentTexts();
    assertThat(sent).hasSize(1);
    final var node = MAPPER.readTree(sent.get(0));
    assertThat(node.get("type").asText()).isEqualTo("input_audio_buffer.append");
    assertThat(Base64.getDecoder().decode(node.get("audio").asText())).isEqualTo(audio);
  }

  @Test
  void inputAudioDoesNotSendCommitWhenNotEager() throws Exception {
    build().inputAudio(new byte[] {0x01});

    final var sent = captureAllSentTexts();
    assertThat(sent).hasSize(1);
    assertThat(MAPPER.readTree(sent.get(0)).get("type").asText())
        .isEqualTo("input_audio_buffer.append");
  }

  @Test
  void inputAudioSendsCommitAndResponseCreateWhenEager() throws Exception {
    build(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)
        .inputAudio(new byte[] {0x01}); // simple test fixture

    final var sent = captureAllSentTexts();
    // append + commit + response.create (from askForResponse)
    assertThat(sent).hasSize(3);
    assertThat(MAPPER.readTree(sent.get(0)).get("type").asText())
        .isEqualTo("input_audio_buffer.append");
    assertThat(MAPPER.readTree(sent.get(1)).get("type").asText())
        .isEqualTo("input_audio_buffer.commit");
    assertThat(MAPPER.readTree(sent.get(2)).get("type").asText()).isEqualTo("response.create");
  }

  @Test
  void inputAudioSplitsLargeInputInto8192ByteChunks() throws Exception {
    final int chunkSize = 8192;
    final byte[] audio = new byte[chunkSize * 2 + 100];
    Arrays.fill(audio, (byte) 0x42); // simple test fixture
    build().inputAudio(audio);

    final var sent = captureAllSentTexts();
    assertThat(sent).hasSize(3);

    final byte[] chunk1 =
        Base64.getDecoder().decode(MAPPER.readTree(sent.get(0)).get("audio").asText());
    final byte[] chunk2 =
        Base64.getDecoder().decode(MAPPER.readTree(sent.get(1)).get("audio").asText());
    final byte[] chunk3 =
        Base64.getDecoder().decode(MAPPER.readTree(sent.get(2)).get("audio").asText());
    assertThat(chunk1).hasSize(chunkSize);
    assertThat(chunk2).hasSize(chunkSize);
    assertThat(chunk3).hasSize(100);

    final byte[] reassembled = new byte[audio.length];
    System.arraycopy(chunk1, 0, reassembled, 0, chunkSize);
    System.arraycopy(chunk2, 0, reassembled, chunkSize, chunkSize);
    System.arraycopy(chunk3, 0, reassembled, chunkSize * 2, 100);
    assertThat(reassembled).isEqualTo(audio);
  }

  @Test
  void inputAudioSendsExactlyOneChunkWhenSizeEqualsChunkSize() {
    build().inputAudio(new byte[8192]);

    assertThat(captureAllSentTexts()).hasSize(1);
  }

  @Test
  void inputAudioWithEagerTurnDetectionSendsCommitAfterAllChunks() throws Exception {
    final byte[] audio = new byte[8192 + 1];
    build(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN).inputAudio(audio);

    final var sent = captureAllSentTexts();
    // 2 appends + 1 commit + 1 response.create
    assertThat(sent).hasSize(4);
    assertThat(MAPPER.readTree(sent.get(0)).get("type").asText())
        .isEqualTo("input_audio_buffer.append");
    assertThat(MAPPER.readTree(sent.get(1)).get("type").asText())
        .isEqualTo("input_audio_buffer.append");
    assertThat(MAPPER.readTree(sent.get(2)).get("type").asText())
        .isEqualTo("input_audio_buffer.commit");
    assertThat(MAPPER.readTree(sent.get(3)).get("type").asText()).isEqualTo("response.create");
  }
}
