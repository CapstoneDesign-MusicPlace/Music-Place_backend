package org.musicplace.chat.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.chat.chatRoom.ChatRoom;
import org.musicplace.chat.dto.RoomDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoom chatRoom;

    // 채팅방 목록 조회 엔드포인트
    @GetMapping("/rooms")
    public Collection<RoomDto> getChatRooms() {
        return chatRoom.getChatRooms();
    }

    // 새로운 채팅방 생성 엔드포인트
    @PostMapping("/rooms")
    public RoomDto createChatRoom(@RequestBody RoomDto roomDto) {
        return chatRoom.createChatRoom(roomDto);
    }
}
