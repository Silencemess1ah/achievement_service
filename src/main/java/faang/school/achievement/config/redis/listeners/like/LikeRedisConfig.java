package faang.school.achievement.config.redis.listeners.like;

import faang.school.achievement.messaging.listener.like.LikeEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class LikeRedisConfig {

    @Value("${spring.data.redis.channel.like}")
    private String likeChannel;

    @Bean
    MessageListenerAdapter likeListenerAdapter(LikeEventListener likeEventListener) {
        return new MessageListenerAdapter(likeEventListener);
    }

    @Bean
    Pair<MessageListenerAdapter, ChannelTopic> likeEvent(
            @Qualifier(value = "likeListenerAdapter") MessageListenerAdapter likeListenerAdapter) {
        return Pair.of(likeListenerAdapter, new ChannelTopic(likeChannel));
    }
}
