package org.musicplace.playList.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "TITLE", nullable = false)
    @Comment("노래 제목")
    private String vidioTitle;

    @Column(name = "DELETE_STATE", nullable = false)
    @Comment("삭제여부")
    private boolean musicDelete = false;

    @Column(name = "VIDIO_ID", nullable = false)
    @Comment("비디오 아이디")
    private String vidioId;

    @Column(name = "VIDIO_IMGE", nullable = true)
    @Comment("비디오 이미지")
    private String vidioImage;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PLEntity plEntity;

    @Builder
    public MusicEntity(String vidioTitle, String vidioId, String vidioImage) {
        this.vidioTitle = vidioTitle;
        this.vidioId = vidioId;
        this.vidioImage = vidioImage;
    }

    public void setPlEntity(PLEntity plEntity) {
        this.plEntity = plEntity;
    }

    public void delete () {
        musicDelete = true;
    }

}
