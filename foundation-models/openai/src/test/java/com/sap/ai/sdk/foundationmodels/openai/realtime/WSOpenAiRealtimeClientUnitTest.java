package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.models.realtime.RealtimeAudioConfig;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioConfigOutput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeAudioInputTurnDetection;
import com.openai.models.realtime.RealtimeSessionCreateRequest;
import com.openai.models.realtime.SessionUpdateEvent;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateParams;
import com.sap.ai.sdk.core.common.ClientException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class WSOpenAiRealtimeClientUnitTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private List<String> onResponseEventTypes;
  private List<JsonNode> onResponseEvents;
  private SessionUpdateEvent expectedSessionUpdateEvent;
  private WebSocket webSocketMock;

  /**
   * Concrete testable subclass. It also owns a {@link BufferedWebSocketListener} wired to the same
   * text-routing logic (including the handleMessageTypes filter) so we can simulate incoming
   * WebSocket frames without a real server.
   */
  private class TestableClient extends WSOpenAiRealtimeClient {

    private final Set<String> handledTypes;
    private final String systemPrompt;

    /**
     * The listener that mirrors what the production URL-based constructor wires up, letting tests
     * inject incoming text frames directly.
     */
    final BufferedWebSocketListener inboundListener;

    TestableClient(
        final CompletableFuture<WebSocket> ws,
        final Timer timer,
        final Set<String> handleMessageTypes,
        final String systemPrompt) {
      super(mock(HttpClient.class), ws, timer, handleMessageTypes);
      this.handledTypes = handleMessageTypes;
      this.systemPrompt = systemPrompt;
      this.inboundListener =
          new BufferedWebSocketListener(
              ignored -> {}, // onSocketOpen — not under test here
              this::dispatchText);
    }

    /**
     * Replicates {@code WSOpenAiRealtimeClient.onText}: parse JSON, filter by type, call {@link
     * #onResponse}. This is intentionally a thin copy so we can drive routing tests from the test
     * package without coupling to the private method.
     */
    private void dispatchText(final WebSocket ws, final CharSequence data) {
      final JsonNode event;
      try {
        event = MAPPER.readTree(data.toString());
      } catch (final Exception e) {
        throw new ClientException("Error parsing JSON response from speech API", e);
      }
      final var eventType = event.get("type").asText();
      if (handledTypes.contains(eventType)) {
        onResponse(eventType, event);
      }
      ws.request(1);
    }

    @Override
    protected synchronized void onResponse(
        @Nonnull final String eventType, @Nonnull final JsonNode event) {
      onResponseEventTypes.add(eventType);
      onResponseEvents.add(event);
    }

    @Override
    protected synchronized @Nonnull SessionUpdateEvent sessionConfiguration() {
      return expectedSessionUpdateEvent;
    }

    @Override
    @Nonnull
    protected Optional<String> getSystemPrompt() {
      return systemPrompt.isEmpty() ? Optional.empty() : Optional.of(systemPrompt);
    }

    // Expose protected methods for direct invocation in tests
    void invokeOnText(final WebSocket ws, final CharSequence data) {
      onText(ws, data);
    }

    void invokeSendPing(final WebSocket ws) {
      sendPing(ws);
    }
  }

  @BeforeEach
  void setUp() {
    expectedSessionUpdateEvent = buildSessionUpdateEvent();
    onResponseEventTypes = new ArrayList<>();
    onResponseEvents = new ArrayList<>();

    webSocketMock = mock(WebSocket.class);
    when(webSocketMock.sendText(any(), anyBoolean()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
    when(webSocketMock.sendClose(anyInt(), anyString()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
    when(webSocketMock.sendPing(any()))
        .thenReturn(CompletableFuture.completedFuture(webSocketMock));
    when(webSocketMock.isInputClosed()).thenReturn(false);
  }

  private TestableClient buildInstance(final Set<String> handleMessageTypes) {
    return new TestableClient(
        CompletableFuture.completedFuture(webSocketMock),
        mock(Timer.class),
        handleMessageTypes,
        "");
  }

  private TestableClient buildInstance(final Timer timer, final String systemPrompt) {
    return new TestableClient(
        CompletableFuture.completedFuture(webSocketMock), timer, Set.of(), systemPrompt);
  }

  private TestableClient buildBrokenInstance() {
    final var failedFuture = new CompletableFuture<WebSocket>();
    failedFuture.completeExceptionally(new RuntimeException("connection refused"));
    return new TestableClient(failedFuture, mock(Timer.class), Set.of(), "");
  }

  @Test
  void sendMessageSerializesObjectAsJsonAndSendsOverWebSocket() {
    buildInstance(Set.of()).sendMessage(expectedSessionUpdateEvent);

    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), eq(true));
    final var lastJson =
        parseJson(captor.getAllValues().get(captor.getAllValues().size() - 1).toString());
    assertThat(lastJson.get("type").asText()).isEqualTo("session.update");
    verify(webSocketMock, atLeastOnce()).request(anyLong());
  }

  @Test
  void sendMessageThrowsClientExceptionWhenConnectionFailed() {
    assertThatThrownBy(() -> buildBrokenInstance().sendMessage("anything"))
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Failed to establish web socket connection");
  }

  @Test
  void askForResponseSendsResponseCreateEvent() {
    buildInstance(Set.of()).askForResponse();

    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), eq(true));
    final var sentResponseCreate =
        captor.getAllValues().stream()
            .map(cs -> parseJson(cs.toString()))
            .anyMatch(n -> "response.create".equals(n.get("type").asText()));
    assertThat(sentResponseCreate).isTrue();
    verify(webSocketMock, atLeastOnce()).request(anyLong());
  }

  @Test
  void askForResponseThrowsClientExceptionWhenConnectionFailed() {
    assertThatThrownBy(buildBrokenInstance()::askForResponse)
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Failed to establish web socket connection");
  }

  @Test
  void closeSendsCloseFrameWithNormalClosureCode() {
    buildInstance(Set.of()).close();

    verify(webSocketMock).sendClose(eq(1000), anyString());
  }

  @Test
  void closeThrowsClientExceptionWhenConnectionFailed() {
    assertThatThrownBy(buildBrokenInstance()::close)
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Failed to establish web socket connection");
  }

  @Test
  void incomingMessageWithRegisteredTypeIsRoutedToOnResponse() {
    final var instance = buildInstance(Set.of("response.audio.delta", "session.created"));

    instance.inboundListener.onText(
        webSocketMock, "{\"type\":\"response.audio.delta\",\"delta\":\"abc\"}", true);

    assertThat(onResponseEventTypes).containsExactly("response.audio.delta");
    assertThat(onResponseEvents).hasSize(1);
    assertThat(onResponseEvents.get(0).get("delta").asText()).isEqualTo("abc");
  }

  @Test
  void incomingMessageWithUnregisteredTypeIsIgnored() {
    final var instance = buildInstance(Set.of("session.created"));

    instance.inboundListener.onText(webSocketMock, "{\"type\":\"some.other.event\"}", true);

    assertThat(onResponseEventTypes).isEmpty();
    assertThat(onResponseEvents).isEmpty();
  }

  @Test
  void multipleMatchingIncomingMessagesAreAllDelivered() {
    final var instance = buildInstance(Set.of("response.audio.delta"));

    instance.inboundListener.onText(
        webSocketMock, "{\"type\":\"response.audio.delta\",\"delta\":\"chunk1\"}", true);
    instance.inboundListener.onText(
        webSocketMock, "{\"type\":\"response.audio.delta\",\"delta\":\"chunk2\"}", true);

    assertThat(onResponseEventTypes)
        .containsExactly("response.audio.delta", "response.audio.delta");
    assertThat(onResponseEvents.get(0).get("delta").asText()).isEqualTo("chunk1");
    assertThat(onResponseEvents.get(1).get("delta").asText()).isEqualTo("chunk2");
  }

  @Test
  void bufferedListenerAssemblesPartialFramesBeforeDelivery() {
    final var instance = buildInstance(Set.of("session.created"));

    instance.inboundListener.onText(webSocketMock, "{\"type\":\"session", false);
    assertThat(onResponseEventTypes).isEmpty();

    instance.inboundListener.onText(webSocketMock, ".created\",\"id\":\"xyz\"}", true);
    assertThat(onResponseEventTypes).containsExactly("session.created");
  }

  @Test
  void incomingMalformedJsonThrowsClientException() {
    final var instance = buildInstance(Set.of());

    assertThatThrownBy(
            () -> instance.inboundListener.onText(webSocketMock, "not-valid-json{{{", true))
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Error parsing JSON response from speech API");
  }

  @Test
  void onSocketOpenSendsSessionConfigurationToWebSocket() {
    final var timerMock = mock(Timer.class);
    final var instance = buildInstance(timerMock, "");

    instance.onSocketOpen(webSocketMock);

    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), eq(true));
    final var sentTypes =
        captor.getAllValues().stream()
            .map(cs -> parseJson(cs.toString()).get("type").asText())
            .toList();
    assertThat(sentTypes).contains("session.update");
  }

  @Test
  void onSocketOpenSkipsConversationItemWhenSystemPromptIsEmpty() {
    final var timerMock = mock(Timer.class);
    final var instance = buildInstance(timerMock, "");

    instance.onSocketOpen(webSocketMock);

    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), eq(true));
    final var sentTypes =
        captor.getAllValues().stream()
            .map(cs -> parseJson(cs.toString()).get("type").asText())
            .toList();
    assertThat(sentTypes).doesNotContain("conversation.item.create");
  }

  @Test
  void onSocketOpenSendsSystemPromptAsConversationItemWhenPresent() {
    final var timerMock = mock(Timer.class);
    final var systemPrompt = "You are a helpful assistant.";
    final var instance = buildInstance(timerMock, systemPrompt);

    instance.onSocketOpen(webSocketMock);

    final var captor = ArgumentCaptor.forClass(CharSequence.class);
    verify(webSocketMock, atLeastOnce()).sendText(captor.capture(), eq(true));
    final var conversationItemCreate =
        captor.getAllValues().stream()
            .map(cs -> parseJson(cs.toString()))
            .filter(n -> "conversation.item.create".equals(n.get("type").asText()))
            .findFirst();
    assertThat(conversationItemCreate).isPresent();
    final var contentText = conversationItemCreate.get().at("/item/content/0/text").asText();
    assertThat(contentText).isEqualTo(systemPrompt);
  }

  @Test
  void onSocketOpenSchedulesHeartbeatTimer() {
    final var timerMock = mock(Timer.class);
    final var instance = buildInstance(timerMock, "");

    instance.onSocketOpen(webSocketMock);

    verify(timerMock).scheduleAtFixedRate(any(java.util.TimerTask.class), eq(0L), eq(4500L));
  }

  @Test
  void onTextRoutesRegisteredEventTypeToOnResponse() {
    final var instance = buildInstance(Set.of("session.created"));

    instance.invokeOnText(webSocketMock, "{\"type\":\"session.created\",\"id\":\"xyz\"}");

    assertThat(onResponseEventTypes).containsExactly("session.created");
    assertThat(onResponseEvents.get(0).get("id").asText()).isEqualTo("xyz");
  }

  @Test
  void onTextDoesNotCallOnResponseForUnregisteredType() {
    final var instance = buildInstance(Set.of("session.created"));

    instance.invokeOnText(webSocketMock, "{\"type\":\"response.audio.delta\"}");

    assertThat(onResponseEventTypes).isEmpty();
  }

  @Test
  void onTextAlwaysRequestsNextMessageRegardlessOfType() {
    final var instance = buildInstance(Set.of());

    instance.invokeOnText(webSocketMock, "{\"type\":\"any.event\"}");

    verify(webSocketMock).request(1L);
  }

  @Test
  void onTextThrowsClientExceptionForMalformedJson() {
    final var instance = buildInstance(Set.of());

    assertThatThrownBy(() -> instance.invokeOnText(webSocketMock, "not-json{{"))
        .isInstanceOf(ClientException.class)
        .hasMessageContaining("Error parsing JSON response from speech API");
  }

  @Test
  void sendPingSendsPingBytesAndRequestsNextMessageWhenInputOpen() {
    when(webSocketMock.isInputClosed()).thenReturn(false);
    final var instance = buildInstance(Set.of());

    instance.invokeSendPing(webSocketMock);

    final var captor = ArgumentCaptor.forClass(java.nio.ByteBuffer.class);
    verify(webSocketMock).sendPing(captor.capture());
    assertThat(new String(captor.getValue().array(), java.nio.charset.StandardCharsets.UTF_8))
        .isEqualTo("ping");
    verify(webSocketMock).request(1L);
  }

  @Test
  void sendPingDoesNothingWhenInputIsClosed() {
    when(webSocketMock.isInputClosed()).thenReturn(true);
    final var instance = buildInstance(Set.of());

    instance.invokeSendPing(webSocketMock);

    verify(webSocketMock, never()).sendPing(any());
    verify(webSocketMock, never()).request(anyLong());
  }

  @Test
  void getSystemPromptReturnsEmptyByDefault() {
    assertThat(buildInstance(Set.of()).getSystemPrompt()).isEqualTo(Optional.empty());
  }

  @Test
  void sessionConfigurationReturnsTheConfiguredEvent() {
    assertThat(buildInstance(Set.of()).sessionConfiguration()).isSameAs(expectedSessionUpdateEvent);
  }

  private JsonNode parseJson(final String json) {
    try {
      return MAPPER.readTree(json);
    } catch (final Exception e) {
      throw new RuntimeException("Failed to parse JSON in test: " + json, e);
    }
  }

  private SessionUpdateEvent buildSessionUpdateEvent() {
    final var input =
        RealtimeAudioConfigInput.builder()
            .turnDetection(
                RealtimeAudioInputTurnDetection.ofSemanticVad(
                    RealtimeAudioInputTurnDetection.SemanticVad.builder().build()))
            .format(
                RealtimeAudioFormats.AudioPcm.builder()
                    .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                    .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                    .build())
            .build();

    final var output =
        RealtimeAudioConfigOutput.builder()
            .format(
                RealtimeAudioFormats.AudioPcm.builder()
                    .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                    .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                    .build())
            .voice(UUID.randomUUID().toString())
            .build();

    return SessionUpdateEvent.builder()
        .session(
            ClientSecretCreateParams.Session.ofRealtime(
                    RealtimeSessionCreateRequest.builder()
                        .outputModalities(
                            Arrays.asList(
                                RealtimeSessionCreateRequest.OutputModality.AUDIO,
                                RealtimeSessionCreateRequest.OutputModality.TEXT))
                        .audio(RealtimeAudioConfig.builder().input(input).output(output).build())
                        .build())
                .asRealtime())
        .build();
  }
}
