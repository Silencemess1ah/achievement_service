package faang.school.achievement.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.listener.mentorship.MentorshipStartEventListener;
import faang.school.achievement.listener.comment.NewCommentEventListener;
import faang.school.achievement.listener.goal.GoalSetEventListener;
import faang.school.achievement.listener.profile.ProfilePicEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisProperties redisProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter commentEvent,
                                                 MessageListenerAdapter profilePicEvent,
                                                 MessageListenerAdapter mentorshipStartEvent,
                                                 MessageListenerAdapter goalSetListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(commentEvent, commentChannelTopic());
        container.addMessageListener(profilePicEvent, profilePicEventTopic());
        container.addMessageListener(mentorshipStartEvent, mentorshipStartEventTopic());
        container.addMessageListener(goalSetListenerAdapter, goalSetTopic());
        return container;
    }

    @Bean
    public ChannelTopic commentChannelTopic() {
        return new ChannelTopic(redisProperties.getChannels().getComment().getName());
    }

    @Bean
    MessageListenerAdapter commentEvent(NewCommentEventListener newCommentEventListener) {
        return new MessageListenerAdapter(newCommentEventListener);
    }

    @Bean
    public ChannelTopic profilePicEventTopic() {
        return new ChannelTopic(redisProperties.getChannels().getProfilePicEventChannel().getName());
    }

    @Bean
    MessageListenerAdapter profilePicEvent(ProfilePicEventListener profilePicEventListener) {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    @Bean
    public ChannelTopic mentorshipStartEventTopic() {
        return new ChannelTopic(redisProperties.getChannels().getMentorshipStartEvent().getName());
    }

    @Bean
    MessageListenerAdapter mentorshipStartEvent(MentorshipStartEventListener mentorshipStartEventListener) {
        return new MessageListenerAdapter(mentorshipStartEventListener);
    }

    @Bean
    public ChannelTopic achievementEventTopic() {
        return new ChannelTopic(redisProperties.getChannels().getAchievementEventChannel().getName());
    }

    @Bean
    MessageListenerAdapter goalSetListenerAdapter(GoalSetEventListener goalSetEventListener) {
        return new MessageListenerAdapter(goalSetEventListener);
    }

    @Bean
    public ChannelTopic goalSetTopic() {
        return new ChannelTopic(redisProperties.getChannels().getGoalSetChannel().getName());
    }
}
