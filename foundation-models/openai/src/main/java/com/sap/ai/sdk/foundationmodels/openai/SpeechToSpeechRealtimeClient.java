package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.models.realtime.InputAudioBufferAppendEvent;
import com.openai.models.realtime.InputAudioBufferCommitEvent;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeAudioInputTurnDetection;
import com.sap.ai.sdk.core.RealtimeParam;
import com.sap.ai.sdk.core.RealtimeParamTurnDetection;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SpeechToSpeechRealtimeClient extends ToAudioRealtimeClient implements AudioInputChannel {

  private static final int MAX_DATA_CHUNK_SIZE_BYTES = 8192;

  private static final String TASK = "";

  private final boolean eagerTurnDetection;

  public SpeechToSpeechRealtimeClient(
      String url,
      Map<String, String> httpHeaders,
      AudioOutputChannel outputConsumer,
      RealtimeParam... params) {
    super(url, httpHeaders, outputConsumer, params);

    var turnDetectionEager = false;
    for (RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.SpeechOutputParamName.TURN_DETECTION) {
        if (RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(param)) {
          turnDetectionEager = true;
        } else if (RealtimeParamTurnDetection.BY_MODEL_AUTO.equals(param)) {
          turnDetectionEager = false;
        }
      }
    }
    this.eagerTurnDetection = turnDetectionEager;
  }

  @Override
  protected RealtimeAudioConfigInput inputConfig() {
    RealtimeAudioInputTurnDetection turnDetection;
    if (eagerTurnDetection) {
      turnDetection = null;
    } else {
      turnDetection =
          RealtimeAudioInputTurnDetection.ofSemanticVad(
              RealtimeAudioInputTurnDetection.SemanticVad.builder().build());
    }

    return RealtimeAudioConfigInput.builder()
        .turnDetection(turnDetection)
        .format(
            RealtimeAudioFormats.AudioPcm.builder()
                .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                .build())
        .build();
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
}
