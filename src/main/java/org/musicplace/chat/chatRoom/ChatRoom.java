package org.musicplace.chat.chatRoom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.Youtube.dto.YoutubeVidioDto;
import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.dto.RoomDto;
import org.musicplace.chat.dto.SendYoutubeVidioDto;
import org.musicplace.chat.redis.RedisServiceImpl;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.chat.websocket.WebSocketMessageType;
import org.musicplace.chat.websocket.WebSocketYoutubeMessage;
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

    public RoomDto deleteCahtRoom(String roomId) {
        return chatRooms.remove(roomId);
    }

    public Collection<RoomDto> getChatRooms() {
        return chatRooms.values();
    }


    public void enter(ChatDto chatDto, WebSocketSession session) {
        String username = (String) session.getAttributes().get("username");

        if (chatDto.getChatRoomId() == null || chatDto.getChatRoomId().isEmpty()) {
            log.error("No chat room ID provided for user {}.", username);
            return;
        }

        if (!chatRooms.containsKey(chatDto.getChatRoomId())) {
            log.error("Chat room ID {} does not exist.", chatDto.getChatRoomId());
            return;
        }

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        redisService.subscribe(channel, session);

        String message = username + "님이 입장하셨습니다.";
        log.info("Publishing enter message: {}", message);

        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId())
                .username(username)
                .message(message)
                .build();

        redisService.publish(channel, getTextMessage(WebSocketMessageType.ENTER, payload));
    }


    public void sendYoutubeMessage(SendYoutubeVidioDto youtubeDto) {
        log.info(youtubeDto.toString());
        String channel = "chatRoom:" + youtubeDto.getChatRoomId();
        redisService.publish(channel, getYoutubeMessage(WebSocketMessageType.YOUTUBE, youtubeDto));
    }



    /**
     * 메시지 전송
     * @param chatDto ChatDto
     */
    public void sendMessage(ChatDto chatDto) {
        String channel = "chatRoom:" + chatDto.getChatRoomId();
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
            WebSocketMessage webSocketMessage = new WebSocketMessage(type, chatDto);
            String message = objectMapper.writeValueAsString(webSocketMessage);
            log.info("Generated text message: {}", message);
            return message;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getYoutubeMessage(WebSocketMessageType type, SendYoutubeVidioDto youtubeDto) {
        try {
            WebSocketYoutubeMessage webSocketYoutubeMessage = new WebSocketYoutubeMessage(type, youtubeDto);
            String message = objectMapper.writeValueAsString(webSocketYoutubeMessage);
            log.info("Generated text message: {}", message);
            return message;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
