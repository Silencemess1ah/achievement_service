package faang.school.achievement.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
@RequiredArgsConstructor
public class AchievementRedisConfig {

    @Value("${spring.data.redis.channels.post_comment_channel.name}")
    private String channelTopic;

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(channelTopic);
    }
}
