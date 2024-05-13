package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musicplace.playList.domain.OnOff;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLUpdateDto {
    private OnOff onOff;

    private String cover_img;

    private String comment;

    @Builder
    public PLUpdateDto(OnOff onOff, String cover_img, String comment){
        this.onOff = onOff;
        this.comment = comment;
        this.cover_img = cover_img;
    }
}
