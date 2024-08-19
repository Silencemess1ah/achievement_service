package faang.school.achievement.messaging.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.AchievementEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AchievementPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementChannelTopic;
    private final ObjectMapper objectMapper;

    public void publishAchievement(AchievementEvent achievementEvent) {
        try {
            String message = objectMapper.writeValueAsString(achievementEvent);
            redisTemplate.convertAndSend(achievementChannelTopic.getTopic(), message);
            log.info("Published achievement event: {}", message);
        } catch (Exception e) {
            log.error("Failed to publish achievement event", e);
        }
    }
}
