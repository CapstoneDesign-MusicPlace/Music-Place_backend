package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musicplace.playList.domain.OnOff;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePLDto {

    private Long playlist_id;

    private String nickname;

    private String PLTitle;

    private String cover_img;

    private OnOff onOff;

    private String comment;

    private String member_id;

    @Builder
    public ResponsePLDto(Long playlist_id, String PLTitle, String nickname,String cover_img, OnOff onOff, String comment, String member_id) {
        this.playlist_id = playlist_id;
        this.nickname = nickname;
        this.PLTitle = PLTitle;
        this.cover_img = cover_img;
        this.onOff = onOff;
        this.comment = comment;
        this.member_id = member_id;
    }
}
