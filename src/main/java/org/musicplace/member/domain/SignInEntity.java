package org.musicplace.member.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.musicplace.follow.domain.FollowEntity;
import org.musicplace.playList.domain.PLEntity;
import org.musicplace.streaming.domain.StreamingEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInEntity implements UserDetails {
    @Id
    @Column(name = "member_id", nullable = false, length = 64)
    @Comment("아이디")
    private String memberId;

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

    @Column(name = "roles", nullable = false)
    @Comment("권한")
    private String role;

    @Column(name = "oauth_provider")
    private String oauthProvider;  // OAuth2 Provider 정보

    @Column(name = "oauth_provider_id")
    private String oauthProviderId; // OAuth2 사용자 식별 ID

    @JsonManagedReference
    @OneToMany(mappedBy = "signInEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FollowEntity> followEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "signInEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StreamingEntity> streamingEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "signInEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PLEntity> playlistEntities = new ArrayList<>();

    @Builder
    public SignInEntity(String memberId, String pw, Gender gender, String email, String nickname, String name, String role, String oauthProvider, String oauthProviderId) {
        this.memberId = memberId;
        this.pw = pw;
        this.gender = gender;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.role = role;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;

    }

    public void SignInUpdate(String pw, String name, String email, String nickname, String profile_img_url) {
        this.pw = pw;
        this.profile_img_url = profile_img_url;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
    }

    public void SignInDelete() {
        this.delete_account = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));

        return roles;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
