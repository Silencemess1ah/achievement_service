package faang.school.achievement.publisher;

import faang.school.achievement.dto.event.AchievementEventDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class AchievementPublisher extends AbstractMessagePublisher<AchievementEventDto> {

    public AchievementPublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic achievementChannel) {
        super(redisTemplate, achievementChannel);
    }
}
