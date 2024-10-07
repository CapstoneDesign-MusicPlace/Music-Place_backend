package org.musicplace.global.security.config;


import org.musicplace.member.domain.SignInEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final SignInEntity signInEntity;
    private String memberId;

    public CustomUserDetails(SignInEntity signInEntity) {
        this.signInEntity = signInEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(signInEntity.getRole()));
        return roles;
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String getPassword() {
        return signInEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return signInEntity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return !signInEntity.getDelete_account(); // 탈퇴 여부에 따른 활성 상태
    }

    // 필요한 경우 추가적인 메서드를 정의하여 SignInEntity의 속성에 접근할 수 있습니다.
    public SignInEntity getSignInEntity() {
        return signInEntity;
    }
}
