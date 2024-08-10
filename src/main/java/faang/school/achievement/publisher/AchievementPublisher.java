package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AchievementPublisher implements Publisher<AchievementEvent> {

    private final ObjectMapper objectMapper;
    private final ChannelTopic achievementChannel;
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(AchievementEvent achievementEvent) {
        try {
            String achievementEventJson = objectMapper.writeValueAsString(achievementEvent);
            redisTemplate.convertAndSend(achievementChannel.getTopic(), achievementEventJson);
        } catch (JsonProcessingException e) {
            String errorMessage = "Could not parse event: " + achievementEvent;
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }
}
