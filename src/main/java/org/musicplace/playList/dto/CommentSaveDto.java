package org.musicplace.playList.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveDto {

    private String nickName;

    private String comment;

    private String profile_img_url;

    @Builder
    public CommentSaveDto(String nickName, String comment, String profile_img_url) {
        this.nickName = nickName;
        this.comment = comment;
        this.profile_img_url = profile_img_url;
    }

}
