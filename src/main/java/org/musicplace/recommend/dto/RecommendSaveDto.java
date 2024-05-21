package org.musicplace.recommend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendSaveDto {

    private String thema;

    private String genre;

    private String singer;

    @Builder
    public RecommendSaveDto(String thema, String genre, String singer) {
        this.thema = thema;
        this.genre = genre;
        this.singer = singer;
    }
}