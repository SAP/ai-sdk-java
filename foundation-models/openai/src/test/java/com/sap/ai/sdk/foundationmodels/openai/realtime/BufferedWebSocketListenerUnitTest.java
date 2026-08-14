package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BufferedWebSocketListenerUnitTest {

  private WebSocket webSocketMock;
  private List<WebSocket> openCallbacks;
  private List<String> textCallbacks;

  @BeforeEach
  void setUp() {
    webSocketMock = mock(WebSocket.class);
    openCallbacks = new ArrayList<>();
    textCallbacks = new ArrayList<>();
  }

  private BufferedWebSocketListener build() {
    return new BufferedWebSocketListener(
        openCallbacks::add, (ws, data) -> textCallbacks.add(data.toString()));
  }

  @Test
  void onOpenInvokesConsumerWithWebSocket() {
    build().onOpen(webSocketMock);

    assertThat(openCallbacks).containsExactly(webSocketMock);
  }

  @Test
  void onOpenRequestsNextMessage() {
    build().onOpen(webSocketMock);

    verify(webSocketMock).request(1L);
  }

  @Test
  void onTextInvokesConsumerWhenLastFrameReceived() {
    build().onText(webSocketMock, "hello", true);

    assertThat(textCallbacks).containsExactly("hello");
  }

  @Test
  void onTextDoesNotInvokeConsumerForPartialFrame() {
    build().onText(webSocketMock, "hel", false);

    assertThat(textCallbacks).isEmpty();
  }

  @Test
  void onTextRequestsNextMessageForPartialFrame() {
    build().onText(webSocketMock, "hel", false);

    verify(webSocketMock).request(1L);
  }

  @Test
  void onTextReturnedFutureIsAlreadyCompleted() {
    final var future = build().onText(webSocketMock, "hello", true);

    assertThat(future.toCompletableFuture()).isDone();
  }

  @Test
  void onTextBuffersPartialsAndDeliversOnLastFrame() {
    final var listener = build();
    listener.onText(webSocketMock, "foo", false);
    listener.onText(webSocketMock, "bar", false);
    listener.onText(webSocketMock, "baz", true);

    assertThat(textCallbacks).containsExactly("foobarbaz");
  }

  @Test
  void onTextResetsBufferAfterCompleteMessage() {
    final var listener = build();
    listener.onText(webSocketMock, "first", true);
    listener.onText(webSocketMock, "second", true);

    assertThat(textCallbacks).containsExactly("first", "second");
  }

  @Test
  void onTextBufferDoesNotLeakAcrossMessages() {
    final var listener = build();
    listener.onText(webSocketMock, "part1", false);
    listener.onText(webSocketMock, "part2", true); // completes first message
    listener.onText(webSocketMock, "part3", true); // second message starts clean

    assertThat(textCallbacks).containsExactly("part1part2", "part3");
  }

  @Test
  void onErrorDoesNotThrow() {
    assertThatNoException()
        .isThrownBy(() -> build().onError(webSocketMock, new RuntimeException("boom")));
  }

  @Test
  void onErrorDoesNotInvokeTextConsumer() {
    build().onError(webSocketMock, new RuntimeException("boom"));

    assertThat(textCallbacks).isEmpty();
  }

  @Test
  void onBinaryDoesNotInvokeTextConsumer() {
    build().onBinary(webSocketMock, ByteBuffer.wrap(new byte[] {0x01}), true);

    assertThat(textCallbacks).isEmpty();
  }

  @Test
  void onBinaryReturnedFutureIsAlreadyCompleted() {
    final var future = build().onBinary(webSocketMock, ByteBuffer.wrap(new byte[] {0x01}), true);

    assertThat(future.toCompletableFuture()).isDone();
  }

  @Test
  void onBinaryDoesNotRequestNextMessage() {
    build().onBinary(webSocketMock, ByteBuffer.wrap(new byte[] {0x01}), true);

    verify(webSocketMock, never()).request(anyLong());
  }
}
