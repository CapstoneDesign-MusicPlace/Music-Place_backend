package org.musicplace.streaming.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingSaveDto {

    private String streamerNickname;

    private String broadcastingTitle;

    private String introduce;

    @Builder
    public StreamingSaveDto(String streamerNickname, String broadcastingTitle, String introduce) {
        this.streamerNickname = streamerNickname;
        this.broadcastingTitle = broadcastingTitle;
        this.introduce = introduce;
    }
}
