package faang.school.achievement.config.redis.listeners.profilepic;

import faang.school.achievement.messaging.listener.profilepic.ProfilePicEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class ProfilePicRedisConfig {
    @Value("${spring.data.redis.channel.profile_pic}")
    private String profilePicChannel;

    @Bean
    MessageListenerAdapter profilePicListener(ProfilePicEventListener profilePicEventListener) {
        return new MessageListenerAdapter(profilePicEventListener);
    }

    @Bean
    Pair<MessageListenerAdapter, ChannelTopic> profilePic(@Qualifier("profilePicListener") MessageListenerAdapter profilePicListener) {
        return Pair.of(profilePicListener, new ChannelTopic(profilePicChannel));
    }
}
