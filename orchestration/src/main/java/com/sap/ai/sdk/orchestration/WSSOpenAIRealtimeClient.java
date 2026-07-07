package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class WSSOpenAIRealtimeClient implements AutoCloseable {

    private static final int SUCCESS_FINISH_WSS_CODE = 1000;
    private static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

    /**
     * WebSocket keeps TCP connection to the server. If connection idles, depending on the provider
     * in cloud environments, cloud provider sends hard TCP RST (reset) after 60-300 seconds of inactivity,
     * this is normally not configurable and would leave realtime conversation unusable on a pause.
     * <p/>
     * In mobile networks this period is even shorter and typical CGNAT session can only idle for 10-15 seconds
     * before it gets terminated
     * <p/>
     * In realtime client we rely on explicit lifetime management (create/close), heartbeat mechanism is required
     * to prevent unintended closing of connections while conversation is still used
     */
    private static final int HEARTBEAT_INTERVAL_MILLIS = 4500;
    private static final String HEARTBEAT_TIMER_NAME = "wss_realtime_heartbeat";

    private final HttpClient client;
    private final CompletableFuture<WebSocket> ws;
    private final Timer heartbeatTimer;
    private final Set<String> handleMessageTypes;

    public WSSOpenAIRealtimeClient(String url, Map<String, String> httpHeaders, Set<String> handleMessageTypes) {
        this.client = HttpClient.newHttpClient();
        var wsBuilder = this.client.newWebSocketBuilder();
        for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
            wsBuilder = wsBuilder.header(entry.getKey(), entry.getValue());
        }
        this.ws = wsBuilder.buildAsync(URI.create(url), new WebsocketListenerGlue(this::onSocketOpen, this::onText));
        this.handleMessageTypes = handleMessageTypes;
        this.heartbeatTimer = new Timer(HEARTBEAT_TIMER_NAME,true);
    }

    public synchronized void askForResponse() {
        WebSocket ws = this.ws.join();
        ws.sendText(JACKSON.valueToTree(ResponseCreateEvent.builder().build()), true);
        ws.request(1);
    }

    @Override
    public synchronized void close() {
        WebSocket ws = this.ws.join();
        heartbeatTimer.cancel();
        try {
            ws.sendClose(SUCCESS_FINISH_WSS_CODE, "done").join();
        } catch (Exception e) {
            log.error("Error while closing WebSocket", e);
        }
        this.client.close();
    }

    protected abstract String getSystemPrompt();

    protected abstract List<RealtimeSessionCreateRequest.OutputModality> getOutputModalities();

    protected abstract void onResponse(String eventType, JsonNode event);

    protected synchronized void sendMessage(RealtimeConversationItemUserMessage message) {
        WebSocket ws = this.ws.join();
        ws.sendText(JACKSON.valueToTree(message), true);
        ws.request(1);
    }

    private synchronized void onSocketOpen(WebSocket ws) {
        configureSession(ws);
        configureConversation(ws);
        scheduleHeartbeat(ws);
    }

    private void onText(WebSocket webSocket, CharSequence data) {
        final JsonNode event;
        try {
            event = JACKSON.readTree(data.toString());
        } catch (JsonProcessingException e) {
            throw new OrchestrationClientException(
                    "Error parsing JSON response from speech API", e);
        }
        var eventType = event.get("type").asText();
        if (handleMessageTypes.contains(eventType)) {
            onResponse(eventType, event);
        }

        webSocket.request(1);
    }

    private synchronized void sendPing(WebSocket ws) {
        if (ws.isInputClosed()) {
            return;
        }
        ws.sendPing(ByteBuffer.wrap("ping".getBytes())).join();
        ws.request(1);
    }

    private void configureSession(WebSocket ws) {
        SessionUpdateEvent sue = SessionUpdateEvent.builder()
                .session(ClientSecretCreateParams.Session.ofRealtime(
                        RealtimeSessionCreateRequest.builder()
                                .outputModalities(getOutputModalities())
                                .audio(RealtimeAudioConfig.builder()
                                        .input(RealtimeAudioConfigInput.builder()
                                                .format(RealtimeAudioFormats.AudioPcm.builder().build())
                                                .build())
                                        .output(RealtimeAudioConfigOutput.builder()
                                                .format(RealtimeAudioFormats.AudioPcm.builder().build())
                                                .voice(RealtimeAudioConfigOutput.Voice.UnionMember1.ALLOY)
                                                .build())
                                        .build())
                                .build())
                        .asRealtime())
                .build();
        ws.sendText(JACKSON.valueToTree(sue), true);
        ws.request(1);
    }

    private void configureConversation(WebSocket ws) {
        var systemPrompt = getSystemPrompt();
        var message = new RealtimeConversationItemSystemMessage.Builder().
                addContent(RealtimeConversationItemSystemMessage.Content.builder().text(systemPrompt).build());
        ws.sendText(JACKSON.valueToTree(message), true);
        ws.request(1);
    }

    private void scheduleHeartbeat(WebSocket ws) {
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                sendPing(ws);
            }

        };
        heartbeatTimer.scheduleAtFixedRate(task, 0, HEARTBEAT_INTERVAL_MILLIS);
    }
}
