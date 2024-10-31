package org.musicplace.chat.config;

import org.musicplace.streaming.handler.StreamingWebSocketHandler;
import org.musicplace.chat.handler.WebSocketChatHandler;
import org.musicplace.chat.interceptor.WebSocketAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketChatHandler webSocketChatHandler;
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;
    private final StreamingWebSocketHandler streamingWebSocketHandler;

    /**
     * 웹소켓 연결을 위한 설정
     * 웹소켓 연결 EndPoint: ws://localhost:8080/chats
     * 에 연결시 동작할 핸들러는 webSocketChatHandler
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 채팅 핸들러 설정
        registry.addHandler(webSocketChatHandler, "/chats")
                .addInterceptors(webSocketAuthInterceptor)
                .setAllowedOrigins("*");

        // 스트리밍 핸들러 설정
        registry.addHandler(streamingWebSocketHandler, "/streaming")
                .addInterceptors(webSocketAuthInterceptor)
                .setAllowedOrigins("*");
    }
}