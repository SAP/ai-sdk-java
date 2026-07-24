package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.ConversationItem;
import com.openai.models.realtime.ConversationItemCreateEvent;
import com.openai.models.realtime.RealtimeConversationItemSystemMessage;
import com.openai.models.realtime.ResponseCreateEvent;
import com.openai.models.realtime.SessionUpdateEvent;
import com.sap.ai.sdk.core.common.ClientException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
abstract class WSOpenAiRealtimeClient implements AutoCloseable {

  private static final int SUCCESS_FINISH_WSS_CODE = 1000;
  private static final ObjectMapper JACKSON =
      new ObjectMapper().setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

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

  public WSOpenAiRealtimeClient(
      @Nonnull final String url,
      @Nonnull final Map<String, String> httpHeaders,
      @Nonnull final Set<String> handleMessageTypes) {
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
    WebSocket ws;
    try {
      ws = this.ws.join();
    } catch (final CompletionException e) {
      throw new ClientException("failed to establish web socket connection", e);
    }
    synchronized (this) {
      try {
        ws.sendText(JACKSON.writeValueAsString(ResponseCreateEvent.builder().build()), true);
      } catch (final JsonProcessingException e) {
        throw new RuntimeException("Failed to serialize ask for response", e);
      }
      ws.request(1);
    }
  }

  @Override
  public void close() {
    WebSocket ws;
    try {
      ws = this.ws.join();
    } catch (final CompletionException e) {
      throw new ClientException("failed to establish web socket connection", e);
    }
    synchronized (this) {
      heartbeatTimer.cancel();
      try {
        ws.sendClose(SUCCESS_FINISH_WSS_CODE, "done").join();
      } catch (final Exception e) {
        log.error("Error while closing WebSocket", e);
      }
      // this.client.close(); // exists only since java 21
    }
  }

  @Nonnull
  protected abstract String getSystemPrompt();

  protected abstract void onResponse(
      @Nonnull final String eventType, @Nonnull final JsonNode event);

  @Nonnull
  protected abstract SessionUpdateEvent sessionConfiguration();

  protected void sendMessage(@Nonnull final Object message) {
    WebSocket ws;
    try {
      ws = this.ws.join();
    } catch (CompletionException e) {
      throw new ClientException("failed to establish web socket connection", e);
    }
    synchronized (this) {
      try {
        ws.sendText(JACKSON.writeValueAsString(message), true);
        ws.request(1);
      } catch (final JsonProcessingException e) {
        throw new ClientException("Failed to serialize message", e);
      }
    }
  }

  private synchronized void onSocketOpen(@Nonnull final WebSocket ws) {
    configureSession(ws);
    configureConversation(ws);
    scheduleHeartbeat(ws);
  }

  private void onText(@Nonnull final WebSocket webSocket, @Nonnull final CharSequence data) {
    final JsonNode event;
    try {
      event = JACKSON.readTree(data.toString());
    } catch (final JsonProcessingException e) {
      throw new ClientException("Error parsing JSON response from speech API", e);
    }
    var eventType = event.get("type").asText();
    if (handleMessageTypes.contains(eventType)) {
      onResponse(eventType, event);
    } else {
      log.trace("Unhandled event type: {}", eventType);
    }

    webSocket.request(1);
  }

  private synchronized void sendPing(@Nonnull final WebSocket ws) {
    if (ws.isInputClosed()) {
      return;
    }
    ws.sendPing(ByteBuffer.wrap("ping".getBytes())).join();
    ws.request(1);
  }

  private void configureSession(@Nonnull final WebSocket ws) {
    final SessionUpdateEvent sue = sessionConfiguration();
    try {
      ws.sendText(JACKSON.writeValueAsString(sue), true);
    } catch (final JsonProcessingException e) {
      throw new ClientException("Failed to serialize session request", e);
    }

    ws.request(1);
  }

  private void configureConversation(@Nonnull final WebSocket ws) {
    final var systemPrompt = getSystemPrompt();
    if (systemPrompt.isEmpty()) {
      return;
    }
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
    } catch (final JsonProcessingException e) {
      throw new ClientException("Failed to serialize message", e);
    }
    ws.request(1);
  }

  private void scheduleHeartbeat(@Nonnull final WebSocket ws) {
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
