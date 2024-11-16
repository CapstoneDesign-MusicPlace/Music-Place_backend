package org.musicplace.global.security.service;

import lombok.RequiredArgsConstructor;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SignInRepository signInRepository;


    @Override
    public SignInEntity loadUserByUsername(String memberId) throws UsernameNotFoundException {
        SignInEntity signInEntity = signInRepository.findByMemberId(memberId);
        if (signInEntity == null) {
            throw new UsernameNotFoundException("User not found with member_id: " + memberId);
        }
        return signInEntity;
    }
}
