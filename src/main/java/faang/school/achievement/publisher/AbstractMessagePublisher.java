package faang.school.achievement.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@RequiredArgsConstructor
public abstract class AbstractMessagePublisher<T> implements MessagePublisher<T> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopics;

    @Override
    public void publish(T message) {
        redisTemplate.convertAndSend(channelTopics.getTopic(), message);
    }
}
