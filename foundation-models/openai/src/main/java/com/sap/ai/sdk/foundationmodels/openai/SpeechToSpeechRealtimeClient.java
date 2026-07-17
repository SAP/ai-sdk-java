package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import com.sap.ai.sdk.core.RealtimeParam;
import com.sap.ai.sdk.core.RealtimeParamTurnDetection;
import com.sap.ai.sdk.core.RealtimeParamVoice;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SpeechToSpeechRealtimeClient extends WSSOpenAiRealtimeClient implements AudioInputChannel {

  private static final int MAX_DATA_CHUNK_SIZE_BYTES = 8192;
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  private static final Set<String> HANDLED_RESPONSE_TYPES =
      Set.of("response.output_audio.delta", "response.output_audio.done");

  private static final List<RealtimeSessionCreateRequest.OutputModality> OUTPUT_MODALITIES =
      List.of(RealtimeSessionCreateRequest.OutputModality.AUDIO);

  private static final String TASK = "";

  private final AudioOutputChannel outputConsumer;
  private final RealtimeAudioConfigOutput.Voice.UnionMember1 voice;
  private final boolean eagerTurnDetection;

  public SpeechToSpeechRealtimeClient(
      String url,
      Map<String, String> httpHeaders,
      AudioOutputChannel outputConsumer,
      RealtimeParam... params) {
    super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
    this.outputConsumer = outputConsumer;

    var voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
    var turnDetectionEager = false;
    for (RealtimeParam param : params) {
      switch (param.getParamName()) {
        case VOICE -> {
          if (RealtimeParamVoice.DEFAULT_2.equals(param)) {
            voice = RealtimeAudioConfigOutput.Voice.UnionMember1.MARIN;
          } else if (RealtimeParamVoice.DEFAULT_1.equals(param)) {
            voice = RealtimeAudioConfigOutput.Voice.UnionMember1.ECHO;
          }
        }
        case TURN_DETECTION -> {
          if (RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(param)) {
            turnDetectionEager = true;
          } else if (RealtimeParamTurnDetection.BY_MODEL_AUTO.equals(param)) {
            turnDetectionEager = false;
          }
        }
      }
    }
    this.voice = voice;
    this.eagerTurnDetection = turnDetectionEager;
  }

  public void inputAudio(byte[] rawAudioChunk) {
    if (rawAudioChunk.length == 0) {
      return;
    }
    var cursorLeft = 0;
    while (cursorLeft < rawAudioChunk.length) {
      var cursorRight = Math.min(cursorLeft + MAX_DATA_CHUNK_SIZE_BYTES, rawAudioChunk.length);
      var part = Arrays.copyOfRange(rawAudioChunk, cursorLeft, cursorRight);
      var audioInputMessage =
          InputAudioBufferAppendEvent.builder()
              .audio(Base64.getEncoder().encodeToString(part))
              .build();
      super.sendMessage(audioInputMessage);
      cursorLeft += MAX_DATA_CHUNK_SIZE_BYTES;
    }

    if (eagerTurnDetection) {
      var commitAudioMessage = InputAudioBufferCommitEvent.builder().build();
      super.sendMessage(commitAudioMessage);
      askForResponse();
    }
  }

  @Override
  protected String getSystemPrompt() {
    return TASK;
  }

  @Override
  protected void onResponse(String eventType, JsonNode event) {
    if ("response.output_audio.delta".equals(eventType)) {
      var base64Audio = event.get("delta").asText();
      byte[] audio = Base64.getDecoder().decode(base64Audio);
      this.outputConsumer.outputAudio(audio, Boolean.FALSE);
    } else if ("response.output_audio.done".equals(eventType)) {
      this.outputConsumer.outputAudio(EMPTY_BYTE_ARRAY, Boolean.TRUE);
    }
  }

  @Override
  protected SessionUpdateEvent sessionConfiguration() {
    RealtimeAudioInputTurnDetection turnDetection;
    if (eagerTurnDetection) {
      turnDetection = null;
    } else {
      turnDetection =
          RealtimeAudioInputTurnDetection.ofSemanticVad(
              RealtimeAudioInputTurnDetection.SemanticVad.builder().build());
    }

    return SessionUpdateEvent.builder()
        .session(
            ClientSecretCreateParams.Session.ofRealtime(
                    RealtimeSessionCreateRequest.builder()
                        .outputModalities(OUTPUT_MODALITIES)
                        .audio(
                            RealtimeAudioConfig.builder()
                                .input(
                                    RealtimeAudioConfigInput.builder()
                                        .turnDetection(turnDetection)
                                        .format(
                                            RealtimeAudioFormats.AudioPcm.builder()
                                                .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                                                .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                .build())
                                        .build())
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
