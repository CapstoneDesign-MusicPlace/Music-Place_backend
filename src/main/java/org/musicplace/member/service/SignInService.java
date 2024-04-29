package org.musicplace.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.dto.SignInSaveDto;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final SignInRepository signInRepository;

    @Transactional
    public void SignInSave(SignInSaveDto signInSaveDto) {
        signInRepository.save(SignInEntity.builder()
                .member_id(signInSaveDto.getMember_id())
                .pw(signInSaveDto.getPw())
                .gender(signInSaveDto.getGender())
                .profile_img_url(signInSaveDto.getProfile_img_url())
                .email(signInSaveDto.getEmail())
                .nickname(signInSaveDto.getNickname())
                .name(signInSaveDto.getName())
                .build());
    }
}
