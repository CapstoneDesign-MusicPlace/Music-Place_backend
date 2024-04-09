package org.musicplace.playList.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;


@Entity
@Getter
@Table(name = "PLAYLIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYLIST_ID", nullable = false)
    private Long playlist_id;

    @Column(name = "TITLE", nullable = false)
    @Comment("플리 제목")
    private String PLTitle;

    @Column(name = "COVER_IMG", nullable = true)
    @Comment("커버 이미지")
    private String cover_img;

    @Column(name = "ONOFF", nullable = false)
    @Comment("공개여부")
    private OnOff onOff;

    @Column(name = "COMMENT", nullable = true)
    @Comment("플리 설명")
    private String comment;



/*

    @OneToMany(mappedBy = "MusicEntity", cascade = CascadeType.ALL)
    private List<MusicEntity> musicEntities;
*/

    @Builder
    public PLEntity(String title, OnOff onOff) {
        this.PLTitle = title;
        this.onOff = onOff;
    }

    public void PLUpdate() {

    }






}
