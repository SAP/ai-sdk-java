package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.RealtimeAudioConfig;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioConfigOutput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeSessionCreateRequest;
import com.openai.models.realtime.SessionUpdateEvent;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/** Implements common functionality for realtime api clients that output audio */
@Slf4j
abstract class ToAudioRealtimeClient extends WSOpenAiRealtimeClient {

  private static final Set<String> HANDLED_RESPONSE_TYPES =
      Set.of("response.output_audio.delta", "response.output_audio.done");
  private static final List<RealtimeSessionCreateRequest.OutputModality> OUTPUT_MODALITIES =
      List.of(RealtimeSessionCreateRequest.OutputModality.AUDIO);
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  final AudioOutputChannel outputConsumer;
  final RealtimeAudioConfigOutput.Voice.UnionMember1 voice;

  /**
   * defines default eagerness (if EACH_CALL_IS_A_TURN) behavior if specific turn detection config
   * not provided
   */
  protected final boolean eagerTurnDetection;

  final String systemPrompt;

  /**
   * Constructs the object
   *
   * @param url - realtime api endpoint url
   * @param httpHeaders - http headers (key - value) for client to use
   * @param outputConsumer - consumer of audio bytes in pcm 24000 Hz mono little endian format
   * @param defaultTurnDetectionEager - if explicit cfg for turn detection was not specified, this
   *     turn detection eagerness flag will be used (true results in EACH_CALL_IS_A_TURN handling)
   * @param params - possible overrides for default params (e.g. voice, system prompt, etc.)
   */
  public ToAudioRealtimeClient(
      @Nonnull final String url,
      @Nonnull final Map<String, String> httpHeaders,
      @Nonnull final AudioOutputChannel outputConsumer,
      final boolean defaultTurnDetectionEager,
      @Nonnull final RealtimeParam... params) {
    super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
    var voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.OUTPUT_VOICE) {
        if (RealtimeParamVoice.DEFAULT_2.equals(param)) {
          voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
        } else if (RealtimeParamVoice.DEFAULT_1.equals(param)) {
          voice = RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO;
        }
      }
    }

    var turnDetectionEager = defaultTurnDetectionEager;
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.TURN_DETECTION) {
        if (RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(param)) {
          turnDetectionEager = true;
        } else if (RealtimeParamTurnDetection.BY_MODEL_AUTO.equals(param)) {
          turnDetectionEager = false;
        }
      }
    }

    var systemPrompt = "";
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.SYSTEM_PROMPT) {
        systemPrompt = param.getValueAsString();
      }
    }

    this.outputConsumer = outputConsumer;
    this.voice = voice;
    this.eagerTurnDetection = turnDetectionEager;
    this.systemPrompt = systemPrompt;
  }

  ToAudioRealtimeClient(
      @Nonnull final HttpClient httpClient,
      @Nonnull final CompletableFuture<WebSocket> ws,
      @Nonnull final Timer timer,
      @Nonnull final AudioOutputChannel outputConsumer,
      final boolean eagerTurnDetection,
      @Nonnull final RealtimeParam... params) {
    super(httpClient, ws, timer, HANDLED_RESPONSE_TYPES);
    var voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.OUTPUT_VOICE) {
        if (RealtimeParamVoice.DEFAULT_2.equals(param)) {
          voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
        } else if (RealtimeParamVoice.DEFAULT_1.equals(param)) {
          voice = RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO;
        }
      }
    }
    var turnDetectionEager = eagerTurnDetection;
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.TURN_DETECTION) {
        if (RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(param)) {
          turnDetectionEager = true;
        } else if (RealtimeParamTurnDetection.BY_MODEL_AUTO.equals(param)) {
          turnDetectionEager = false;
        }
      }
    }
    var systemPrompt = "";
    for (final RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.ParamName.SYSTEM_PROMPT) {
        systemPrompt = param.getValueAsString();
      }
    }
    this.outputConsumer = outputConsumer;
    this.voice = voice;
    this.eagerTurnDetection = turnDetectionEager;
    this.systemPrompt = systemPrompt;
  }

  @Nonnull
  protected abstract RealtimeAudioConfigInput inputConfig();

  @Override
  @Nonnull
  protected Optional<String> getSystemPrompt() {
    return systemPrompt.isEmpty() ? Optional.empty() : Optional.of(systemPrompt);
  }

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
