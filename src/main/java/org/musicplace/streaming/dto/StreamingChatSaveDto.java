package org.musicplace.streaming.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingChatSaveDto {

    private String writeUserId;

    private String writeUserNickname;

    private String chat;

    @Builder
    public StreamingChatSaveDto(String writeUserId, String writeUserNickname, String chat) {
        this.writeUserId = writeUserId;
        this.writeUserNickname = writeUserNickname;
        this.chat = chat;
    }
}
