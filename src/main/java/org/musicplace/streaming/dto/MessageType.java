package org.musicplace.streaming.dto;

public enum MessageType {
    ENTER("ENTER"),
    TALK("TALK"),
    STREAM("STREAM"); // 스트리밍 타입 추가

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}