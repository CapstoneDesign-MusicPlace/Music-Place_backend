package org.musicplace.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SendYoutubeVidioDto {

    private final String vidioId;
    private final String vidioTitle;
    private final String vidioImage;
    private final String chatRoomId;
    private final String username;

    @Builder
    @JsonCreator
    public SendYoutubeVidioDto(
            @JsonProperty("vidioId") String vidioId,
            @JsonProperty("vidioTitle") String vidioTitle,
            @JsonProperty("username") String username,
            @JsonProperty("vidioImage") String vidioImage,
            @JsonProperty("chatRoomId") String chatRoomId) {
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
        this.username = username;
        this.vidioImage = vidioImage;
        this.chatRoomId = chatRoomId;
    }
}
