package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.*;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class WSSOpenAIRealtimeClient implements AutoCloseable {

  private static final int SUCCESS_FINISH_WSS_CODE = 1000;
  private static final ObjectMapper JACKSON =
      getOrchestrationObjectMapper()
          .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

  /**
   * WebSocket keeps TCP connection to the server. If connection idles, depending on the provider in
   * cloud environments, cloud provider sends hard TCP RST (reset) after 60-300 seconds of
   * inactivity, this is normally not configurable and would leave realtime conversation unusable on
   * a pause.
   *
   * <p>In mobile networks this period is even shorter and typical CGNAT session can only idle for
   * 10-15 seconds before it gets terminated
   *
   * <p>In realtime client we rely on explicit lifetime management (create/close), heartbeat
   * mechanism is required to prevent unintended closing of connections while conversation is still
   * expected to continue
   */
  private static final int HEARTBEAT_INTERVAL_MILLIS = 4500;

  private static final String HEARTBEAT_TIMER_NAME = "wss_realtime_heartbeat";

  private final HttpClient client;
  private final CompletableFuture<WebSocket> ws;
  private final Timer heartbeatTimer;
  private final Set<String> handleMessageTypes;

  public WSSOpenAIRealtimeClient(
      String url, Map<String, String> httpHeaders, Set<String> handleMessageTypes) {
    this.client = HttpClient.newHttpClient();
    var wsBuilder = this.client.newWebSocketBuilder();
    for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
      wsBuilder = wsBuilder.header(entry.getKey(), entry.getValue());
    }
    this.ws =
        wsBuilder.buildAsync(
            URI.create(url), new BufferedWebSocketListener(this::onSocketOpen, this::onText));
    this.handleMessageTypes = handleMessageTypes;
    this.heartbeatTimer = new Timer(HEARTBEAT_TIMER_NAME, true);
  }

  public void askForResponse() {
    WebSocket ws = this.ws.join();
    synchronized (this) {
      try {
        ws.sendText(JACKSON.writeValueAsString(ResponseCreateEvent.builder().build()), true);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to serialize ask for response", e);
      }
      ws.request(1);
    }
  }

  @Override
  public void close() {
    WebSocket ws = this.ws.join();
    synchronized (this) {
      heartbeatTimer.cancel();
      try {
        ws.sendClose(SUCCESS_FINISH_WSS_CODE, "done").join();
      } catch (Exception e) {
        log.error("Error while closing WebSocket", e);
      }
      // this.client.close(); // exists only since java 21
    }
  }

  protected abstract String getSystemPrompt();

  protected abstract List<RealtimeSessionCreateRequest.OutputModality> getOutputModalities();

  protected abstract void onResponse(String eventType, JsonNode event);

  protected void sendMessage(ConversationItemCreateEvent message) {
    WebSocket ws = this.ws.join();
    synchronized (this) {
      try {
        ws.sendText(JACKSON.writeValueAsString(message), true);
        ws.request(1);
        log.warn("WSS sent message: {}", JACKSON.writeValueAsString(message));
        askForResponse();
      } catch (JsonProcessingException e) {
        throw new OrchestrationClientException("Failed to serialize message", e);
      }
    }
  }

  private synchronized void onSocketOpen(WebSocket ws) {
    configureSession(ws);
    configureConversation(ws);
    scheduleHeartbeat(ws);
  }

  private void onText(WebSocket webSocket, CharSequence data) {
    log.warn("WSS received message: {}", data);
    final JsonNode event;
    try {
      event = JACKSON.readTree(data.toString());
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Error parsing JSON response from speech API", e);
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
    SessionUpdateEvent sue =
        SessionUpdateEvent.builder()
            .session(
                ClientSecretCreateParams.Session.ofRealtime(
                        RealtimeSessionCreateRequest.builder()
                            .outputModalities(getOutputModalities())
                            .audio(
                                RealtimeAudioConfig.builder()
                                    .input(
                                        RealtimeAudioConfigInput.builder()
                                            .turnDetection(Optional.empty())
                                            .format(
                                                RealtimeAudioFormats.AudioPcm.builder()
                                                    .type(
                                                        RealtimeAudioFormats.AudioPcm.Type
                                                            .AUDIO_PCM)
                                                    .build())
                                            .build())
                                    .output(
                                        RealtimeAudioConfigOutput.builder()
                                            .format(RealtimeAudioFormats.AudioPcm.builder().build())
                                            .voice(
                                                RealtimeAudioConfigOutput.Voice.UnionMember1.ALLOY)
                                            .build())
                                    .build())
                            .build())
                    .asRealtime())
            .build();
    try {
      ws.sendText(JACKSON.writeValueAsString(sue), true);
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize session request", e);
    }

    ws.request(1);
  }

  private void configureConversation(WebSocket ws) {
    var systemPrompt = getSystemPrompt();
    var systemConversationItem =
        ConversationItemCreateEvent.builder()
            .item(
                ConversationItem.ofRealtimeConversationItemSystemMessage(
                    RealtimeConversationItemSystemMessage.builder()
                        .addContent(
                            RealtimeConversationItemSystemMessage.Content.builder()
                                .text(systemPrompt)
                                .type(RealtimeConversationItemSystemMessage.Content.Type.INPUT_TEXT)
                                .build())
                        .build()))
            .build();

    try {
      var json = JACKSON.writeValueAsString(systemConversationItem);
      ws.sendText(json, true);
      log.warn("WSS conversation config sent: {}", json);
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize message", e);
    }
    ws.request(1);
  }

  private void scheduleHeartbeat(WebSocket ws) {
    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            sendPing(ws);
          }
        };
    heartbeatTimer.scheduleAtFixedRate(task, 0, HEARTBEAT_INTERVAL_MILLIS);
  }
}
