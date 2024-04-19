package org.musicplace.playList.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.musicplace.global.jpa.AuditInformation;

@Entity
@Table(name = "COMMENTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends AuditInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", nullable = false)
    private Long comment_id;

    @Column(name = "NICKNAME", nullable = false)
    @Comment("유저 닉네임")
    private String nickName;

    @Column(name = "COMMENT", nullable = false)
    @Comment("댓글")
    private String comment;

    @Column(name = "DELETE_STATE", nullable = false)
    @Comment("삭제여부")
    private boolean delete = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playlist_id",insertable = false, updatable = false)
    private PLEntity plEntity;

    @Builder
    public CommentEntity(String nickName, String comment) {
        this.nickName = nickName;
        this.comment = comment;
    }

    public void delete () {
        delete = true;
    }
}
