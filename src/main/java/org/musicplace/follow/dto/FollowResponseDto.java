package org.musicplace.follow.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResponseDto {

    private Long follow_id;
    private String target_id;
    private String profile_img_url;
    private String nickname;

    @Builder
    public FollowResponseDto(Long follow_id, String target_id, String nickname, String profile_img_url) {
        this.follow_id = follow_id;
        this.target_id = target_id;
        this.nickname = nickname;
        this.profile_img_url = profile_img_url;
    }
}
