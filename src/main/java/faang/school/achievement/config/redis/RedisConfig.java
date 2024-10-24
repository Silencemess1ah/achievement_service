package faang.school.achievement.config.redis;

import faang.school.achievement.listener.LikeEventListener;
import faang.school.achievement.listener.MentorshipEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(
            Map<String, MessageListenerAdapter> listenerAdapters,
            Map<String, ChannelTopic> topics) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(listenerAdapters.get("likeEventAdapter"), topics.get("likeChannelTopic"));
        container.addMessageListener(listenerAdapters.get("mentorshipEventAdapter"), topics.get("mentorshipEventChannel"));
        return container;
    }

    @Bean
    MessageListenerAdapter likeEventAdapter(LikeEventListener likeEventListener) {
        return new MessageListenerAdapter(likeEventListener);
    }

    @Bean
    MessageListenerAdapter mentorshipEventAdapter(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean(value = "likeChannelTopic")
    ChannelTopic likeChannelTopic(@Value("${spring.data.redis.channel.like-channel.name}") String likeChannelName) {
        return new ChannelTopic(likeChannelName);
    }

    @Bean(value = "mentorshipEventChannel")
    ChannelTopic mentorshipEventTopic(@Value("${spring.data.redis.channels.mentorship-channel.name}") String name) {
        return new ChannelTopic(name);
    }
}
