package org.musicplace.streaming.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.musicplace.streaming.dto.StreamingRoomDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@Slf4j
public class StreamingWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    public StreamingWebSocketHandler() {
        this.objectMapper = new ObjectMapper(); // Jackson ObjectMapper 초기화
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message: {}", payload);

        try {
            // 메시지 처리 로직 (채팅 또는 스트리밍 데이터 전달)
            StreamingRoomDto room = parseRoomFromPayload(payload);
            if (room != null) {
                session.sendMessage(new TextMessage("방에 입장했습니다: " + room.getRoomId()));
            } else {
                session.sendMessage(new TextMessage("잘못된 메시지 형식입니다."));
            }
        } catch (IOException e) {
            log.error("메시지 처리 중 오류 발생: {}", e.getMessage());
            session.sendMessage(new TextMessage("메시지 처리 중 오류가 발생했습니다."));
        }
    }

    private StreamingRoomDto parseRoomFromPayload(String payload) throws IOException {
        // payload에서 roomId와 기타 정보를 추출하는 로직
        return objectMapper.readValue(payload, StreamingRoomDto.class); // JSON 파싱
    }
}