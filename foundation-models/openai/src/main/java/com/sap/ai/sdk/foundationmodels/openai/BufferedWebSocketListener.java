package com.sap.ai.sdk.foundationmodels.openai;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BufferedWebSocketListener implements WebSocket.Listener {

  private final Consumer<WebSocket> onOpen;
  private final BiConsumer<WebSocket, CharSequence> onText;
  private final StringBuilder buffer;

  BufferedWebSocketListener(
      Consumer<WebSocket> onOpen, BiConsumer<WebSocket, CharSequence> onText) {
    this.onOpen = onOpen;
    this.onText = onText;
    this.buffer = new StringBuilder(1024 * 1024);
  }

  @Override
  public void onOpen(WebSocket webSocket) {
    this.onOpen.accept(webSocket);
    webSocket.request(1);
  }

  @Override
  public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean isLast) {
    buffer.append(data);
    webSocket.request(1);
    if (isLast) {
      var completeMessage = buffer.toString();
      buffer.setLength(0);
      this.onText.accept(webSocket, completeMessage);
    }

    return CompletableFuture.completedStage(null);
  }

  @Override
  public void onError(WebSocket webSocket, Throwable error) {
    log.error("Websocket error occurred during realtime communication", error);
  }

  @Override
  public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean isLast) {
    log.warn("Received unexpected binary bytes for WebSocket connection");
    return CompletableFuture.completedStage(null);
  }
}
