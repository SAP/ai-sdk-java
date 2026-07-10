package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TextToSpeechRealtimeClient extends WSSOpenAIRealtimeClient implements TextInputChannel {

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final Set<String> HANDLED_RESPONSE_TYPES = Set.of(
            "response.output_audio.delta", "response.output_audio.done"
    );

    private static final List<RealtimeSessionCreateRequest.OutputModality> OUTPUT_MODALITIES = List.of(
            RealtimeSessionCreateRequest.OutputModality.AUDIO
    );

    private static final String TASK =
            "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input";


    private final AudioOutputChannel outputConsumer;

    public TextToSpeechRealtimeClient(String url, Map<String, String> httpHeaders, AudioOutputChannel outputConsumer) {
        super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
        this.outputConsumer = outputConsumer;
    }

    public void sendText(String text) {
        var message = ConversationItemCreateEvent.builder()
                .item(ConversationItem.ofRealtimeConversationItemUserMessage(
                        RealtimeConversationItemUserMessage.builder()
                                .addContent(RealtimeConversationItemUserMessage.Content.builder()
                                        .text(text)
                                        .type(RealtimeConversationItemUserMessage.Content.Type.INPUT_TEXT).build()).build()
                )).build();

        super.sendMessage(message);
        askForResponse();
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
        } else {
            log.warn("skipping message type: {}", eventType);
        }
    }

    @Override
    protected SessionUpdateEvent sessionConfiguration() {
        SessionUpdateEvent sessionUpdateEvent = SessionUpdateEvent.builder()
            .session(
                ClientSecretCreateParams.Session.ofRealtime(
                        RealtimeSessionCreateRequest.builder()
                            .outputModalities(OUTPUT_MODALITIES)
                            .audio(
                                RealtimeAudioConfig.builder()
                                    .input(
                                        RealtimeAudioConfigInput.builder()
                                            .turnDetection(Optional.empty())
                                            .format(
                                                RealtimeAudioFormats.AudioPcm
                                                    .builder()
                                                        .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                                                        .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                                                .build())
                                            .build())
                                    .output(
                                        RealtimeAudioConfigOutput.builder()
                                            .format(RealtimeAudioFormats.AudioPcm
                                                .builder()
                                                    .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
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
