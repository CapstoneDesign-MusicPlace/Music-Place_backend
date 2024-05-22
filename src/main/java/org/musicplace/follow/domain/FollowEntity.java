package org.musicplace.follow.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.musicplace.member.domain.SignInEntity;

@Entity
@Getter
@Table(name = "FOLLOW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("팔로우id")
    private Long follow_id;

    @Column(name = "target_id", nullable = false, length = 64)
    @Comment("대상id")
    private String target_id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private SignInEntity signInEntity;

    @Builder
    public FollowEntity(String target_id, String target_nick) {
        this.target_id = target_id;
    }

    public void SignInEntity(SignInEntity signInEntity) {this.signInEntity = signInEntity; }
}
