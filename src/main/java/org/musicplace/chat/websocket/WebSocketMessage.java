package org.musicplace.chat.websocket;

import org.musicplace.chat.dto.ChatDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * 웹소켓 메시지 프로토콜 (채팅 + 스트리밍)
 */
@Getter
public class WebSocketMessage {
    private final WebSocketMessageType type;
    private final ChatDto payload; // 스트리밍과 채팅 모두 동일한 ChatDto를 사용

    @JsonCreator
    public WebSocketMessage(
            @JsonProperty("type") WebSocketMessageType type,
            @JsonProperty("payload") ChatDto payload) {
        this.type = type;
        this.payload = payload;
    }
}