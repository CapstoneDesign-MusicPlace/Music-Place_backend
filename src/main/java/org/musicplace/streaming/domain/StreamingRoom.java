package org.musicplace.streaming.domain;

public class StreamingRoom {

    // 방의 고유 ID
    private String roomId;
    // 방장 ID
    private String hostId;
    // 유튜브 재생목록 ID
    private String playlistId;
    // 방이 비공개인지 여부
    private boolean isPrivate;
    // 비공개 방에 사용할 비밀번호
    private String password;

    // 생성자
    public StreamingRoom(String roomId, String hostId, String playlistId, boolean isPrivate, String password) {
        this.roomId = roomId;  // 방 ID 초기화
        this.hostId = hostId;  // 방장 ID 초기화
        this.playlistId = playlistId;  // 유튜브 재생목록 ID 초기화
        this.isPrivate = isPrivate;  // 방 비공개 여부 초기화
        // 비공개 방이라면 비밀번호를 설정하고, 아니면 null로 설정
        this.password = isPrivate ? password : null;
    }

    // 비밀번호 확인 메서드
    public boolean checkPassword(String inputPassword) {
        // 비밀번호가 null이 아니고 입력된 비밀번호와 일치하는지 확인
        return password != null && password.equals(inputPassword);
    }

    // Getter 및 Setter
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}