package org.musicplace.chat.websocket;

import org.musicplace.chat.dto.BaseMessageDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.musicplace.chat.dto.ChatDto;

/**
 * 웹소켓 메시지 프로토콜
 */
@Getter
public class WebSocketMessage {
    private final WebSocketMessageType type;
    private final String roomId; // roomId 추가
    private final BaseMessageDto payload;

    @JsonCreator
    public WebSocketMessage(
            @JsonProperty("type") WebSocketMessageType type,
            @JsonProperty("roomId") String roomId,
            @JsonProperty("payload") BaseMessageDto payload) {
        this.type = type;
        this.roomId = roomId;
        this.payload = payload;
    }

    // 새로운 생성자 추가
    public WebSocketMessage(WebSocketMessageType type, ChatDto chatDto) {
        this.type = type;
        this.roomId = chatDto.getRoomId(); // ChatDto에서 roomId 가져오기
        this.payload = chatDto; // payload에 ChatDto 설정
    }
}