package com.sap.ai.sdk.app;

import com.sap.ai.sdk.app.realtime.TextToSpeechWebsocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final TextToSpeechWebsocketHandler textToSpeech;

    public WebsocketConfig(TextToSpeechWebsocketHandler textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textToSpeech, "/text-to-speech").setAllowedOrigins("*");
    }
}
