package faang.school.achievement.config.redis.listeners.post;

import faang.school.achievement.listener.post.PostEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class PostRedisConfig {
    @Value("${spring.data.redis.channel.post}")
    private String postChannel;

    @Bean
    MessageListenerAdapter postListener(PostEventListener postEventListener) {
        return new MessageListenerAdapter(postEventListener);
    }

    @Bean
    Pair<MessageListenerAdapter, ChannelTopic> post(@Qualifier("postListener") MessageListenerAdapter postListener) {
        return Pair.of(postListener, new ChannelTopic(postChannel));
    }
}
