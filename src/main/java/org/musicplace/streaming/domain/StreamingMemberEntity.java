package org.musicplace.streaming.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
@Getter
@Table(name = "STREAMINGMEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NUMBER", nullable = false)
    private Long memberNum;

    @Column(name = "STREAMINGUSER_ID", nullable = false)
    @Comment("스트리밍 참가자 아이디")
    private String streamingUserId;

    @Column(name = "STREAMING_ROLE", nullable = false)
    @Comment("스트리밍 참가자 역할")
    private StreamingRole streamingRole;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "streaming_id")
    private StreamingEntity streamingEntity;

    @Builder
    public StreamingMemberEntity(String streamingUserId, StreamingRole streamingRole) {
        this.streamingUserId = streamingUserId;
        this.streamingRole = streamingRole;
    }

    public void setStreamingEntity(StreamingEntity streamingEntity) {
        this.streamingEntity = streamingEntity;
    }
}
