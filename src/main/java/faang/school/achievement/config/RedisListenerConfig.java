package faang.school.achievement.config;

import faang.school.achievement.service.listener.CommentEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;
import java.util.Map;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        List<Map.Entry<MessageListenerAdapter, ChannelTopic>> adapters) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        for (Map.Entry<MessageListenerAdapter, ChannelTopic> entry : adapters) {
            container.addMessageListener(entry.getKey(), entry.getValue());
        }

        return container;
    }

    @Bean
    public Map.Entry<MessageListenerAdapter, ChannelTopic> commentEventAdapter(CommentEventListener commentEventListener,
                                                                               @Value("${spring.data.redis.channel.comment}") String nameTopic) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(commentEventListener);
        ChannelTopic channelTopic = new ChannelTopic(nameTopic);
        return Map.entry(adapter, channelTopic);
    }

}
