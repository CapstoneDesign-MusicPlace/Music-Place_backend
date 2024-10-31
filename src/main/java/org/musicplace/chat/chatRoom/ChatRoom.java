package org.musicplace.chat.chatRoom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musicplace.chat.dto.ChatDto;
import org.musicplace.chat.redis.RedisServiceImpl;
import org.musicplace.chat.websocket.WebSocketMessage;
import org.musicplace.chat.websocket.WebSocketMessageType;
import org.musicplace.streaming.dto.StreamingDto;
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
    private Map<String, ChatDto> chatRooms = new ConcurrentHashMap<>();
    private Map<String, StreamingDto> streamingRooms = new ConcurrentHashMap<>();

    // 채팅방 생성
    public ChatDto createChatRoom(String username) {
        String roomId = UUID.randomUUID().toString(); // UUID로 랜덤한 roomId 생성

        log.info("Generated roomId: {}", roomId);

        // ChatDto를 Build
        ChatDto newRoom = ChatDto.builder()
                .roomId(roomId) // roomId 설정
                .username(username)  // username 설정
                .message("채팅방이 생성되었습니다.") // 기본 메시지 설정
                .build();

        chatRooms.put(roomId, newRoom); // roomId를 키로 사용하여 ChatDto 저장
        return newRoom;
    }

    // 스트리밍 방 생성
    public StreamingDto createStreamingRoom(String videoUrl) {
        String roomId = UUID.randomUUID().toString(); // UUID로 랜덤한 roomId 생성
        StreamingDto newStreamingRoom = StreamingDto.builder()
                .roomId(roomId)
                .videoUrl(videoUrl)
                .build();

        streamingRooms.put(roomId, newStreamingRoom);
        log.info("Streaming room created with ID: {}", roomId);
        return newStreamingRoom;
    }

    // 채팅방 목록 조회
    public Collection<ChatDto> getChatRooms() {
        return chatRooms.values();
    }

    public void enter(ChatDto chatDto, WebSocketSession session) {
        String username = (String) session.getAttributes().get("username");

        if (chatDto.getRoomId() == null || chatDto.getRoomId().isEmpty()) {
            chatDto = createChatRoom(username); // 새로운 채팅방 생성
            log.info("New chat room created with ID: {}", chatDto.getRoomId());
        } else {
            if (!chatRooms.containsKey(chatDto.getRoomId())) {
                log.error("Chat room ID {} does not exist.", chatDto.getRoomId());
                return; // 채팅방이 존재하지 않으면 종료
            }
        }

        String channel = "chatRoom:" + chatDto.getRoomId();
        redisService.subscribe(channel, session);

        String message = username + "님이 입장하셨습니다."; // 입장 메시지
        log.info("Publishing enter message: {}", message);

        // ChatDto 생성
        ChatDto payload = ChatDto.builder()
                .roomId(chatDto.getRoomId()) // 방 ID 설정
                .username(username) // 사용자 이름 설정
                .message(message) // 입장 메시지 설정
                .build();

        log.info("Payload for publish: {}", payload);

        redisService.publish(channel, getTextMessage(WebSocketMessageType.ENTER, payload));
        log.info("Published enter message for user {} in chat room {}", username, chatDto.getRoomId());
    }

    public void sendMessage(ChatDto chatDto) {
        String channel = "chatRoom:" + chatDto.getRoomId();

        // WebSocket 메시지 생성
        WebSocketMessage message = new WebSocketMessage(
                WebSocketMessageType.TALK,
                chatDto.getRoomId(), // roomId를 따로 가져와서 전달
                chatDto // payload에 ChatDto 사용
        );

        try {
            // 메시지를 JSON으로 변환하여 Redis에 전송
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisService.publish(channel, jsonMessage);
            log.info("Published message for room {}: {}", chatDto.getRoomId(), jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error converting ChatDto to JSON: {}", e.getMessage());
        }
    }


    public void handleChatInput(ChatDto chatDto, WebSocketSession session) {
        sendMessage(chatDto); // 메시지 전송 메서드 호출
    }

    private String getTextMessage(WebSocketMessageType type, ChatDto chatDto) {
        try {
            String message = objectMapper.writeValueAsString(new WebSocketMessage(type, chatDto));
            log.info("Generated text message: {}", message);
            return message;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendStreamingMessage(StreamingDto streamingDto) {
        String channel = "streamingRoom:" + streamingDto.getRoomId();
        try {
            String message = objectMapper.writeValueAsString(streamingDto);
            redisService.publish(channel, message);
            log.info("Published streaming message for room {}: {}", streamingDto.getRoomId(), message);
        } catch (JsonProcessingException e) {
            log.error("Error converting StreamingDto to JSON: {}", e.getMessage());
        }
    }
}