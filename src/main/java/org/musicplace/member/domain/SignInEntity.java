package org.musicplace.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInEntity {
    @Id
    @Column(name = "member_id", nullable = false, length = 64)
    @Comment("아이디")
    private String member_id;

    @Column(name = "pw", nullable = false, length = 64)
    @Comment("비밀번호")
    private String pw;

    @Column(name = "name", nullable = false, length = 20)
    @Comment("이름")
    private String name;

    @Column(name = "gender", nullable = false)
    @Comment("성별")
    private Gender gender;

    @Column(name = "profile_img_url", nullable = true)
    @Comment("프로필 이미지")
    private String profile_img_url;

    @Column(name = "email", nullable = false, length = 100)
    @Comment("이메일")
    private String email;

    @Column(name = "nickname", nullable = false, length = 50)
    @Comment("닉네임")
    private String nickname;

    @Column(name = "delete_account", nullable = false)
    @Comment("탈퇴여부")
    private Boolean delete_account = false;

    @Builder
    public SignInEntity(String member_id, String pw, Gender gender, String profile_img_url, String email, String nickname, String name) {
        this.member_id = member_id;
        this.pw = pw;
        this.gender = gender;
        this.profile_img_url = profile_img_url;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
    }
}

