package com.sap.ai.sdk.app;

import com.sap.ai.sdk.app.realtime.SpeechToSpeechWebsocketHandler;
import com.sap.ai.sdk.app.realtime.TextToSpeechWebsocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final TextToSpeechWebsocketHandler textToSpeech;
    private final SpeechToSpeechWebsocketHandler speechToSpeech;

    public WebsocketConfig(TextToSpeechWebsocketHandler textToSpeech, SpeechToSpeechWebsocketHandler speechToSpeech) {
        this.textToSpeech = textToSpeech;
        this.speechToSpeech = speechToSpeech;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textToSpeech, "/text-to-speech").setAllowedOrigins("*");
        registry.addHandler(speechToSpeech, "/speech-to-speech").setAllowedOrigins("*");
    }
}
