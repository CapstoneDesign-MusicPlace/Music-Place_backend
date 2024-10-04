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


    /**
     * 채팅방 입장
     */

    // 채팅방 생성
    public ChatDto createChatRoom(String username) {
        String roomId = UUID.randomUUID().toString(); // UUID로 랜덤한 roomId 생성

        // Builder를 사용하여 ChatDto를 Build
        ChatDto newRoom = ChatDto.builder()
                .chatRoomId(roomId) // roomId 설정
                .username(username)  // username 설정
                .message("채팅방이 생성되었습니다.") // 기본 메시지 설정
                .build();

        chatRooms.put(roomId, newRoom); // roomId를 키로 사용하여 ChatDto 저장
        return newRoom;
    }

    // 채팅방 목록 조회
    public Collection<ChatDto> getChatRooms() {
        return chatRooms.values();
    }

    public void enter(ChatDto chatDto, WebSocketSession session) {
        String username = (String) session.getAttributes().get("username");

        // 클라이언트에서 보낸 chatRoomId를 확인
        if (chatDto.getChatRoomId() == null || chatDto.getChatRoomId().isEmpty()) {
            // chatDto의 chatRoomId가 null이거나 빈 문자열인 경우 새로운 채팅방 생성
            chatDto = createChatRoom(username); // 새로운 채팅방 생성
            log.info("New chat room created with ID: {}", chatDto.getChatRoomId()); // 생성된 방 ID 확인
        } else {
            // 이미 존재하는 채팅방에 입장
            if (!chatRooms.containsKey(chatDto.getChatRoomId())) {
                log.error("Chat room ID {} does not exist.", chatDto.getChatRoomId());
                return; // 채팅방이 존재하지 않으면 종료
            }
        }

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        redisService.subscribe(channel, session);

        // 입장 메시지 생성
        String message = username + "님이 입장하셨습니다."; // 입장 메시지
        log.info("Publishing enter message: {}", message); // 메시지 로그 추가

        // ChatDto 생성: chatRoomId와 username이 올바르게 설정되었는지 확인
        ChatDto payload = ChatDto.builder()
                .chatRoomId(chatDto.getChatRoomId()) // 방 ID 설정
                .username(username) // 사용자 이름 설정
                .message(message) // 입장 메시지 설정
                .build();

        redisService.publish(channel, getTextMessage(WebSocketMessageType.ENTER, payload));

        // 추가: redisService.publish 호출 후 로그 추가
        log.info("Published enter message for user {} in chat room {}", username, chatDto.getChatRoomId());
    }

    public void leave(ChatDto chatDto, WebSocketSession session) {
        String username = (String) session.getAttributes().get("username");
        if (username == null) {
            // 예외 처리: username이 없을 경우
            log.error("Username not found in session attributes.");
            return;
        }

        String channel = "chatRoom:" + chatDto.getChatRoomId();
        chatDto.setMessage(username + "님이 퇴장하셨습니다.");
        redisService.publish(channel, getTextMessage(WebSocketMessageType.EXIT, chatDto));

        // 추가: chatDto를 chatRooms에서 제거하는 로직을 추가할 수 있음
        log.info("User {} left chat room {}", username, chatDto.getChatRoomId());
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
            return objectMapper.writeValueAsString(new WebSocketMessage(type, chatDto));
        }catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}