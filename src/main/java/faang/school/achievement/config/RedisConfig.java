package faang.school.achievement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
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

    @Value("${spring.data.redis.channel.profile-picture}")
    private String profilePicture;

    @Value("${spring.data.redis.channel.album}")
    private String albumTopicName;

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

    @Bean
    public MessageListenerAdapter profilePictureListener(ProfilePicEventListener profilePicEventListener) {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    public MessageListenerAdapter albumCreateListener(AlbumCreateEventListener albumCreateEventListener) {
        return new MessageListenerAdapter(albumCreateEventListener);
    }

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

    @Bean
    public ChannelTopic profilePictureTopic() {
        return new ChannelTopic(profilePicture);
    }

    @Bean
    public ChannelTopic albumChannelTopic() {
        return new ChannelTopic(albumTopicName);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                 ProfilePicEventListener profilePicEventListener,
                                                 AlbumCreateEventListener albumCreateEventListener) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(profilePictureListener(profilePicEventListener), profilePictureTopic());
        container.addMessageListener(albumCreateListener(albumCreateEventListener), albumChannelTopic());


        return container;
    }
}