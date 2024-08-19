package faang.school.achievement.config;

import faang.school.achievement.redis.listener.PostEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisCredentials redisCredentials;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisCredentials.getHost(), redisCredentials.getPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean("postChannelTopic")
    public ChannelTopic postChannelTopic() {
        return new ChannelTopic(redisCredentials.getChannels().getPost());
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(PostEventListener postEventListener) {
        return new MessageListenerAdapter(postEventListener);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, postChannelTopic());

        return redisMessageListenerContainer;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
