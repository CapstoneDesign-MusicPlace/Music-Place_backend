package org.musicplace.streaming.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamingRoomDto {
    private String roomId;
    private String username;
    private String message;
}