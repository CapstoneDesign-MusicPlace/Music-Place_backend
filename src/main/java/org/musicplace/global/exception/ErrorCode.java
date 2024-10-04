package org.musicplace.global.exception;

import lombok.Getter;

public enum ErrorCode {
    ID_NOT_FOUND("해당 ID를 찾을 수 없습니다."),

    ID_DELETE("삭제된 ID입니다."),

    NOT_FOUND_RESULT("결과를 찾을 수 없습니다."),


    EMAIL_NOT_FOUND("해당 이메일을 찾을 수 없습니다."),

    FOLLOW_SAME_ID("동일한 팔로우 ID가 있습니다"),
    INVALID_CREDENTIALS("x"),

    SAME_MUSIC("동일한 비디오ID가 있습니다")
    ;


    @Getter
    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
