package org.musicplace.streaming.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "STREAMINGUSER_ID", nullable = false)
    @Comment("스트리밍 참가자 아이디")
    private String streamingUserId;

    @Column(name = "STREAMING_ROLE", nullable = false)
    @Comment("스트리밍 참가자 역할")
    private StreamingRole streamingRole;

    @Builder
    public StreamingMemberEntity(String streamingUserId, StreamingRole streamingRole) {
        this.streamingUserId = streamingUserId;
        this.streamingRole = streamingRole;
    }
}
