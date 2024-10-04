package org.musicplace.playList.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSaveDto {

    private String vidioId;

    private String vidioTitle;

    private String vidioImage;

    @Builder
    public MusicSaveDto(String vidioId, String vidioTitle, String vidioImage) {
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
        this.vidioImage = vidioImage;
    }
}
