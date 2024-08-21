package faang.school.achievement.publisher.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.AchievementEvent;
import faang.school.achievement.publisher.MessagePublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * @author Evgenii Malkov
 */
@Component
public class AchievementPublisher extends AbstractPublisher implements MessagePublisher<AchievementEvent> {

    private final ChannelTopic achievementChannelTopic;

    public AchievementPublisher(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper, ChannelTopic achievementChannelTopic) {
        super(redisTemplate, objectMapper);
        this.achievementChannelTopic = achievementChannelTopic;
    }

    @Override
    public void publish(AchievementEvent message) {
        super.publish(achievementChannelTopic.getTopic(), message);
    }
}
