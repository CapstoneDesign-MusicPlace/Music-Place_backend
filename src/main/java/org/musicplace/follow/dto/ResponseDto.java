package org.musicplace.follow.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto {

    private Long follow_id;
    private String target_id;

    @Builder
    public ResponseDto(Long follow_id, String target_id) {
        this.follow_id = follow_id;
        this.target_id = target_id;
    }
}
