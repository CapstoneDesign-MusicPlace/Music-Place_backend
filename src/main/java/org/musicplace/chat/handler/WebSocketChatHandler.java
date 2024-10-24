package org.musicplace.chat.handler;

import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.chatRoom.ChatRoom;
import org.musicplace.chat.websocket.WebSocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ChatRoom chatRoom;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        String username = (String) session.getAttributes().get("username");
        WebSocketMessage webSocketMessage = parseWebSocketMessage(message.getPayload());

        // Debug 로그 추가
        log.info("Received message: {}", message.getPayload()); // 받은 메시지 로그

        switch (webSocketMessage.getType().getValue()) {
            case "ENTER" -> enterChatRoom(webSocketMessage.getPayload(), session);
            case "TALK" -> sendMessage(username, webSocketMessage.getPayload());
            default -> log.warn("Unexpected message type: {}", webSocketMessage.getType());
        }
    }

    /**
     * 메시지 전송
     * @param chatDto ChatDto
     */
    private void sendMessage(String username, ChatDto chatDto) {
        log.info("send chatDto : " + chatDto.toString());
        chatRoom.sendMessage(chatDto);
    }

    /**
     * 채팅방 입장
     * @param chatDto ChatDto
     * @param session 웹소켓 세션
     */
    private void enterChatRoom(ChatDto chatDto, WebSocketSession session) {
        log.info("enter chatDto : " + chatDto.toString());
        chatRoom.enter(chatDto, session);
    }

    /**
     * WebSocketMessage 파싱
     * @param payload JSON 형태의 메시지
     * @return WebSocketMessage 객체
     */
    private WebSocketMessage parseWebSocketMessage(String payload) throws JsonProcessingException {
        // JSON payload를 WebSocketMessage 객체로 변환하는 로직
        return objectMapper.readValue(payload, WebSocketMessage.class);
    }
}