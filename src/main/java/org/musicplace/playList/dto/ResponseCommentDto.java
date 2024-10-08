package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCommentDto {

    private String memberId;

    private String nickName;

    private String userComment;

    private String profile_img_url;

    @Builder
    public ResponseCommentDto( String memberId, String nickName, String userComment, String profile_img_url) {
        this.memberId = memberId;
        this.userComment = userComment;
        this.nickName = nickName;
        this.profile_img_url = profile_img_url;
    }
}
