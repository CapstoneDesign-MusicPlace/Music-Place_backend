package org.musicplace.chat.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.chat.chatRoom.ChatRoom;
import org.musicplace.chat.dto.ChatDto;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Collection<ChatDto> getChatRooms() {
        return chatRoom.getChatRooms();
    }
}
