package org.musicplace.global.security.config;

import org.musicplace.member.domain.SignInEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final SignInEntity signInEntity;

    public CustomUserDetails(SignInEntity signInEntity) {
        this.signInEntity = signInEntity;
    }

    public SignInEntity getSignInEntity() {
        return signInEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(signInEntity.getRole()));
    }

    @Override
    public String getPassword() {
        return signInEntity.getPw();
    }

    @Override
    public String getUsername() {
        return signInEntity.getMemberId();
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
        return !signInEntity.getDelete_account();
    }
}
