package org.musicplace.playList.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

/*    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private PLEntity plEntity;*/


}
