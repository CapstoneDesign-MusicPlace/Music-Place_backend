package org.musicplace.playList.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import org.musicplace.playList.domain.OnOff;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PLSaveDto {

    private String title;

    private OnOff onOff;

    @Builder
    public PLSaveDto(String title, OnOff onOff) {
        this.title = title;
        this.onOff = onOff;
    }

}
