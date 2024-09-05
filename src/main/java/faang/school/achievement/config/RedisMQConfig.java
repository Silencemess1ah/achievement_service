package faang.school.achievement.config;

import faang.school.achievement.listener.achievement.AchievementListener;
import faang.school.achievement.listener.post.PostEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisMQConfig {
    @Value("${spring.data.redis.channel.achievement}")
    private String achievementTopicName;
    @Value("${spring.data.redis.channel.post}")
    private String postTopicName;
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;


    @Bean
    @Lazy
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public ChannelTopic achievementTopic() {
        return new ChannelTopic(achievementTopicName);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    MessageListenerAdapter achievementMessageListener(AchievementListener achievementListener) {
        return new MessageListenerAdapter(achievementListener);
    }

    @Bean
    public MessageListenerAdapter redisPostEventListener(PostEventListener postEventListener) {
        return new MessageListenerAdapter(postEventListener);
    }

    @Bean
    public ChannelTopic postEventTopic() {
        return new ChannelTopic(postTopicName);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter achievementMessageListener,
                                                 MessageListenerAdapter redisPostEventListener) {
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory());

        container.addMessageListener(achievementMessageListener, achievementTopic());
        container.addMessageListener(redisPostEventListener, postEventTopic());

        return container;
    }
}
