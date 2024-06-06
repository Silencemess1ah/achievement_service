package faang.school.achievement.config.redis.mentorship;

import faang.school.achievement.listener.mentorship.MentorshipEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class MentorshipStartRedisConfig {

    @Bean
    public Pair<Topic, MessageListenerAdapter> getMentorshipStartListenerAdapterPair(
            @Value("${spring.data.redis.channel.mentorship}") String channelTopicName,
            @Qualifier("MentorshipStartMessageAdapter") MessageListenerAdapter messageListenerAdapter) {

        return Pair.of(new ChannelTopic(channelTopicName), messageListenerAdapter);
    }

    @Bean
    public MessageListenerAdapter MentorshipStartMessageAdapter(MentorshipEventListener listener) {
        return new MessageListenerAdapter(listener);
    }
}
