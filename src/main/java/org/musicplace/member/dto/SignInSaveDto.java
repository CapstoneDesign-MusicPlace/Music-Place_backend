package org.musicplace.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musicplace.member.domain.Gender;
import sun.misc.SignalHandler;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInSaveDto {
    private String member_id;

    private String pw;

    private String name;

    private String email;

    private String nickname;

    private Gender gender;

    @Builder
    public SignInSaveDto(String member_id, String pw, String name, String email, String nickname, Gender gender){
        this.member_id = member_id;
        this.pw = pw;
        this.gender = gender;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
    }
}
