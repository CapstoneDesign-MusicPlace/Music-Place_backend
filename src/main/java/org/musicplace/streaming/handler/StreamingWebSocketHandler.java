package org.musicplace.streaming.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.musicplace.Youtube.service.YoutubeService;
import org.musicplace.chat.dto.BaseMessageDto;
import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.chat.websocket.WebSocketMessageType;
import org.musicplace.streaming.dto.StreamingDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final YoutubeService youtubeService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            WebSocketMessage webSocketMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

            switch (webSocketMessage.getType()) {
                case STREAM -> {
                    StreamingDto streamingDto = objectMapper.convertValue(webSocketMessage.getPayload(), StreamingDto.class);
                    handleStreamMessage(session, streamingDto);
                }
                case TALK -> {
                    ChatDto chatDto = objectMapper.convertValue(webSocketMessage.getPayload(), ChatDto.class);
                    handleChatMessage(session, chatDto);
                }
                default -> log.warn("지원하지 않는 메시지 타입: {}", webSocketMessage.getType());
            }
        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생: {}", e.getMessage());
        }
    }

    private void handleStreamMessage(WebSocketSession session, StreamingDto streamingDto) {
        String videoUrl = youtubeService.getVideoUrl(streamingDto.getVideoUrl());
        StreamingDto updatedStreamingDto = StreamingDto.builder()
                .roomId(streamingDto.getRoomId())
                .username(streamingDto.getUsername())
                .message(streamingDto.getMessage())
                .videoUrl(videoUrl)
                .build();

        broadcastMessage(session, updatedStreamingDto);
    }

    private void handleChatMessage(WebSocketSession session, ChatDto chatDto) {
        broadcastMessage(session, chatDto);
    }

    private void broadcastMessage(WebSocketSession session, BaseMessageDto messageDto) {
        sessions.values().forEach(sess -> {
            if (sess.isOpen() && !sess.getId().equals(session.getId())) {
                try {
                    sess.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDto)));
                } catch (Exception e) {
                    log.error("메시지 전송 중 오류 발생: {}", e.getMessage());
                }
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        log.info("새로운 세션 연결: Session ID = {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        log.info("세션 종료: Session ID = {}", session.getId());
    }
}