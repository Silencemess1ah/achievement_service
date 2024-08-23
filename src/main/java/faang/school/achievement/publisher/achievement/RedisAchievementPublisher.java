package faang.school.achievement.publisher.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.publisher.RedisMessagePublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisAchievementPublisher extends RedisMessagePublisher<AchievementEventDto> implements AchievementPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;

    public RedisAchievementPublisher(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate, ChannelTopic achievementChannel) {
        super(objectMapper);
        this.redisTemplate = redisTemplate;
        this.achievementTopic = achievementChannel;
    }

    @Override
    public void publish(AchievementEventDto event) {
        String stringEvent = this.handleMessage(event);
        redisTemplate.convertAndSend(achievementTopic.getTopic(), stringEvent);
    }
}
