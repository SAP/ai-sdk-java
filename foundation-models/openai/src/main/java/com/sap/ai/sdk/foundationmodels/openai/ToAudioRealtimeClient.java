package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.RealtimeAudioConfig;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioConfigOutput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeSessionCreateRequest;
import com.openai.models.realtime.SessionUpdateEvent;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import com.sap.ai.sdk.core.RealtimeParam;
import com.sap.ai.sdk.core.RealtimeParamVoice;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class ToAudioRealtimeClient extends WSOpenAiRealtimeClient {

  private static final Set<String> HANDLED_RESPONSE_TYPES =
      Set.of("response.output_audio.delta", "response.output_audio.done");
  private static final List<RealtimeSessionCreateRequest.OutputModality> OUTPUT_MODALITIES =
      List.of(RealtimeSessionCreateRequest.OutputModality.AUDIO);
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  private final AudioOutputChannel outputConsumer;
  private final RealtimeAudioConfigOutput.Voice.UnionMember1 voice;

  public ToAudioRealtimeClient(
      @Nonnull final String url,
      @Nonnull final Map<String, String> httpHeaders,
      @Nonnull final AudioOutputChannel outputConsumer,
      @Nonnull final RealtimeParam... params
  ) {
    super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
    var voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
    for (final RealtimeParam param : params) {
      switch (param.getParamName()) {
        case VOICE -> {
          if (RealtimeParamVoice.DEFAULT_2.equals(param)) {
            voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
          } else if (RealtimeParamVoice.DEFAULT_1.equals(param)) {
            voice = RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO;
          }
        }
      }
    }
    this.outputConsumer = outputConsumer;
    this.voice = voice;
  }

  @Nonnull
  protected abstract RealtimeAudioConfigInput inputConfig();

  @Override
  protected void onResponse(@Nonnull final String eventType, @Nonnull final JsonNode event) {
    if ("response.output_audio.delta".equals(eventType)) {
      final var base64Audio = event.get("delta").asText();
      final byte[] audio = Base64.getDecoder().decode(base64Audio);
      this.outputConsumer.outputAudio(audio, Boolean.FALSE);
    } else if ("response.output_audio.done".equals(eventType)) {
      this.outputConsumer.outputAudio(EMPTY_BYTE_ARRAY, Boolean.TRUE);
    } else {
      log.warn("skipping message type: {}", eventType);
    }
  }

  @Override
  @Nonnull
  protected SessionUpdateEvent sessionConfiguration() {
    return SessionUpdateEvent.builder()
        .session(
            ClientSecretCreateParams.Session.ofRealtime(
                    RealtimeSessionCreateRequest.builder()
                        .outputModalities(OUTPUT_MODALITIES)
                        .audio(
                            RealtimeAudioConfig.builder()
                                .input(inputConfig())
                                .output(
                                    RealtimeAudioConfigOutput.builder()
                                        .format(
                                            RealtimeAudioFormats.AudioPcm.builder()
                                                .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                                                .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                .build())
                                        .voice(voice)
                                        .build())
                                .build())
                        .build())
                .asRealtime())
        .build();
  }
}
