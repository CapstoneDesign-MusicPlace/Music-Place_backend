package org.musicplace.streaming.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingMusicSaveDto {

    private String streamingSinger;

    private String streamingTitle;

    @Builder
    public StreamingMusicSaveDto(String streamingSinger, String streamingTitle) {
        this.streamingSinger = streamingSinger;
        this.streamingTitle = streamingTitle;
    }
}
