package faang.school.achievement.config;


import faang.school.achievement.listener.RecommendationEventListener;
import faang.school.achievement.listener.InviteEventListener;
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
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.recommendation}")
    private String recommendationChannel;
  
    @Value("${spring.data.redis.channels.invite}")
    private String inviteEventChannelName;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RecommendationEventListener recommendationEventListener, InviteEventListener inviteEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(recommendationEventListener, topicRecommendation());
        container.addMessageListener(inviteEventListener, topicInviteEvent());
        return container;
    }

    @Bean
    ChannelTopic topicRecommendation() {
        return new ChannelTopic(recommendationChannel);
    }
  
   @Bean
    ChannelTopic topicInviteEvent() {
        return new ChannelTopic(inviteEventChannelName);
    }
 
}