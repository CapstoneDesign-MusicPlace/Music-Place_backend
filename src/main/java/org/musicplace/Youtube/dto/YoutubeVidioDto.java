package org.musicplace.Youtube.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YoutubeVidioDto {

    private String vidioId;

    private String vidioTitle;

    @Builder
    public YoutubeVidioDto(String vidioId, String vidioTitle) {
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
    }

}