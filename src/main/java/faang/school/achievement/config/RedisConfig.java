package faang.school.achievement.config;

import faang.school.achievement.listener.TeamEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.channel.achievement}")
    private String channelForAchievementEvent;
    @Value("${spring.data.redis.channel.team}")
    private String channelForTeamEvent;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    ChannelTopic topicForAchievementEvent() {
        return new ChannelTopic(channelForAchievementEvent);
    }

    @Bean
    ChannelTopic topicForTeamEvent() {
        return new ChannelTopic(channelForTeamEvent);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter teamListener) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(teamListener, topicForTeamEvent());
        return container;
    }

    @Bean
    MessageListenerAdapter teamListener(TeamEventListener teamEventListener) {
        return new MessageListenerAdapter(teamEventListener);
    }
}
