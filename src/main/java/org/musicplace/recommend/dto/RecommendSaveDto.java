package org.musicplace.recommend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendSaveDto {
    private String recommend_id;

    private String thema;

    private String genre;

    private String singer;

    @Builder
    public RecommendSaveDto(String recommend_id, String thema, String genre, String singer) {
        this.recommend_id = recommend_id;
        this.thema = thema;
        this.genre = genre;
        this.singer = singer;
    }
}