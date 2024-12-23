package org.musicplace.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.chat.chatRoom.ChatRoom;

import org.musicplace.chat.websocket.WebSocketMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ChatRoom chatRoom;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String nickname = (String) session.getAttributes().get("username");
        WebSocketMessage webSocketMessage = objectMapper.readValue(message.getPayload(), WebSocketMessage.class);

        log.info("Received message from {}: {}", nickname, message.getPayload());

        switch (webSocketMessage.getType()) {
            case ENTER -> chatRoom.enter(webSocketMessage.getPayload(), session);
            case TALK -> chatRoom.sendMessage(webSocketMessage.getPayload(), session);
            case EXIT -> chatRoom.exit(webSocketMessage.getPayload(), session);
        }
    }



}
