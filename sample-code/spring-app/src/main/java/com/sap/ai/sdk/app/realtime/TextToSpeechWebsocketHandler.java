package com.sap.ai.sdk.app.realtime;

import com.sap.ai.sdk.app.services.OpenAiService;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/** Implements handler (Web Socket messages handling) for text to speech realtime api operation */
@Component
@Slf4j
public class TextToSpeechWebsocketHandler extends BinaryWebSocketHandler {

  private final OpenAiService service;
  private final Map<String, TextInputChannel> channels;

  /**
   * Constructs handler object
   *
   * @param service - handling service
   */
  @Autowired
  public TextToSpeechWebsocketHandler(@Nonnull final OpenAiService service) {
    this.service = service;
    channels = new ConcurrentHashMap<>();
  }

  @Override
  // The channel MUST NOT be closed here, its lifecycle is managed by the WebSocket container (RAII)
  // closing performed in afterConnectionClosed method
  @SuppressWarnings("PMD.CloseResource")
  protected void handleBinaryMessage(
      @Nonnull final WebSocketSession session, @Nonnull final BinaryMessage message) {
    final ByteBuffer payload = message.getPayload();
    final byte[] textBytes = payload.array();
    final TextInputChannel channel =
        channels.computeIfAbsent(
            session.getId(),
            sessionId ->
                service.textToSpeech(
                    (rawBytesChunk, isLast) -> {
                      try {
                        session.sendMessage(new BinaryMessage(rawBytesChunk, isLast));
                      } catch (final IOException e) {
                        log.error("failed to send text message to realtime api", e);
                      }
                    }));
    channel.sendText(new String(textBytes));
  }

  @Override
  public void afterConnectionClosed(
      @Nonnull final WebSocketSession session, @Nonnull final CloseStatus status) throws Exception {
    channels.computeIfPresent(
        session.getId(),
        (sessionId, inputChannel) -> {
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
