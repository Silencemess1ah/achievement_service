package faang.school.achievement.config;

import faang.school.achievement.redis.listener.PostEventListener;
import faang.school.achievement.listener.CommentEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channel.achievement}")
    public String achievementChannel;

    @Value("${spring.data.redis.channel.follower}")
    private String followerChannel;

    @Value("${spring.data.redis.channel.post}")
    private String postChannel;

//    @Value("${spring.data.redis.channel.achievement}")
//    private String achievement;

    @Value("${spring.data.redis.channel.comment_achievement}")
    private String commentChannel;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

//    @Bean
//    MessageListenerAdapter commentListener(CommentEventListener commentEventListener) {
//        return new MessageListenerAdapter(commentEventListener);
//    }
//
//    @Bean
//    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter commentListener) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(jedisConnectionFactory());
//        container.addMessageListener(commentListener, commentChannel());
//        return container;
//    }

    @Bean
    public ChannelTopic achievementChannel() {
        return new ChannelTopic(achievementChannel);
    }

    @Bean("postChannelTopic")
    public ChannelTopic postChannelTopic() {
        return new ChannelTopic(postChannel);
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
//
//    @Bean
//    public ChannelTopic achievementTopic() {
//        return new ChannelTopic(achievement);
//    }
//
//    @Bean
//    public ChannelTopic commentChannel() {
//        return new ChannelTopic(commentChannel);
//    }
}