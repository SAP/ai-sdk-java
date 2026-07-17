package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SpeechToTextRealtimeClient extends WSOpenAiRealtimeClient implements AudioInputChannel {

  private static final int MAX_DATA_CHUNK_SIZE_BYTES = 8192;
  private static final Set<String> HANDLED_RESPONSE_TYPES =
      Set.of("response.output_audio_transcript.done");

  private static final String TASK =
      "you are a transcript writer and your role is to convert input speech to text as precise as possible (word to word exactly, if possible)";

  private final TextOutputChannel outputConsumer;

  public SpeechToTextRealtimeClient(
      String url, Map<String, String> httpHeaders, TextOutputChannel outputConsumer) {
    super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
    this.outputConsumer = outputConsumer;
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

    var commitAudioMessage = InputAudioBufferCommitEvent.builder().build();
    super.sendMessage(commitAudioMessage);
    askForResponse();
  }

  @Override
  protected String getSystemPrompt() {
    return TASK;
  }

  @Override
  protected void onResponse(String eventType, JsonNode event) {
    if ("response.output_audio_transcript.done".equals(eventType)) {
      this.outputConsumer.outputText(
          event.has("transcript") ? event.get("transcript").asText() : "", Boolean.TRUE);
    } else {
      log.trace("skipping message type: {}", eventType);
    }
  }

  @Override
  protected SessionUpdateEvent sessionConfiguration() {
    SessionUpdateEvent sessionUpdateEvent =
        SessionUpdateEvent.builder()
            .session(
                ClientSecretCreateParams.Session.ofTranscription(
                        RealtimeTranscriptionSessionCreateRequest.builder()
                            .audio(
                                RealtimeTranscriptionSessionAudio.builder()
                                    .input(
                                        RealtimeTranscriptionSessionAudioInput.builder()
                                            .turnDetection(Optional.empty())
                                            .format(
                                                RealtimeAudioFormats.AudioPcm.builder()
                                                    .type(
                                                        RealtimeAudioFormats.AudioPcm.Type
                                                            .AUDIO_PCM)
                                                    .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                    .build())
                                            .transcription(
                                                AudioTranscription.builder()
                                                    .model(
                                                        AudioTranscription.Model.GPT_4O_TRANSCRIBE)
                                                    .build())
                                            .build())
                                    .build())
                            .build())
                    .asTranscription())
            .build();
    return sessionUpdateEvent;
  }
}
