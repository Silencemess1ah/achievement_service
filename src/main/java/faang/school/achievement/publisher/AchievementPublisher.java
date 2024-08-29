package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher<AchievementEvent> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topicForAchievementEvent;

    public void publish(AchievementEvent event) throws JsonProcessingException {
        String jsonEvent = new ObjectMapper().writeValueAsString(event);
        redisTemplate.convertAndSend(topicForAchievementEvent.getTopic(), jsonEvent);
    }
}
