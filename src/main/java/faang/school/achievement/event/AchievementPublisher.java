package faang.school.achievement.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementPublisher {

    protected final RedisTemplate<String, Object> redisTemplate;
    protected final ChannelTopic achievementChannel;

    public void publish(AchievementEvent event) {
        redisTemplate.convertAndSend(achievementChannel.getTopic(), event);
    }
}