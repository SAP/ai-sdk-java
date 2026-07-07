package com.sap.ai.sdk.orchestration;

import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BufferedWebSocketListener implements WebSocket.Listener {

  private final Consumer<WebSocket> onOpen;
  private final BiConsumer<WebSocket, CharSequence> onText;
  private final StringBuilder buffer;

  public BufferedWebSocketListener(
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
  public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
    buffer.append(data);
    webSocket.request(1);
    if (last) {
      var completeMessage = buffer.toString();
      buffer.setLength(0);
      this.onText.accept(webSocket, completeMessage);
    }

    return CompletableFuture.completedStage(null);
  }
}
