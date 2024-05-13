package org.musicplace.playList.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicSaveDto {
    private Long music_id;

    private String singer;

    private String title;

    @Builder
    public MusicSaveDto(String singer, String title) {
        this.singer = singer;
        this.title = title;
    }
}
