package faang.school.achievement.config;

import faang.school.achievement.listener.MentorshipEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.mentorship_channel.name}")
    private String mentorshipTopicName;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        log.info("redis start work at port {}", port);
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public MessageListenerAdapter mentorshipListener(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    public ChannelTopic mentorshipEventTopic() {
        return new ChannelTopic(mentorshipTopicName);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory());
        redisMessageListenerContainer.addMessageListener(listenerAdapter, mentorshipEventTopic());
        return redisMessageListenerContainer;
    }
}
