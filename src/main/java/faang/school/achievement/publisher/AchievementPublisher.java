package faang.school.achievement.publisher;

import faang.school.achievement.event.AchievementRecievedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementPublisher implements MessagePublisher<AchievementRecievedEvent> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @Override
    public void publish(AchievementRecievedEvent event) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), event);
        log.info("Published achievement event: {}", event);
    }
}
