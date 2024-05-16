package org.musicplace.recommend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendUpdateDto {

    private String recommend_name;

    private String thema;

    private String genre;

    private String singer;

    @Builder
    public RecommendUpdateDto(String recommend_name, String thema, String genre, String singer) {
        this.recommend_name = recommend_name;
        this.thema = thema;
        this.genre = genre;
        this.singer = singer;
    }
}