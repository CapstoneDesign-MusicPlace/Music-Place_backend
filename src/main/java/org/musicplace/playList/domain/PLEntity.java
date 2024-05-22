package org.musicplace.playList.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.musicplace.global.jpa.AuditInformation;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.playList.dto.MusicSaveDto;

import java.util.ArrayList;
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
    private boolean PLDelete = false;

    @JsonManagedReference
    @OneToMany(mappedBy = "plEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MusicEntity> musicEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "plEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private SignInEntity signInEntity;


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
        PLDelete = true;
    }

    public void SignInEntity(SignInEntity signInEntity) {this.signInEntity = signInEntity; }
}
