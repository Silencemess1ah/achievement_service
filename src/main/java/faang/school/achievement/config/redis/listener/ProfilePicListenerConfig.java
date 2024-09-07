package faang.school.achievement.config.redis.listener;

import faang.school.achievement.redis.listeners.ProfilePicEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class ProfilePicListenerConfig {

    @Value("${spring.data.redis.channel.profile_pic_channel}")
    private String topicName;

    @Bean
    public MessageListenerAdapter profilePicListenerAdapter(ProfilePicEventListener profilePicEventListener) {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    @Bean
    public ChannelTopic profilePicTopic() {
        return new ChannelTopic(topicName);
    }

    @Bean
    public Pair<MessageListenerAdapter, ChannelTopic> profilePiclistenerTopicPair(
            @Qualifier("profilePicListenerAdapter") MessageListenerAdapter profilePicEventListener,
            @Qualifier("profilePicTopic") ChannelTopic profilePicTopic) {

        return Pair.of(profilePicEventListener, profilePicTopic);
    }
}
