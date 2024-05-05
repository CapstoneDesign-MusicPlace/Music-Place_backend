package org.musicplace.streaming.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private Long streamingId;

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
    @OneToMany(mappedBy = "StreamingEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StreamingChatEntity> chatEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "StreamingEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StreamingMemberEntity> memberEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "StreamingEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

}
