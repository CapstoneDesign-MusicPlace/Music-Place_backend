package org.musicplace.follow.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowSaveDto {

    private String target_id;

    private String profile_img_url;

    private String nickname;

    @Builder
    public FollowSaveDto(String target_id, String profile_img_url, String nickname) {
        this.target_id = target_id;
        this.nickname = nickname;
        this.profile_img_url = profile_img_url;
    }
}
