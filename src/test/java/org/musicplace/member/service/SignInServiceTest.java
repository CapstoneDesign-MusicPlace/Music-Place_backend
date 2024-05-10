package org.musicplace.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.musicplace.member.domain.Gender;
import org.musicplace.member.domain.SignInEntity;
import org.musicplace.member.dto.SignInSaveDto;
import org.musicplace.member.dto.SignInUpdateDto;
import org.musicplace.member.repository.SignInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignInServiceTest {

    @Autowired
    private SignInService signInService;

    @Autowired
    private SignInRepository signInRepository;

    @BeforeEach
    void setUp() {
        signInRepository.deleteAll();
    }

    @Test
    @DisplayName("Sign In Save")
    void signInSave() {
        //given
        String member_id = "jucheolkang";
        String pw = "1234";
        String name = "강주철";
        Gender gender = Gender.male;
        String email = "jucheolkang@naver.com";
        String nickname = "전아협";

        signInService.SignInSave(SignInSaveDto.builder()
                .pw(pw)
                .gender(gender)
                .member_id(member_id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build());

        //when
        SignInEntity signInEntity = signInRepository.findById(member_id).get();

        //then
        assertEquals(pw, signInEntity.getPw());
        assertEquals(email, signInEntity.getEmail());
        assertEquals(member_id, signInEntity.getMember_id());
        assertEquals(name, signInEntity.getName());
        assertEquals(nickname, signInEntity.getNickname());
        assertEquals(gender, signInEntity.getGender());
    }

    @Test
    @DisplayName("SignIn Update")
    void signInUpdate() {

        //given
        String member_id = "jucheolkang";
        String pw1 = "1234";
        String name1 = "강주철";
        Gender gender = Gender.male;
        String email1 = "jucheolkang@naver.com";
        String nickname1 = "전아협";

        String pw2 = "5678";
        String name2 = "주철강";
        String email2 = "jucheolkang1111@naver.com";
        String nickname2 = "전가협";

        signInService.SignInSave(SignInSaveDto.builder()
                .pw(pw1)
                .gender(gender)
                .member_id(member_id)
                .email(email1)
                .name(name1)
                .nickname(nickname1)
                .build());

        //when
        signInService.SignInUpdate(member_id, SignInUpdateDto.builder()
                .pw(pw2)
                .name(name2)
                .nickname(nickname2)
                .email(email2)
                .build());
        SignInEntity signInEntity = signInRepository.findById(member_id).get();

        //then
        assertEquals(pw2, signInEntity.getPw());
        assertEquals(email2, signInEntity.getEmail());
        assertEquals(name2, signInEntity.getName());
        assertEquals(nickname2, signInEntity.getNickname());
    }

    @Test
    @DisplayName("SignIn Delete")
    void signInDelete() {

        //given
        String member_id = "jucheolkang";
        String pw = "1234";
        String name = "강주철";
        Gender gender = Gender.male;
        String email = "jucheolkang@naver.com";
        String nickname = "전아협";

        signInService.SignInSave(SignInSaveDto.builder()
                .pw(pw)
                .gender(gender)
                .member_id(member_id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build());

        //when
        signInService.SignInDelete(member_id);
        Optional<SignInEntity> signInEntity = signInRepository.findById(member_id);

        //then
        assertTrue(signInEntity.isPresent());
    }

    @Test
    void forgetPw() {
        //given
        String member_id = "jucheolkang";
        String pw = "1234";
        String name = "강주철";
        Gender gender = Gender.male;
        String email = "jucheolkang@naver.com";
        String nickname = "전아협";

        signInService.SignInSave(SignInSaveDto.builder()
                .pw(pw)
                .gender(gender)
                .member_id(member_id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build());

        //when
        String findPw = signInService.ForgetPw(member_id, email);

        //then
        assertEquals(pw, findPw);
    }

    @Test
    void forgetId() {
        //given
        String member_id = "jucheolkang";
        String pw = "1234";
        String name = "강주철";
        Gender gender = Gender.male;
        String email = "jucheolkang@naver.com";
        String nickname = "전아협";

        signInService.SignInSave(SignInSaveDto.builder()
                .pw(pw)
                .gender(gender)
                .member_id(member_id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build());

        //when
        String findId = signInService.ForgetId(pw, email);

        //then
        assertEquals(member_id, findId);
    }
}