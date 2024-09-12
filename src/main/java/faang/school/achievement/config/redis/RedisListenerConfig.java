package faang.school.achievement.config.redis;

import faang.school.achievement.listener.MentorshipStartEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisListenerConfig {
   private final JedisConnectionFactory redisConnectionFactory;
    @Value("${spring.data.redis.channel.mentorship}")
    private String mentorshipChannelName;
    private final MentorshipStartEventListener mentorshipStartEventListener;

    @Bean
    public ChannelTopic mentorshipChannelTopic() {
        return new ChannelTopic(mentorshipChannelName);
    }
    @Bean
    public MessageListenerAdapter mentorshipListener(MentorshipStartEventListener mentorshipStartEventListener) {
        return new MessageListenerAdapter(mentorshipStartEventListener);
    }
    @Bean
    public RedisMessageListenerContainer messageListenerContainer(MessageListenerAdapter mentorshipListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(mentorshipListener, mentorshipChannelTopic());
        return container;
    }

}
