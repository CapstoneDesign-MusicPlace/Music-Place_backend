package org.musicplace.playList.dto;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import org.hibernate.annotations.Comment;
import org.musicplace.playList.domain.OnOff;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLSaveDto {

    private String title;

    private OnOff onOff;

    private String cover_img;

    private String comment;

    @Builder
    public PLSaveDto(String title, OnOff onOff, String cover_img, String comment) {
        this.title = title;
        this.onOff = onOff;
        this.comment = comment;
        this.cover_img = cover_img;
    }

}
