package faang.school.achievement.config.redis;

import faang.school.achievement.listener.CommentEventListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEvent;
import faang.school.achievement.listener.AchievementEventListener;
import faang.school.achievement.listener.LikeEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final ObjectMapper objectMapper;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, AchievementEvent> achievementEventRedisTemplate() {
        RedisTemplate<String, AchievementEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, AchievementEvent.class));
        return template;
    }

    @Bean
    MessageListenerAdapter commentEventAdapter(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener);
    }

    @Bean
    ChannelListenerAdapter likeChannelListenerAdapter(LikeEventListener likeEventListener,
                                                      @Value("${spring.data.redis.channels.like-channel.name}") String likeChannelName) {
        return new ChannelListenerAdapter(likeEventListener, likeChannelName);
    }

    @Bean(value = "commentChannelTopic")
    ChannelTopic commentChannelTopic(
            @Value("${spring.data.redis.channels.comment-channel.name}") String commentChannelName) {
        return new ChannelTopic(commentChannelName);
    }

    @Bean
    ChannelListenerAdapter achievementChannelListener(AchievementEventListener achievementEventListener,
                                                      @Value("${spring.data.redis.channel.achievement-channel.name}")
                                                      String achievementChannelName) {
        return new ChannelListenerAdapter(achievementEventListener, achievementChannelName);
    }

    @Bean
    public RedisMessageListenerContainer container(List<ChannelListenerAdapter> channelListenerAdapters) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        channelListenerAdapters.forEach(
                listener -> container.addMessageListener(listener.getListenerAdapter(), listener.getTopic()));
        return container;
    }
}
