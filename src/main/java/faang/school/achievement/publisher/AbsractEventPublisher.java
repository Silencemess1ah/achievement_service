package faang.school.achievement.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Slf4j
@RequiredArgsConstructor
public abstract class AbsractEventPublisher<E extends Event> implements Publisher<E> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(E event) {
        log.info("Publishing event: {}", event);
        try {
            String topic = channelTopic.getTopic();
            String mappedEvent = objectMapper.writeValueAsString(event);
            redisTemplate.convertAndSend(topic, mappedEvent);
        } catch (Exception e) {
            log.error("Error publishing event: {}", event, e);
            throw new RuntimeException("Error publishing event: " + event, e);
        }
    }
}
