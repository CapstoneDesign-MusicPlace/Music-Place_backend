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

    /**
     * Redis 메시지 수신
     * @param message 메시지
     * @param pattern 패턴
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // JSON 데이터를 먼저 노드로 파싱하여 type 필드를 확인
            JsonNode jsonNode = objectMapper.readTree(message.getBody());
            WebSocketMessageType messageType = WebSocketMessageType.valueOf(jsonNode.get("type").asText());

            // 메시지 타입에 따라 적절한 클래스 사용
            if (messageType == WebSocketMessageType.TALK) {
                WebSocketMessage webSocketMessage = objectMapper.treeToValue(jsonNode, WebSocketMessage.class);

                // 메시지 처리 로직
                if (session.isOpen() && !webSocketMessage.getPayload().getUsername().equals(session.getAttributes().get("username"))) {
                    session.sendMessage(new TextMessage(new String(message.getBody())));
                }
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            log.error("==========");
            log.error(e.getLocalizedMessage());
        }
    }

}
