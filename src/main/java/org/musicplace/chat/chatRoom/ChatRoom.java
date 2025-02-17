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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        String roomId = UUID.randomUUID().toString();
        log.info("Generated roomId: {}", roomId);

        RoomDto newRoom = RoomDto.builder()
                .chatRoomId(roomId)
                .roomComment(roomDto.getRoomComment())
                .roomTitle(roomDto.getRoomTitle())
                .username(roomDto.getUsername())
                .build();

        chatRooms.put(roomId, newRoom);
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

    public RoomDto deleteChatRoom(String roomId) {
        RoomDto room = chatRooms.get(roomId);
        if (room == null) {
            log.warn("Chat room with ID {} does not exist.", roomId);
            return null;
        }

        // 방에 있는 모든 사용자 세션 가져오기
        Collection<WebSocketSession> sessions = redisService.getSessionsInRoom(roomId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                String nickname = (String) session.getAttributes().get("username");
                String message = nickname + "님이 채팅방이 삭제되어 퇴장하셨습니다.";
                log.info("Publishing exit message for user {}: {}", nickname, message);

                ChatDto payload = ChatDto.builder()
                        .chatRoomId(roomId)
                        .username(nickname)
                        .message(message)
                        .build();

                // 각 사용자에게 exit 메시지 전송 및 구독 해제
                exit(payload, session);
            }
        }

        // 채팅방 자체를 삭제
        return chatRooms.remove(roomId);
    }

    public List<RoomDto> getChatRooms() {
        return new ArrayList<>(chatRooms.values());
    }


    public void enter(ChatDto chatDto, WebSocketSession session) {
        String nickname = (String) session.getAttributes().get("username");

        if (chatDto.getChatRoomId() == null || chatDto.getChatRoomId().isEmpty()) {
            log.error("No chat room ID provided for user {}.", nickname);
            return;
        }

        if (!chatRooms.containsKey(chatDto.getChatRoomId())) {
            log.error("Chat room ID {} does not exist.", chatDto.getChatRoomId());
            return;
        }

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        redisService.subscribe(channel, session);

        String message = nickname + "님이 입장하셨습니다.";
        log.info("Publishing enter message: {}", message);

        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .username(nickname)
                .message(message)
                .build();

        redisService.publish(channel, getTextMessage(WebSocketMessageType.ENTER, payload));
    }

    public void sendMessage(ChatDto chatDto, WebSocketSession session) {
        String nickname = (String) session.getAttributes().get("username");

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .username(nickname)
                .message(chatDto.getMessage())
                .vidioId(chatDto.getVidioId())
                .build();

        redisService.publish(channel, getTextMessage(WebSocketMessageType.TALK, payload));
    }

    public void exit(ChatDto chatDto, WebSocketSession session) {
        String nickname = (String) session.getAttributes().get("username");
        String channel = "chatRoom:" + chatDto.getChatRoomId();

        redisService.unsubscribe(channel, session);

        String message = nickname + "님이 퇴장하셨습니다.";
        log.info("Publishing exit message: {}", message);

        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .username(nickname)
                .message(message)
                .build();

        redisService.publish(channel, getTextMessage(WebSocketMessageType.EXIT, payload));
    }



    /**
     * 메시지 전송
     * @param type 메시지 타입
     * @param chatDto ChatDto
     * @return String
     */
    private String getTextMessage(WebSocketMessageType type, ChatDto chatDto) {
        try {
            WebSocketMessage webSocketMessage = new WebSocketMessage(type, chatDto);
            String message = objectMapper.writeValueAsString(webSocketMessage);
            log.info("Generated text message: {}", message);
            return message;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
