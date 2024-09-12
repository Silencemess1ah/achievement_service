package faang.school.achievement.config.redis;

import faang.school.achievement.model.TopicType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class AchievementRedisConfig {

    @Bean
    public ChannelTopic achievementChannel() {
        return new ChannelTopic(TopicType.ACHIEVEMENT_CHANNEL.getChannelType());
    }
}
