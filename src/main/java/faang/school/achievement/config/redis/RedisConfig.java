package faang.school.achievement.config.redis;

import faang.school.achievement.listener.PostEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter postListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(postListener, postListenerTopic());

        return container;
    }

    @Bean
    public MessageListenerAdapter postListener(PostEventListener postEventListener){
        return new MessageListenerAdapter(postEventListener);
    }

    @Bean
    public ChannelTopic postListenerTopic(){
        return new ChannelTopic(redisProperties.channels().get("post"));
    }
}
