package faang.school.achievement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class AchievementRedisConfig {

    @Value("${spring.data.redis.channel.achievement }")
    private String achievementChannel;

    @Bean
    public ChannelTopic achievementChannelTopic() {
        return new ChannelTopic(achievementChannel);
    }
}
