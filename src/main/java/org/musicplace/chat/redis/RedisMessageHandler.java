package org.musicplace.chat.redis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.chat.websocket.WebSocketMessageType;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Log4j2
@RequiredArgsConstructor
class RedisMessageHandler implements MessageListener {
    private final WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // JSON 데이터 파싱 및 메시지 타입 확인
            JsonNode jsonNode = objectMapper.readTree(message.getBody());
            WebSocketMessageType messageType = WebSocketMessageType.valueOf(jsonNode.get("type").asText());

            // WebSocketMessage 객체 생성
            WebSocketMessage webSocketMessage = objectMapper.treeToValue(jsonNode, WebSocketMessage.class);

            String messageUsername = webSocketMessage.getPayload().getUsername();
            String sessionUsername = (String) session.getAttributes().get("username");

            // 현재 세션의 사용자와 메시지 사용자 비교
            if (session.isOpen() && !messageUsername.equals(sessionUsername)) {
                session.sendMessage(new TextMessage(new String(message.getBody())));
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
