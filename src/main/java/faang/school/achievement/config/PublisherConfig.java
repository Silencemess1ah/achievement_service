package faang.school.achievement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.publisher.achievement.RedisAchievementPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class PublisherConfig {

    @Bean
    public AchievementPublisher achievementPublisher(
        ObjectMapper objectMapper,
        RedisTemplate<String, Object> redisTemplate,
        ChannelTopic achievementChannel
    ) {
        return new RedisAchievementPublisher(objectMapper, redisTemplate, achievementChannel);
    }
}
