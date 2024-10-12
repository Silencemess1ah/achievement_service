package faang.school.achievement.publisher;

import faang.school.achievement.model.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementEventPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;

    public void publish(AchievementEvent event) {
        redisTemplate.convertAndSend(achievementTopic.getTopic(), event);
    }
}
