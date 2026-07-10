package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpeechToSpeechRealtimeClient extends WSSOpenAIRealtimeClient implements AudioInputChannel {

    private static final int MAX_DATA_CHUNK_SIZE_BYTES = 8192;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final Set<String> HANDLED_RESPONSE_TYPES = Set.of(
            "response.output_audio.delta", "response.output_audio.done"
    );

    private static final List<RealtimeSessionCreateRequest.OutputModality> OUTPUT_MODALITIES = List.of(
            RealtimeSessionCreateRequest.OutputModality.AUDIO
    );

    private static final String TASK = "";


    private final AudioOutputChannel outputConsumer;

    public SpeechToSpeechRealtimeClient(String url, Map<String, String> httpHeaders, AudioOutputChannel outputConsumer) {
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
            var audioInputMessage = InputAudioBufferAppendEvent.builder()
                    .audio(Base64.getEncoder().encodeToString(part)).build();
            super.sendMessage(audioInputMessage);
            cursorLeft += MAX_DATA_CHUNK_SIZE_BYTES;
        }

        // TODO: make turn detection configurable and uncomment for explicit turn control
        // var commitAudioMessage = InputAudioBufferCommitEvent.builder().build();
        // super.sendMessage(commitAudioMessage);
        // askForResponse();
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
    SessionUpdateEvent sessionUpdateEvent =
        SessionUpdateEvent.builder()
            .session(
                ClientSecretCreateParams.Session.ofRealtime(
                        RealtimeSessionCreateRequest.builder()
                            .outputModalities(OUTPUT_MODALITIES)
                            .audio(
                                RealtimeAudioConfig.builder()
                                    .input(
                                        RealtimeAudioConfigInput.builder()
                                            .turnDetection(
                                                RealtimeAudioInputTurnDetection.ofSemanticVad(RealtimeAudioInputTurnDetection.SemanticVad.builder().build()))
                                            .format(
                                                RealtimeAudioFormats.AudioPcm.builder()
                                                    .type(
                                                        RealtimeAudioFormats.AudioPcm.Type
                                                            .AUDIO_PCM)
                                                    .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                    .build())
                                            .build())
                                    .output(
                                        RealtimeAudioConfigOutput.builder()
                                            .format(
                                                RealtimeAudioFormats.AudioPcm.builder()
                                                    .type(
                                                        RealtimeAudioFormats.AudioPcm.Type
                                                            .AUDIO_PCM)
                                                    .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                    .build())
                                            .voice(
                                                RealtimeAudioConfigOutput.Voice.UnionMember1.ALLOY)
                                            .build())
                                    .build())
                            .build())
                    .asRealtime())
            .build();
        return sessionUpdateEvent;
    }
}
