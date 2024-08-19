package faang.school.achievement.publisher.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.achievement.AchievementEvent;
import faang.school.achievement.publisher.AbsractEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class AchievementPublisher extends AbsractEventPublisher<AchievementEvent> {
    public AchievementPublisher(
            RedisTemplate<String, Object> redisTemplate,
            ChannelTopic achievementTopic,
            ObjectMapper objectMapper) {
        super(redisTemplate, achievementTopic, objectMapper);
    }
}
