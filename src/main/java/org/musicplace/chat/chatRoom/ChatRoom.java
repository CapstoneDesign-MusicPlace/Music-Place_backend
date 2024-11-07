package org.musicplace.chat.chatRoom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.dto.RoomDto;
import org.musicplace.chat.redis.RedisServiceImpl;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.chat.websocket.WebSocketMessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Getter
@Component
@RequiredArgsConstructor
public class ChatRoom {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisServiceImpl redisService;
    private Map<String, RoomDto> chatRooms = new ConcurrentHashMap<>();


    /**
     * 채팅방 입장
     */

    // 채팅방 생성
    public RoomDto createChatRoom(RoomDto roomDto) {
        String roomId = UUID.randomUUID().toString(); // UUID로 랜덤한 roomId 생성

        // 로그로 생성된 roomId 확인
        log.info("Generated roomId: {}", roomId);

        // Builder를 사용하여 ChatDto를 Build
        RoomDto newRoom = RoomDto.builder()
                .chatRoomId(roomId)
                .roomComment(roomDto.getRoomComment())
                .roomTitle(roomDto.getRoomTitle())
                .username(roomDto.getUsername())
                .build();

        chatRooms.put(roomId, newRoom); // roomId를 키로 사용하여 ChatDto 저장
        return newRoom;
    }

    public RoomDto patchChatRoom(String roomId, RoomDto roomDto) {
        RoomDto changeRoom = RoomDto.builder()
                .chatRoomId(roomId)
                .roomComment(roomDto.getRoomComment())
                .roomTitle(roomDto.getRoomTitle())
                .username(roomDto.getUsername())
                .build();
        chatRooms.replace(roomId, changeRoom);
        return changeRoom;
    }

    public RoomDto deleteCahtRoom(String roomId) {
        return chatRooms.remove(roomId);
    }


    // 채팅방 목록 조회
    public Collection<RoomDto> getChatRooms() {
        return chatRooms.values();
    }


    public void enter(ChatDto chatDto, WebSocketSession session) {
        String username = (String) session.getAttributes().get("username");

        // 클라이언트에서 보낸 chatRoomId가 존재하는지 확인
        if (chatDto.getChatRoomId() == null || chatDto.getChatRoomId().isEmpty()) {
            log.error("No chat room ID provided for user {}.", username);
            return; // 채팅방 ID가 없으면 종료
        }

        // 이미 존재하는 채팅방에 입장
        if (!chatRooms.containsKey(chatDto.getChatRoomId())) {
            log.error("Chat room ID {} does not exist.", chatDto.getChatRoomId());
            return; // 채팅방이 존재하지 않으면 종료
        }

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        redisService.subscribe(channel, session);

        // 입장 메시지 생성
        String message = username + "님이 입장하셨습니다.";
        log.info("Publishing enter message: {}", message);

        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .username(username)
                .message(message)
                .build();

        log.info("Payload for publish: {}", payload);
        redisService.publish(channel, getTextMessage(WebSocketMessageType.ENTER, payload));
        log.info("Published enter message for user {} in chat room {}", username, chatDto.getChatRoomId());
    }



    /**
     * 메시지 전송
     * @param chatDto ChatDto
     */
    public void sendMessage(ChatDto chatDto) {
        String channel = "chatRoom:"+chatDto.getChatRoomId();
        redisService.publish(channel, getTextMessage(WebSocketMessageType.TALK, chatDto));
    }

    /**
     * 메시지 전송
     * @param type 메시지 타입
     * @param chatDto ChatDto
     * @return String
     */
    private String getTextMessage(WebSocketMessageType type, ChatDto chatDto) {
        try {
            String message = objectMapper.writeValueAsString(new WebSocketMessage(type, chatDto));
            // 로그로 실제 발행되는 메시지 확인
            log.info("Generated text message: {}", message);
            return message;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
