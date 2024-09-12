package faang.school.achievement.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AchievementEventDtoPublisher extends EventPublisher<AchievementEventDto> {
    public AchievementEventDtoPublisher(RedisTemplate<String, Object> redisTemplate,
                                     ObjectMapper objectMapper,
                                     ChannelTopic achievementTopic) {
        super(redisTemplate, objectMapper, achievementTopic);
    }
}
