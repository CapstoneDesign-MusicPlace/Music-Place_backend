package org.musicplace.playList.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSaveDto {
    private Long music_id;

    private String vidioId;

    private String vidioTitle;

    @Builder
    public MusicSaveDto(String vidioId, String vidioTitle) {
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
    }
}
