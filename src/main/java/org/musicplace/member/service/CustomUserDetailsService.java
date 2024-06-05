package org.musicplace.member.service;

import lombok.RequiredArgsConstructor;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // JPA 사용, Mybatis 사용시 mapper를 등록하셔서 user 정보를 받아오시면 됩니다.
    private final SignInRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SignInEntity entity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return entity;
    }
}
