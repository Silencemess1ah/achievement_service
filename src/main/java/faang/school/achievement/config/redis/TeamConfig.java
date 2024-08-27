package faang.school.achievement.config.redis;

import faang.school.achievement.listener.TeamMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.util.Pair;

@Configuration
public class TeamConfig {
    @Value("${spring.data.redis.channel.team}")
    private String teamTopic;

    @Bean
    public ChannelTopic teamTopic() {
        return new ChannelTopic(teamTopic);
    }

    @Bean
    MessageListenerAdapter teamEventListener(TeamMessageListener teamMessageListener) {
        return new MessageListenerAdapter(teamMessageListener);
    }

    @Bean
    Pair<MessageListenerAdapter, ChannelTopic> followerRequester(MessageListenerAdapter teamMessageListener) {
        return Pair.of(teamMessageListener, teamTopic());
    }
}
