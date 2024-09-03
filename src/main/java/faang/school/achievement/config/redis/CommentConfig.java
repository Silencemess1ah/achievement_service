package faang.school.achievement.config.redis;

import faang.school.achievement.listener.CommentMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class CommentConfig {
    @Value("${spring.data.redis.channel.comment}")
    private String commentTopic;

    @Bean
    public ChannelTopic commentTopic() {
        return new ChannelTopic(commentTopic);
    }

    @Bean
    public MessageListenerAdapter commentEventListener(CommentMessageListener commentMessageListener) {
        return new MessageListenerAdapter(commentMessageListener);
    }

    @Bean
    Pair<MessageListenerAdapter, ChannelTopic> commentRequester(MessageListenerAdapter commentEventListener) {
        return Pair.of(commentEventListener, commentTopic());
    }
}
