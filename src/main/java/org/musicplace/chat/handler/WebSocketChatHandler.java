package org.musicplace.chat.handler;

import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.chatRoom.ChatRoom;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.streaming.dto.StreamingDto; // StreamingDto 임포트
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
        WebSocketMessage webSocketMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

        // Debug 로그 추가
        log.info("받은 메세지: {}", message.getPayload()); // 받은 메시지 로그

        switch (webSocketMessage.getType().getValue()) {
            case "ENTER" -> enterChatRoom((ChatDto) webSocketMessage.getPayload(), session);
            case "TALK" -> sendMessage(username, (ChatDto) webSocketMessage.getPayload());
            case "STREAM" -> handleStreamMessage(webSocketMessage.getPayload(), session); // 스트리밍 메시지 처리 추가
        }
    }

    /**
     * 메시지 전송
     * @param chatDto ChatDto
     */
    private void sendMessage(String username, ChatDto chatDto) {
        log.info("채팅 메세지 전송 : " + chatDto.toString());
        chatRoom.sendMessage(chatDto);
    }

    /**
     * 채팅방 입장
     * @param chatDto ChatDto
     * @param session 웹소켓 세션
     */
    private void enterChatRoom(ChatDto chatDto, WebSocketSession session) {
        log.info("채팅방 입장 : " + chatDto.toString());
        chatRoom.enter(chatDto, session);
    }

    /**
     * 스트리밍 메시지 처리
     * @param payload 메시지의 payload
     * @param session 웹소켓 세션
     */
    private void handleStreamMessage(Object payload, WebSocketSession session) {
        StreamingDto streamingDto = objectMapper.convertValue(payload, StreamingDto.class);
        log.info("스트리밍 메세지 처리 : " + streamingDto.toString());

        // 스트리밍 관련 로직을 추가 (예: 스트리밍 시작, URL 전송 등)
        // 이곳에서 스트리밍 정보를 다른 클라이언트에게 전송하는 로직을 작성하세요.
    }
}