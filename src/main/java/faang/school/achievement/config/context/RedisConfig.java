package faang.school.achievement.config.context;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.channel.achievement}")
    private final String achievementChannelName;

    @Bean
    ChannelTopic achievementTopic(){
        return new ChannelTopic(achievementChannelName);
    }
}
