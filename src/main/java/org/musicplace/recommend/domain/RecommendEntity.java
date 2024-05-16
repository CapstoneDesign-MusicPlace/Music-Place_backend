package org.musicplace.recommend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.ManyToAny;
import org.musicplace.member.domain.SignInEntity;

@Entity
@Getter
@Table(name = "RECOMMEND")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendEntity {
    @Id
    @Column(name = "recommend_id", nullable = false, length = 64)
    @Comment("추천id")
    private String recommend_id;

    @Column(name = "thema", nullable = false, length = 64)
    @Comment("테마")
    private String thema;

    @Column(name = "genre", nullable = false, length = 64)
    @Comment("장르")
    private String genre;

    @Column(name = "singer", nullable = false, length = 64)
    @Comment("가수")
    private String singer;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private SignInEntity signInEntity;


    @Builder
    public RecommendEntity(String recommend_id, String thema, String genre, String singer) {
        this.recommend_id = recommend_id;
        this.thema = thema;
        this.genre = genre;
        this.singer = singer;
    }

    void SignInEntity(SignInEntity signInEntity) {
        this.signInEntity = signInEntity;
    }
}