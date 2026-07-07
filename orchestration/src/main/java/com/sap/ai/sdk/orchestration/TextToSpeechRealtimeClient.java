package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;

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


    private final BiConsumer<byte[], Boolean> outputConsumer;

    public TextToSpeechRealtimeClient(String url, Map<String, String> httpHeaders, BiConsumer<byte[], Boolean> outputConsumer) {
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
    }

    @Override
    protected String getSystemPrompt() {
        return TASK;
    }

    @Override
    protected List<RealtimeSessionCreateRequest.OutputModality> getOutputModalities() {
        return OUTPUT_MODALITIES;
    }

    @Override
    protected void onResponse(String eventType, JsonNode event) {
        if ("response.output_audio.delta".equals(eventType)) {
            var base64Audio = event.get("delta").asText();
            byte[] audio = Base64.getDecoder().decode(base64Audio);
            this.outputConsumer.accept(audio, Boolean.FALSE);
            log.warn("Called consumer with base64 converted to bytes: {} (base64)", base64Audio);
        } else if ("response.output_audio.done".equals(eventType)) {
            this.outputConsumer.accept(EMPTY_BYTE_ARRAY, Boolean.TRUE);
        } else {
            log.warn("skipping message type: {}", eventType);
        }
    }
}
