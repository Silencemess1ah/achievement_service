package faang.school.achievement.config.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.listener.ProfilePicEventListener;
import faang.school.achievement.listener.SkillEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channel.skill}")
    private String skillChannel;
    @Value("${spring.data.redis.channel.profile}")
    private String profilePicChannel;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter skillEventListenerAdapter,
                                                                       MessageListenerAdapter profilePicListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(skillEventListenerAdapter, skillTopic());
        container.addMessageListener(profilePicListenerAdapter, profilePicTopic());
        return container;
    }

    @Bean
    public MessageListenerAdapter profilePicListenerAdapter(ProfilePicEventListener profilePicEventListener) {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    @Bean
    public MessageListenerAdapter skillEventListenerAdapter(SkillEventListener skillEventListener) {
        return new MessageListenerAdapter(skillEventListener);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       ObjectMapper mapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(mapper, SkillAcquiredEvent.class));
        return redisTemplate;
    }

    @Bean
    ChannelTopic skillTopic() {
        return new ChannelTopic(skillChannel);
    }

    @Bean
    ChannelTopic profilePicTopic() {
        return new ChannelTopic(profilePicChannel);
    }
}
