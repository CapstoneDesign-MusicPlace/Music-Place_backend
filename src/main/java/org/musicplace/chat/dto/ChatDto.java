package org.musicplace.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ChatDto {
    private String message;
    private final String chatRoomId;
    private final String username;

    @JsonCreator
    public ChatDto(@JsonProperty("chatRoomId") String chatRoomId,
                   @JsonProperty("username") String username,
                   @JsonProperty("message") String message) {
        this.chatRoomId = chatRoomId;
        this.username = username;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}