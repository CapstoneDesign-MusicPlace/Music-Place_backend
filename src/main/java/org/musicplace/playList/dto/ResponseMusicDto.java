package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMusicDto {

    private Long music_id;

    private String vidioId;

    private String vidioTitle;

    private String vidioImage;

    @Builder
    public ResponseMusicDto(Long music_id, String vidioId, String vidioTitle, String vidioImage) {
        this.music_id = music_id;
        this.vidioId = vidioId;
        this.vidioTitle = vidioTitle;
        this.vidioImage = vidioImage;
    }

}
