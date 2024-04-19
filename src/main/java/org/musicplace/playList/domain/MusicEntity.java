package org.musicplace.playList.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "PLMUSIC")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MUSIC_ID", nullable = false)
    private Long music_id;

    @Column(name = "SINGER", nullable = false)
    @Comment("가수")
    private String singer;

    @Column(name = "TITLE", nullable = false)
    @Comment("노래 제목")
    private String title;

    @Column(name = "DELETE_STATE", nullable = false)
    @Comment("삭제여부")
    private boolean delete = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playlist_id",insertable = false, updatable = false)
    private PLEntity plEntity;

    @Builder
    public MusicEntity(String singer, String title) {
        this.singer = singer;
        this.title = title;
    }

    public void delete () {
        delete = true;
    }



}
