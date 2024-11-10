package org.musicplace.chat.websocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.musicplace.chat.dto.SendYoutubeVidioDto;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSocketYoutubeMessage {
    private final WebSocketMessageType type;
    private final SendYoutubeVidioDto payload;

    @JsonCreator
    public WebSocketYoutubeMessage(
            @JsonProperty("type") WebSocketMessageType type,
            @JsonProperty("payload") SendYoutubeVidioDto payload) {
        this.type = type;
        this.payload = payload;
    }
}
