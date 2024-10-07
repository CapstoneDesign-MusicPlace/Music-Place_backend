package org.musicplace.global.security.service;

import lombok.RequiredArgsConstructor;
import org.musicplace.global.security.config.CustomUserDetails;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SignInRepository signInRepository;


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        // member_id를 사용하여 사용자 조회
        SignInEntity signInEntity = signInRepository.findByMemberId(memberId);

        // 사용자가 존재하지 않을 경우 예외 발생
        if (signInEntity == null) {
            throw new UsernameNotFoundException("User not found with member_id: " + memberId);
        }

        // CustomUserDetails 객체 반환
        return new CustomUserDetails(signInEntity);
    }
}
