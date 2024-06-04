package org.musicplace.streaming.dto;


import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStreamingMusicDto {

    private Long streamingMusicId;

    private String streamingSinger;

    private String streamingTitle;

    @Builder
    public ResponseStreamingMusicDto(Long streamingMusicId, String streamingSinger, String streamingTitle) {
        this.streamingMusicId = streamingMusicId;
        this.streamingSinger = streamingSinger;
        this.streamingTitle = streamingTitle;
    }
}
