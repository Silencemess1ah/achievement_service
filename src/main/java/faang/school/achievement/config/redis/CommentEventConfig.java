package faang.school.achievement.config.redis;

import faang.school.achievement.redis.listeners.CommentEventListener;
import io.netty.channel.ChannelConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommentEventConfig {

    @Bean
    public ChannelTopic commentEventTopic(@Value("${spring.data.redis.channel.comment_achievement}") String commentEventTopic) {
        return new ChannelTopic(commentEventTopic);
    }

    @Bean
    public MessageListenerAdapter commentEventMessageListenerAdapter(CommentEventListener listener) {
        return new MessageListenerAdapter(listener);
    }

    @Bean
    public Pair<MessageListenerAdapter, ChannelTopic> commentEventPair(
            @Qualifier("commentEventMessageListenerAdapter") MessageListenerAdapter listenerAdapter,
            @Qualifier("commentEventTopic") ChannelTopic topic) {
        return Pair.of(listenerAdapter,topic);
    }
}
