package faang.school.achievement.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AchievementEventPublisher extends EventPublisher<AchievementEvent> {
    public AchievementEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                     ObjectMapper objectMapper,
                                     @Qualifier("achievementChannel") ChannelTopic achievementChannel) {
        super(redisTemplate, objectMapper, achievementChannel);
    }
}
