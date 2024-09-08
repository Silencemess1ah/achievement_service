package faang.school.achievement.config.redis.listener;

import faang.school.achievement.redis.listeners.MentorshipEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class MentorshipListenerConfig {
    @Value("${spring.data.redis.channel.mentorship_channel}")
    private String topic;

    @Bean
    public MessageListenerAdapter mentorshipListenerAdapter(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    public ChannelTopic mentorshipChannel() {
        return new ChannelTopic(topic);
    }

    @Bean
    public Pair<MessageListenerAdapter, ChannelTopic> mentorshipPairAdapterAndTopic(MessageListenerAdapter mentorshipListenerAdapter,
                                                                                    ChannelTopic mentorshipChannel){
        return Pair.of(mentorshipListenerAdapter, mentorshipChannel);
    }
}