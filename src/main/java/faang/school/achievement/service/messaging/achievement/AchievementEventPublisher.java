package faang.school.achievement.service.messaging.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.AchievementEvent;
import faang.school.achievement.service.messaging.AbstractEventPublisher;
import faang.school.achievement.service.messaging.MessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AchievementEventPublisher extends AbstractEventPublisher<AchievementEvent>
        implements MessagePublisher<AchievementEvent> {

    private final ChannelTopic achievementTopic;

    protected AchievementEventPublisher(RedisTemplate<String, Object> redisTemplate,
                                        ObjectMapper objectMapper,
                                        ChannelTopic achievementTopic) {
        super(redisTemplate, objectMapper);
        this.achievementTopic = achievementTopic;
    }

    @Override
    public void publish(AchievementEvent achievementEvent) {
        log.info("Publishing LikeEvent: {}", achievementEvent);
        String eventJson = eventJson(achievementEvent);
        redisTemplate.convertAndSend(achievementTopic.getTopic(), eventJson);
    }
}
