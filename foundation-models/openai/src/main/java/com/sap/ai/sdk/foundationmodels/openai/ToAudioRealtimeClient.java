package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.realtime.SessionUpdateEvent;

import java.util.Map;
import java.util.Set;

class ToAudioRealtimeClient extends WSSOpenAiRealtimeClient {

    private static final Set<String> HANDLED_RESPONSE_TYPES =
            Set.of("response.output_audio.delta", "response.output_audio.done");
    private static final String DEFAULT_SYSTEM_PROMPT = "";

    public ToAudioRealtimeClient(String url, Map<String, String> httpHeaders) {
        super(url, httpHeaders, HANDLED_RESPONSE_TYPES);
    }

    @Override
    protected String getSystemPrompt() {
        return "";
    }

    @Override
    protected void onResponse(String eventType, JsonNode event) {

    }

    @Override
    protected SessionUpdateEvent sessionConfiguration() {
        return null;
    }
}
