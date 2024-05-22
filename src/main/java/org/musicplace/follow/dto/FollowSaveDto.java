package org.musicplace.follow.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowSaveDto {

    private String target_id;

    @Builder
    public FollowSaveDto(String target_id) {
        this.target_id = target_id;
    }
}
