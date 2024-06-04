package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMusicDto {

    private Long music_id;

    private String singer;

    private String title;

    @Builder
    public ResponseMusicDto(Long music_id, String singer, String title) {
        this.music_id = music_id;
        this.singer = singer;
        this.title = title;
    }

}
