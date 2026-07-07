package com.sap.ai.sdk.orchestration;

import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WebsocketListenerGlue implements WebSocket.Listener {

    private final Consumer<WebSocket> onOpen;
    private final BiConsumer<WebSocket, CharSequence> onText;

    public WebsocketListenerGlue(Consumer<WebSocket> onOpen, BiConsumer<WebSocket, CharSequence> onText) {
        this.onOpen = onOpen;
        this.onText = onText;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        this.onOpen.accept(webSocket);
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        this.onText.accept(webSocket, data);
        webSocket.request(1);
        return CompletableFuture.completedStage(null);
    }
}
