package faang.school.achievement.publish;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class RedisMessagePublisher<T> {
    protected final RedisTemplate<String, Object> redisTemplate;

    public void publish(T event, ChannelTopic topic) {
        redisTemplate.convertAndSend(topic.getTopic(), event);
    }
}
