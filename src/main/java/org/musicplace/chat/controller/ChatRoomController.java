package org.musicplace.chat.controller;

import lombok.RequiredArgsConstructor;
import org.musicplace.chat.chatRoom.ChatRoom;
import org.musicplace.chat.dto.RoomDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/rooms")
    public Collection<RoomDto> getChatRooms() {
        return chatRoom.getChatRooms();
    }

    @PostMapping("/rooms")
    public String createChatRoom(@RequestBody RoomDto roomDto) {
        return chatRoom.createChatRoom(roomDto).getChatRoomId();
    }

    @DeleteMapping("/rooms/{roomId}")
    public RoomDto deleteChatRoom(@PathVariable String roomId) {
        return chatRoom.deleteChatRoom(roomId);
    }

    @PatchMapping("/rooms/{roomId}")
    public RoomDto patchChatRoom(@PathVariable String roomId, @RequestBody RoomDto roomDto) {
        return chatRoom.patchChatRoom(roomId, roomDto);
    }

}
