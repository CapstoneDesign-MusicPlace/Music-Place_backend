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

    @Builder
    public CommentSaveDto(String nickName, String comment) {
        this.nickName = nickName;
        this.comment = comment;
    }

}
