package org.musicplace.chat.websocket;

import org.musicplace.Youtube.dto.YoutubeVidioDto;
import org.musicplace.chat.dto.ChatDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 웹소켓 메시지 프로토콜
 */
@Getter
public class WebSocketMessage {
    private final WebSocketMessageType type;
    private final ChatDto payload;
    private final YoutubeVidioDto youtubePayload;

    @JsonCreator
    public WebSocketMessage(
            @JsonProperty("type") WebSocketMessageType type,
            @JsonProperty("payload") ChatDto payload,
            @JsonProperty("youtubePayload") YoutubeVidioDto youtubePayload) {
        this.type = type;
        this.payload = payload;
        this.youtubePayload = youtubePayload;
    }
}
