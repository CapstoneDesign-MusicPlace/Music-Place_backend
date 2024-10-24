package org.musicplace.chat.websocket;

/**
 * 웹소켓 메시지 타입 (채팅 + 스트리밍)
 */
public enum WebSocketMessageType {
    ENTER("ENTER"),
    JOIN("JOIN"),
    TALK("TALK"),
    EXIT("EXIT"),
    SUB("SUBSCRIBE"),
    PUB("PUBLISH"),
    STREAM("STREAM"); // 스트리밍을 위한 메시지 타입 추가

    private final String type;

    WebSocketMessageType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.type;
    }
}