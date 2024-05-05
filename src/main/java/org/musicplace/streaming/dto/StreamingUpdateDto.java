package org.musicplace.streaming.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingUpdateDto {

    private String broadcastingTitle;

    private String introduce;

    @Builder
    public void StreamingUpdateDto(String broadcastingTitle, String introduce) {
        this.broadcastingTitle = broadcastingTitle;
        this.introduce = introduce;
    }
}
