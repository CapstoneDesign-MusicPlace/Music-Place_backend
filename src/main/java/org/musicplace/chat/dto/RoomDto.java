package org.musicplace.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDto {
    private String chatRoomId;
    private String username;
    private String roomTitle;
    private String roomComment;

    @Builder
    @JsonCreator
    public RoomDto(
            @JsonProperty("chatRoomId") String chatRoomId,
            @JsonProperty("username") String username,
            @JsonProperty("roomTitle") String roomTitle,
            @JsonProperty("roomComment") String roomComment) {
        this.chatRoomId = chatRoomId;
        this.username = username;
        this.roomTitle = roomTitle;
        this.roomComment = roomComment;
    }
}
