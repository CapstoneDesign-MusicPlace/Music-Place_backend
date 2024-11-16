package org.musicplace.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatDto {
    private String message;
    private final String chatRoomId;
    private final String username;
    private final String vidioId;


    @Builder
    @JsonCreator
    public ChatDto(@JsonProperty("chatRoomId") String chatRoomId,
                   @JsonProperty("username") String username,
                   @JsonProperty("message") String message,
                   @JsonProperty("vidioId") String vidioId) {
        this.chatRoomId = chatRoomId;
        this.username = username;
        this.message = message;
        this.vidioId = vidioId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
