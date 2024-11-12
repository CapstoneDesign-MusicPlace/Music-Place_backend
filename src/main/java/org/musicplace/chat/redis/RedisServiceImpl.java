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

    public void publish(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

    public void subscribe(String channel, WebSocketSession session) {
        redisMessageListenerContainer.addMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
    }

    public void unsubscribe(String channel, WebSocketSession session) {
        redisMessageListenerContainer.removeMessageListener(
                new MessageListenerAdapter(getMessageHandler(session)),
                new ChannelTopic(channel)
        );
    }

    private RedisMessageHandler getMessageHandler(WebSocketSession session) {
        return new RedisMessageHandler(session);
    }
}
