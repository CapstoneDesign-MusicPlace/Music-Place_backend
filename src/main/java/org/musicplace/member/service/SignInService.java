package org.musicplace.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.dto.SignInSaveDto;
import org.musicplace.member.dto.SignInUpdateDto;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final SignInRepository signInRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void SignInSave(SignInSaveDto signInSaveDto) {
        signInRepository.save(SignInEntity.builder()
                .member_id(signInSaveDto.getMember_id())
                .pw(passwordEncoder.encode(signInSaveDto.getPw()))
                .gender(signInSaveDto.getGender())
                .email(signInSaveDto.getEmail())
                .nickname(signInSaveDto.getNickname())
                .name(signInSaveDto.getName())
                .role("ROLE_USER")
                .build());
    }

    @Transactional
    public void SignInUpdate(String member_id, SignInUpdateDto signInUpdateDto) {
        SignInEntity signInEntity = SignInFindById(member_id);
        CheckSignInDelete(signInEntity);
        signInEntity.SignInUpdate(signInUpdateDto.getPw(),
                signInUpdateDto.getName(),
                signInUpdateDto.getEmail(),
                signInUpdateDto.getNickname(),
                signInUpdateDto.getProfile_img_url());
    }

    @Transactional
    public void SignInDelete(String member_id) {
        SignInEntity signInEntity = SignInFindById(member_id);
        CheckSignInDelete(signInEntity);
        signInEntity.SignInDelete();
    }

    public SignInEntity SignInFindById(String member_id) {
        SignInEntity signInEntity = signInRepository.findById(member_id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        return signInEntity;
    }

    public Boolean SignInCheckSameId(String member_id) {
        ArrayList<SignInEntity> signInEntityArrayList = (ArrayList<SignInEntity>) signInRepository.findAll();
        for (SignInEntity getListUser : signInEntityArrayList) {
            if(getListUser.getMember_id().equals(member_id)) {
                return false;
            }
        }
        return true;
    }

    public void CheckSignInDelete(SignInEntity signInEntity) {
        if (signInEntity.getDelete_account()) {
            throw new ExceptionHandler(ErrorCode.ID_DELETE);
        }
    }

    public String ForgetPw(String member_id, String email) {
        SignInEntity signInEntity = signInRepository.findById(member_id)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        if(signInEntity.getEmail().equals(email)) {
            return signInEntity.getPw();
        }
        return ErrorCode.EMAIL_NOT_FOUND.toString();
    }

    public String ForgetId(String pw, String email) {
        List<SignInEntity> signInEntityList = signInRepository.findAll();
        String result = null;
        for(SignInEntity n : signInEntityList) {
            if(n.getPw().equals(pw) && n.getEmail().equals(email)) {
                result = n.getMember_id();
            }
        }
        return result;
    }

    public SignInEntity authenticate(String username, String password) {
        SignInEntity user = signInRepository.findById(username)
                .orElseThrow(() -> new ExceptionHandler(ErrorCode.ID_NOT_FOUND));
        if (passwordEncoder.matches(password, user.getPw())) {
            return user;
        }
        throw new ExceptionHandler(ErrorCode.INVALID_CREDENTIALS);
    }

}
