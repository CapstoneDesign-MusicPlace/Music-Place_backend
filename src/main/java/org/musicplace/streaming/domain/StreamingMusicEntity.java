package org.musicplace.streaming.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "STREAMINGMUSIC")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingMusicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STREAMINGMUSIC_ID", nullable = false)
    private Long streamingMusicId;

    @Column(name = "SINGER", nullable = false)
    @Comment("가수")
    private String streamingSinger;

    @Column(name = "TITLE", nullable = false)
    @Comment("노래 제목")
    private String streamingTitle;

    @Builder
    public StreamingMusicEntity(String streamingSinger, String streamingTitle) {
        this.streamingSinger = streamingSinger;
        this.streamingTitle = streamingTitle;
    }
}
