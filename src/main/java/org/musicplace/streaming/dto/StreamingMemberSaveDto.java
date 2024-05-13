package org.musicplace.streaming.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musicplace.streaming.domain.StreamingRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingMemberSaveDto {

    private String streamingUserId;

    private StreamingRole streamingRole;

    @Builder
    public StreamingMemberSaveDto(String streamingUserId, StreamingRole streamingRole) {
        this.streamingUserId = streamingUserId;
        this.streamingRole = streamingRole;
    }
}
