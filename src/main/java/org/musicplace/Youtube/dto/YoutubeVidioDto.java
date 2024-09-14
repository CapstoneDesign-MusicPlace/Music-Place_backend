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

    private String vidioImage;

    @Builder
    public YoutubeVidioDto(String vidioId, String vidioTitle,String vidioImage) {
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
        this.vidioImage = vidioImage;
    }
}
