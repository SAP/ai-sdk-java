package com.sap.ai.sdk.app.realtime;

import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TextToSpeechWebsocketHandler extends BinaryWebSocketHandler {

    private final OpenAiService service;
    private final Map<String, TextInputChannel> channels;

    @Autowired
    public TextToSpeechWebsocketHandler(OpenAiService service) {
        this.service = service;
        channels = new ConcurrentHashMap<>();
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        ByteBuffer payload = message.getPayload();
        byte[] textBytes = payload.array();
        TextInputChannel channel = channels.computeIfAbsent(
                session.getId(),
                sessionId -> service.textToSpeech((rawBytesChunk, isLast) -> {
                    try {
                        session.sendMessage(new BinaryMessage(rawBytesChunk, isLast));
                    } catch (IOException e) {
                        log.error("failed to send text message to realtime api", e);
                    }
        }));
        channel.sendText(new String(textBytes));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        channels.computeIfPresent(session.getId(), (sessionId, inputChannel) -> {
            try {
                inputChannel.close();
            } catch (Exception e) {
                log.warn("failed to close input channel for session {}", sessionId, e);
            }
            return null;
        });
        super.afterConnectionClosed(session, status);
    }

}
