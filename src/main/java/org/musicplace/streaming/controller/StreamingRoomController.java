package org.musicplace.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.streaming.dto.StreamingRoomDto;
import org.musicplace.streaming.service.StreamingRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class StreamingRoomController {

    private final StreamingRoomService roomService;

    // 방 생성
    @PostMapping("/create")
    public ResponseEntity<StreamingRoomDto> createRoom(@RequestParam String username) {
        StreamingRoomDto roomDto = roomService.createRoom(username);
        return ResponseEntity.ok(roomDto);
    }

    // 방 입장
    @PostMapping("/enter")
    public ResponseEntity<Void> enterRoom(@RequestParam String roomId, WebSocketSession session) {
        roomService.enterRoom(roomId, session);
        return ResponseEntity.ok().build();
    }

    // 메시지 전송
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody StreamingRoomDto roomDto) {
        roomService.sendMessage(roomDto);
        return ResponseEntity.ok().build();
    }
}