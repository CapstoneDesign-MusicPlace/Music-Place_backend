package org.musicplace.chat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    // 방별로 모든 WebSocket 세션을 관리하는 구조 추가
    private final Map<String, Collection<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public void subscribe(String channel, WebSocketSession session) {
        redisMessageListenerContainer.addMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
        // 방에 세션 추가
        roomSessions.computeIfAbsent(channel, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void unsubscribe(String channel, WebSocketSession session) {
        redisMessageListenerContainer.removeMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
        Collection<WebSocketSession> sessions = roomSessions.get(channel);
        if (sessions != null) {
            sessions.remove(session);
            // 방에 세션이 없으면 roomSessions에서 방 제거
            if (sessions.isEmpty()) {
                roomSessions.remove(channel);
            }
        }
    }

    // 특정 방에 있는 모든 사용자 세션을 반환
    public Collection<WebSocketSession> getSessionsInRoom(String roomId) {
        String channel = "chatRoom:" + roomId;
        return roomSessions.getOrDefault(channel, new ArrayList<>());
    }

    private RedisMessageHandler getMessageHandler(WebSocketSession session) {
        return new RedisMessageHandler(session);
    }
}

