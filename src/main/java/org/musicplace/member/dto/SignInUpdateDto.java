package org.musicplace.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musicplace.member.domain.Gender;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInUpdateDto {

    private String pw;

    private String name;

    private String profile_img_url;

    private String email;

    private String nickname;

    @Builder
    public SignInUpdateDto(String pw, String name, String email, String nickname, String profile_img_url){

        this.pw = pw;
        this.profile_img_url = profile_img_url;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
    }
}
