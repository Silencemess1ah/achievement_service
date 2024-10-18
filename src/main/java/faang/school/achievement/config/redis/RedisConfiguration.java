package faang.school.achievement.config.redis;

import faang.school.achievement.listener.ProjectEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
   private final RedisProperties redisProperties;


    @Bean
    MessageListenerAdapter projectEventListenerAdapter(ProjectEventListener projectEventListener) {
        return new MessageListenerAdapter(projectEventListener);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(ProjectEventListener projectEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(projectEventListenerAdapter(projectEventListener), projectEventTopic());
        return container;
    }

    @Bean
    ChannelTopic projectEventTopic() {
        return new ChannelTopic(redisProperties.getChannels().get("project"));
    }
}
