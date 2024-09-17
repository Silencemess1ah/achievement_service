package faang.school.achievement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.dto.event.TeamEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import faang.school.achievement.service.listener.AbstractEventListener;
import faang.school.achievement.service.listener.CommentEventListener;
import faang.school.achievement.service.listener.FollowerEventListener;
import faang.school.achievement.service.listener.TeamEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        List<AbstractEventListener> listeners) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        listeners.forEach(listener -> {
            MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "onMessage");
            ChannelTopic topic = new ChannelTopic(listener.getChannelName());
            container.addMessageListener(adapter, topic);
        });

        return container;
    }

    @Bean
    public CommentEventListener commentEventListener(ObjectMapper objectMapper,
                                                     List<AbstractEventHandler<CommentEvent>> eventHandlers,
                                                     MessageSource messageSource,
                                                     @Value("${spring.data.redis.channel.comment}") String channelName) {
        return new CommentEventListener(objectMapper, eventHandlers, messageSource, CommentEvent.class, channelName);
    }

    @Bean
    public TeamEventListener teamEventListener(ObjectMapper objectMapper,
                                               List<AbstractEventHandler<TeamEvent>> eventHandlers,
                                               MessageSource messageSource,
                                               @Value("${spring.data.redis.channel.team}") String channelName) {
        return new TeamEventListener(objectMapper, eventHandlers, messageSource, TeamEvent.class, channelName);
    }

    @Bean
    public FollowerEventListener followerEventListener(ObjectMapper objectMapper,
                                                        List<AbstractEventHandler<FollowerEvent>> eventHandlers,
                                                        MessageSource messageSource,
                                                        @Value("${spring.data.redis.channel.follower}") String channelName) {
        return new FollowerEventListener(objectMapper, eventHandlers, messageSource, FollowerEvent.class, channelName);
    }
}
