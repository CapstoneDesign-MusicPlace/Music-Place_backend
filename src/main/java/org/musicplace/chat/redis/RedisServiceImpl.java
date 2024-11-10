package org.musicplace.chat.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Service
@RequiredArgsConstructor
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

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
        redisMessageListenerContainer.addMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
    }

    /**
     * 메시지 구독 해제
     * @param channel 채널
     * @param session WebSocketSession
     */
    public void unsubscribe(String channel, WebSocketSession session) {
        redisMessageListenerContainer.removeMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
    }

    /**
     * 메세지 핸들러 생성
     * @param session WebSocketSession
     * @return RedisMessageHandler
     */
    private RedisMessageHandler getMessageHandler(WebSocketSession session) {
        return new RedisMessageHandler(session);
    }
}
