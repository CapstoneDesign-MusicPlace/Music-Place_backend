package org.musicplace.streaming.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.musicplace.streaming.dto.StreamingRoomDto;
import org.musicplace.chat.redis.RedisServiceImpl;
import org.musicplace.streaming.handler.StreamingWebSocketHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamingRoomService {

    private final RedisServiceImpl redisService;
    private final StreamingWebSocketHandler webSocketHandler;

    // 방 정보를 저장하기 위한 ConcurrentHashMap
    private final Map<String, StreamingRoomDto> streamingRooms = new ConcurrentHashMap<>();

    /**
     * 새로운 스트리밍 방을 생성합니다.
     *
     * @param username 방을 생성하는 사용자 이름
     * @return 생성된 StreamingRoomDto
     */
    public StreamingRoomDto createRoom(String username) {
        String roomId = UUID.randomUUID().toString();
        StreamingRoomDto newRoom = StreamingRoomDto.builder()
                .roomId(roomId)
                .username(username)
                .message("스트리밍 방이 생성되었습니다.")
                .build();
        streamingRooms.put(roomId, newRoom);
        log.info("방 ID {}가 생성되었습니다.", roomId);
        return newRoom;
    }

    /**
     * 지정된 방에 사용자가 입장합니다.
     *
     * @param roomId 방 ID
     * @param session 사용자의 WebSocketSession
     * @throws IllegalArgumentException 방 ID가 존재하지 않을 경우
     */
    public void enterRoom(String roomId, WebSocketSession session) {
        if (!streamingRooms.containsKey(roomId)) {
            log.error("방 ID {}가 존재하지 않습니다.", roomId);
            throw new IllegalArgumentException("방 ID가 존재하지 않습니다: " + roomId);
        }
        redisService.subscribe("streamingRoom:" + roomId, session);
        log.info("방 ID {}에 입장했습니다.", roomId);
    }

    /**
     * 방에 메시지를 전송합니다.
     *
     * @param roomDto 방에 전송할 메시지를 포함한 StreamingRoomDto
     */
    public void sendMessage(StreamingRoomDto roomDto) {
        String channel = "streamingRoom:" + roomDto.getRoomId();
        // 메시지를 JSON 형식으로 변환하여 Redis에 퍼블리시합니다.
        String message = createMessagePayload(roomDto);
        redisService.publish(channel, message);
        log.info("방 ID {}에 메시지 전송: {}", roomDto.getRoomId(), message);
    }

    /**
     * StreamingRoomDto를 JSON 형식의 메시지로 변환합니다.
     *
     * @param roomDto 변환할 StreamingRoomDto
     * @return JSON 형식의 문자열
     */
    private String createMessagePayload(StreamingRoomDto roomDto) {
        // JSON 변환 로직 구현 필요 (예: ObjectMapper 사용)
        return roomDto.getMessage(); // 단순 메시지 전송, JSON 변환 로직 추가 필요
    }
}