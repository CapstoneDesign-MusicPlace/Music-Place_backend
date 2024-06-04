package org.musicplace.streaming.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.musicplace.member.domain.SignInEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "STREAMING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STREAMING_ID", nullable = false)
    private Long streaming_id;

    @Column(name = "STREAMER_NICKNAME", nullable = false)
    @Comment("스트리머 이름")
    private String streamerNickname;

    @Column(name = "BROADCASTING_TITLE", nullable = false)
    @Comment("스트리밍 제목")
    private String broadcastingTitle;

    @Column(name = "CONNETORS", nullable = false)
    @Comment("접속자 수")
    private Long connectors = 0L;

    @Column(name = "INTRODUCE", nullable = true)
    @Comment("스트리밍 소개글")
    private String introduce;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private SignInEntity signInEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "streamingEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StreamingChatEntity> chatEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "streamingEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StreamingMemberEntity> memberEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "streamingEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StreamingMusicEntity> musicEntities = new ArrayList<>();


    @Builder
    public StreamingEntity(String streamerNickname, String broadcastingTitle, String introduce) {
        this.streamerNickname = streamerNickname;
        this.broadcastingTitle = broadcastingTitle;
        this.introduce = introduce;
    }

    public void StreamingUpdate(String broadcastingTitle, String introduce) {
        this.broadcastingTitle = broadcastingTitle;
        this.introduce = introduce;
    }

    public void SignInEntity(SignInEntity signInEntity) {
        this.signInEntity = signInEntity;
    }

}
