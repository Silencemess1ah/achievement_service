package faang.school.achievement.config.redis;

import faang.school.achievement.redis.AchievementPublisher;
import faang.school.achievement.redis.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
@RequiredArgsConstructor
public class AchievementRedisConfig {

    private final RedisConfig redisConfig;

    @Bean
    MessagePublisher achievementPublisher() {
        return new AchievementPublisher(redisConfig.redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("achievement_channel");
    }
}
