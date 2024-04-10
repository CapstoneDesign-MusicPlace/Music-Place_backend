package org.musicplace.global.exception;

import lombok.Getter;

public enum ErrorCode {
    ID_NOT_FOUND("해당 ID를 찾을 수 없습니다."),
    ID_DELETE("삭제된 ID입니다.");

    @Getter
    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
