package com.sap.ai.sdk.foundationmodels.openai;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BufferedWebSocketListener implements WebSocket.Listener {

  private final Consumer<WebSocket> onOpen;
  private final BiConsumer<WebSocket, CharSequence> onText;
  private final StringBuilder buffer;

  BufferedWebSocketListener(
      @Nonnull final Consumer<WebSocket> onOpen,
      @Nonnull final BiConsumer<WebSocket, CharSequence> onText) {
    this.onOpen = onOpen;
    this.onText = onText;
    this.buffer = new StringBuilder(128 * 1024);
  }

  @Override
  public void onOpen(@Nonnull final WebSocket webSocket) {
    this.onOpen.accept(webSocket);
    webSocket.request(1);
  }

  @Override
  @Nonnull
  public CompletionStage<?> onText(
      final @Nonnull WebSocket webSocket, final @Nonnull CharSequence data, final boolean isLast) {
    buffer.append(data);
    webSocket.request(1);
    if (isLast) {
      final var completeMessage = buffer.toString();
      buffer.setLength(0);
      this.onText.accept(webSocket, completeMessage);
    }

    return CompletableFuture.completedStage(null);
  }

  @Override
  public void onError(@Nonnull final WebSocket webSocket, @Nonnull final Throwable error) {
    log.error("Websocket error occurred during realtime communication", error);
  }

  @Override
  @Nonnull
  public CompletionStage<?> onBinary(
      @Nonnull final WebSocket webSocket, @Nonnull final ByteBuffer data, final boolean isLast) {
    log.warn("Received unexpected binary bytes for WebSocket connection");
    return CompletableFuture.completedStage(null);
  }
}
