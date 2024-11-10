package org.musicplace.chat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

import static org.springframework.boot.availability.AvailabilityChangeEvent.publish;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;
    /**
     * 메시지 발행
     * @param channel 채널
     * @param message 메시지
     */
    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }
    /**
     * 메시지 구독
     * @param channel 채널
     * @param session WebSocketSession
     */
    public void subscribe(String channel, WebSocketSession session) {
        Objects.requireNonNull(stringRedisTemplate.getConnectionFactory())
                .getConnection()
                .subscribe(getMessageHandler(session), channel.getBytes());
    }
    /**
     * 여러 채널 메시지 구독
     * @param channel 채널
     * @param session WebSocketSession
     */
    public void subscribe(String[] channel, WebSocketSession session) {
        for (String c : channel){
            Objects.requireNonNull(stringRedisTemplate.getConnectionFactory())
                    .getConnection()
                    .subscribe(getMessageHandler(session), c.getBytes());
        }
    }


    /**
     * 메세지 핸들러 생성
     * @param session WebSocketSession
     */
    private RedisMessageHandler getMessageHandler(WebSocketSession session) {
        return new RedisMessageHandler(session);
    }
}
