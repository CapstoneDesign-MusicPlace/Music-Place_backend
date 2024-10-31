package org.musicplace.streaming.dto;

import lombok.Builder;
import lombok.Getter;
import org.musicplace.chat.dto.BaseMessageDto;

@Getter
public class StreamingDto extends BaseMessageDto {
    private String videoUrl;

    @Builder
    public StreamingDto(String roomId, String username, String message, String videoUrl) {
        super(roomId, username, message);
        this.videoUrl = videoUrl;
    }
}