package org.musicplace.streaming.config;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.handler.StreamingWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final StreamingWebSocketHandler streamingWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(streamingWebSocketHandler, "/ws/streaming").setAllowedOrigins("*");
    }
}