package org.musicplace.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatDto extends BaseMessageDto {
    @Builder
    public ChatDto(String roomId, String username, String message) {
        super(roomId, username, message);
    }
}