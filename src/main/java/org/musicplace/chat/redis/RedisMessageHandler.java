package org.musicplace.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Log4j2
@RequiredArgsConstructor
class RedisMessageHandler implements MessageListener {
    private final WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Redis 메시지 수신
     * @param message 메시지
     * @param pattern 패턴
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            WebSocketMessage webSocketMessage = objectMapper.readValue(message.getBody(), WebSocketMessage.class);
            if(session.isOpen() && !webSocketMessage.getPayload().getUsername().equals(session.getAttributes().get("username"))){
                session.sendMessage(new TextMessage(new String(message.getBody())));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}