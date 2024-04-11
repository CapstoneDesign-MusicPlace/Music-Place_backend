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
import org.musicplace.global.jpa.AuditInformation;

import java.util.List;


@Entity
@Getter
@Table(name = "PLAYLIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLEntity extends AuditInformation {
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

    @Column(name = "DELETE_STATE", nullable = false)
    @Comment("삭제여부")
    private boolean delete = false;


/*

    @OneToMany(mappedBy = "MusicEntity", cascade = CascadeType.ALL)
    private List<MusicEntity> musicEntities;
*/

    @Builder
    public PLEntity(String title, OnOff onOff, String cover_img, String comment) {
        this.PLTitle = title;
        this.onOff = onOff;
        this.comment = comment;
        this.cover_img = cover_img;
    }

    public void PLUpdate(OnOff onOff, String cover_img, String comment) {
        this.onOff = onOff;
        this.comment = comment;
        this.cover_img = cover_img;
    }

    public void delete () {
        delete = true;
    }





}
