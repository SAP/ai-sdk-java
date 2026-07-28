package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioConfigOutput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeSessionCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class ToAudioRealtimeClientUnitTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final RealtimeAudioConfigInput FIXED_INPUT_CONFIG =
      RealtimeAudioConfigInput.builder()
          .format(
              RealtimeAudioFormats.AudioPcm.builder()
                  .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                  .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                  .build())
          .build();

  private AudioOutputChannel outputConsumerMock;

  @BeforeEach
  void setUp() {
    outputConsumerMock = mock(AudioOutputChannel.class);
  }

  /**
   * Concrete subclass that calls the URL-based constructor with a stub address. {@code buildAsync}
   * is non-blocking so the constructor returns immediately without attempting a real connection.
   */
  private static class DirectClient extends ToAudioRealtimeClient {

    DirectClient(
        final AudioOutputChannel outputConsumer,
        final boolean defaultEager,
        final RealtimeParam... params) {
      super("ws://localhost:0", Map.of(), outputConsumer, defaultEager, params);
    }

    @Override
    @Nonnull
    protected RealtimeAudioConfigInput inputConfig() {
      return FIXED_INPUT_CONFIG;
    }
  }

  private DirectClient build(
      final AudioOutputChannel outputConsumer,
      final boolean defaultEager,
      final RealtimeParam... params) {
    return new DirectClient(outputConsumer, defaultEager, params);
  }

  @Test
  void outputConsumerIsStoredFromConstructor() {
    assertThat(build(outputConsumerMock, false).outputConsumer).isSameAs(outputConsumerMock);
  }

  @Test
  void defaultVoiceIsMarinWhenNoVoiceParamProvided() {
    assertThat(build(outputConsumerMock, false).voice)
        .isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN);
  }

  @Test
  void voiceIsMarinWhenDefault2Provided() {
    assertThat(build(outputConsumerMock, false, RealtimeParamVoice.DEFAULT_2).voice)
        .isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN);
  }

  @Test
  void voiceIsEchoWhenDefault1Provided() {
    assertThat(build(outputConsumerMock, false, RealtimeParamVoice.DEFAULT_1).voice)
        .isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO);
  }

  @Test
  void lastVoiceParamWinsWhenMultipleProvided() {
    assertThat(
            build(
                    outputConsumerMock,
                    false,
                    RealtimeParamVoice.DEFAULT_1,
                    RealtimeParamVoice.DEFAULT_2)
                .voice)
        .isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN);
  }

  @Test
  void turnDetectionFollowsDefaultFalseWhenNoParamProvided() {
    assertThat(build(outputConsumerMock, false).eagerTurnDetection).isFalse();
  }

  @Test
  void turnDetectionFollowsDefaultTrueWhenNoParamProvided() {
    assertThat(build(outputConsumerMock, true).eagerTurnDetection).isTrue();
  }

  @Test
  void turnDetectionIsEagerWhenEachCallIsATurnParamProvided() {
    assertThat(
            build(outputConsumerMock, false, RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)
                .eagerTurnDetection)
        .isTrue();
  }

  @Test
  void turnDetectionIsByModelWhenByModelAutoParamProvided() {
    assertThat(
            build(outputConsumerMock, true, RealtimeParamTurnDetection.BY_MODEL_AUTO)
                .eagerTurnDetection)
        .isFalse();
  }

  @Test
  void lastTurnDetectionParamWinsWhenMultipleProvided() {
    assertThat(
            build(
                    outputConsumerMock,
                    false,
                    RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN,
                    RealtimeParamTurnDetection.BY_MODEL_AUTO)
                .eagerTurnDetection)
        .isFalse();
  }

  @Test
  void systemPromptIsEmptyWhenNoParamProvided() {
    assertThat(build(outputConsumerMock, false).systemPrompt).isEmpty();
  }

  @Test
  void systemPromptIsStoredWhenParamProvided() {
    assertThat(
            build(outputConsumerMock, false, new RealtimeParamSystemPrompt("You are helpful."))
                .systemPrompt)
        .isEqualTo("You are helpful.");
  }

  @Test
  void lastSystemPromptParamWinsWhenMultipleProvided() {
    assertThat(
            build(
                    outputConsumerMock,
                    false,
                    new RealtimeParamSystemPrompt("first"),
                    new RealtimeParamSystemPrompt("second"))
                .systemPrompt)
        .isEqualTo("second");
  }

  @Test
  void getSystemPromptReturnsEmptyWhenSystemPromptIsBlank() {
    assertThat(build(outputConsumerMock, false).getSystemPrompt()).isEqualTo(Optional.empty());
  }

  @Test
  void getSystemPromptReturnsValueWhenSystemPromptIsSet() {
    final var prompt = "You are a helpful assistant.";
    assertThat(
            build(outputConsumerMock, false, new RealtimeParamSystemPrompt(prompt))
                .getSystemPrompt())
        .isEqualTo(Optional.of(prompt));
  }

  @Test
  void onResponseDecodesBase64AudioAndForwardsWithNotDoneFlag() throws Exception {
    final var client = build(outputConsumerMock, false);
    final byte[] rawAudio = {0x01, 0x02, 0x03};
    final var base64 = Base64.getEncoder().encodeToString(rawAudio);
    final var event =
        MAPPER.readTree("{\"type\":\"response.output_audio.delta\",\"delta\":\"" + base64 + "\"}");

    client.onResponse("response.output_audio.delta", event);

    final var captor = ArgumentCaptor.forClass(byte[].class);
    verify(outputConsumerMock).outputAudio(captor.capture(), eq(Boolean.FALSE));
    assertThat(captor.getValue()).isEqualTo(rawAudio);
  }

  @Test
  void onResponseForwardsEmptyBytesWithDoneFlagOnAudioDoneEvent() throws Exception {
    final var client = build(outputConsumerMock, false);
    final var event = MAPPER.readTree("{\"type\":\"response.output_audio.done\"}");

    client.onResponse("response.output_audio.done", event);

    final var captor = ArgumentCaptor.forClass(byte[].class);
    verify(outputConsumerMock).outputAudio(captor.capture(), eq(Boolean.TRUE));
    assertThat(captor.getValue()).isEmpty();
  }

  @Test
  void onResponseDoesNotCallOutputConsumerForUnknownEventType() throws Exception {
    final var client = build(outputConsumerMock, false);
    final var event = MAPPER.readTree("{\"type\":\"session.created\"}");

    client.onResponse("session.created", event);

    verifyNoInteractions(outputConsumerMock);
  }

  @Test
  void sessionConfigurationHasAudioOutputModality() {
    final var realtimeSession =
        build(outputConsumerMock, false)
            .sessionConfiguration()
            .session()
            .realtimeSessionCreateRequest()
            .orElseThrow();

    assertThat(realtimeSession.outputModalities().orElseThrow())
        .containsExactly(RealtimeSessionCreateRequest.OutputModality.AUDIO);
  }

  @Test
  void sessionConfigurationOutputFormatIsPcm24000() {
    final var output =
        build(outputConsumerMock, false)
            .sessionConfiguration()
            .session()
            .realtimeSessionCreateRequest()
            .orElseThrow()
            .audio()
            .orElseThrow()
            .output()
            .orElseThrow();
    final var format = output.format().orElseThrow().audioPcm().orElseThrow();

    assertThat(format.type().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM);
    assertThat(format.rate().orElseThrow()).isEqualTo(RealtimeAudioFormats.AudioPcm.Rate._24000);
  }

  @Test
  void sessionConfigurationOutputVoiceIsMarinByDefault() {
    final var voice =
        build(outputConsumerMock, false)
            .sessionConfiguration()
            .session()
            .realtimeSessionCreateRequest()
            .orElseThrow()
            .audio()
            .orElseThrow()
            .output()
            .orElseThrow()
            .voice()
            .orElseThrow()
            .unionMember1()
            .orElseThrow();

    assertThat(voice).isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN);
  }

  @Test
  void sessionConfigurationOutputVoiceIsEchoWhenDefault1Provided() {
    final var voice =
        build(outputConsumerMock, false, RealtimeParamVoice.DEFAULT_1)
            .sessionConfiguration()
            .session()
            .realtimeSessionCreateRequest()
            .orElseThrow()
            .audio()
            .orElseThrow()
            .output()
            .orElseThrow()
            .voice()
            .orElseThrow()
            .unionMember1()
            .orElseThrow();

    assertThat(voice).isEqualTo(RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO);
  }

  @Test
  void sessionConfigurationInputConfigDelegatestoInputConfig() {
    final var inputConfig =
        build(outputConsumerMock, false)
            .sessionConfiguration()
            .session()
            .realtimeSessionCreateRequest()
            .orElseThrow()
            .audio()
            .orElseThrow()
            .input()
            .orElseThrow();

    assertThat(inputConfig).isEqualTo(FIXED_INPUT_CONFIG);
  }
}
