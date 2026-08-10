package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.RealtimeAudioConfig;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioConfigOutput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeSessionCreateRequest;
import com.openai.models.realtime.SessionUpdateEvent;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Base64;
import java.util.HashMap;
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

  private static final Map<RealtimeParam.ParamName, RealtimeParam> FALLBACK_DEFAULT_PARAMS =
      Map.of(
          RealtimeParam.ParamName.OUTPUT_VOICE, RealtimeParamVoice.DEFAULT_1,
          RealtimeParam.ParamName.TURN_DETECTION, RealtimeParamTurnDetection.BY_MODEL_AUTO,
          RealtimeParam.ParamName.SYSTEM_PROMPT, new RealtimeParamSystemPrompt(""));

  final AudioOutputChannel outputConsumer;
  final RealtimeAudioConfigOutput.Voice.UnionMember1 voice;

  /** defines if every call to the client should be considered conversation turn */
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
    final var defaults = new HashMap<>(FALLBACK_DEFAULT_PARAMS);
    defaults.put(
        RealtimeParam.ParamName.TURN_DETECTION,
        defaultTurnDetectionEager
            ? RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN
            : RealtimeParamTurnDetection.BY_MODEL_AUTO);
    final var resolvedParams = resolveParams(defaults, params);

    this.outputConsumer = outputConsumer;
    this.voice =
        mapVoice((RealtimeParamVoice) resolvedParams.get(RealtimeParam.ParamName.OUTPUT_VOICE));
    this.eagerTurnDetection =
        RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(
            resolvedParams.get(RealtimeParam.ParamName.TURN_DETECTION));
    this.systemPrompt =
        resolvedParams.get(RealtimeParam.ParamName.SYSTEM_PROMPT).getValueAsString();
  }

  ToAudioRealtimeClient(
      @Nonnull final HttpClient httpClient,
      @Nonnull final CompletableFuture<WebSocket> ws,
      @Nonnull final Timer timer,
      @Nonnull final AudioOutputChannel outputConsumer,
      final boolean eagerTurnDetection,
      @Nonnull final RealtimeParam... params) {
    super(httpClient, ws, timer, HANDLED_RESPONSE_TYPES);
    final var defaults = new HashMap<>(FALLBACK_DEFAULT_PARAMS);
    defaults.put(
        RealtimeParam.ParamName.TURN_DETECTION,
        eagerTurnDetection
            ? RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN
            : RealtimeParamTurnDetection.BY_MODEL_AUTO);
    final var resolvedParams = resolveParams(defaults, params);
    this.outputConsumer = outputConsumer;
    this.voice =
        mapVoice((RealtimeParamVoice) resolvedParams.get(RealtimeParam.ParamName.OUTPUT_VOICE));
    this.eagerTurnDetection =
        RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(
            resolvedParams.get(RealtimeParam.ParamName.TURN_DETECTION));
    this.systemPrompt =
        resolvedParams.get(RealtimeParam.ParamName.SYSTEM_PROMPT).getValueAsString();
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
      this.outputConsumer.outputAudio(audio, false);
    } else if ("response.output_audio.done".equals(eventType)) {
      this.outputConsumer.outputAudio(EMPTY_BYTE_ARRAY, true);
    } else {
      log.warn("skipping message type: {}", eventType);
    }
  }

  /*
   side effect: modifies defaults
  */
  @Nonnull
  protected Map<RealtimeParam.ParamName, RealtimeParam> resolveParams(
      final @Nonnull Map<RealtimeParam.ParamName, RealtimeParam> defaults,
      final @Nonnull RealtimeParam... params) {
    for (final RealtimeParam param : params) {
      if (param == null) {
        log.warn("skipping null param for realtime client");
        continue;
      }
      defaults.put(param.getParamName(), param);
    }
    return defaults;
  }

  @Nonnull
  private RealtimeAudioConfigOutput.Voice.UnionMember1 mapVoice(
      @Nonnull final RealtimeParamVoice voice) {
    if (voice.equals(RealtimeParamVoice.DEFAULT_1)) {
      return RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
    } else if (voice.equals(RealtimeParamVoice.DEFAULT_2)) {
      return RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO;
    }
    return RealtimeAudioConfigOutput.Voice.UnionMember1.of(voice.getValueAsString());
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
