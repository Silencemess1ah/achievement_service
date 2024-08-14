package faang.school.achievement.publisher.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Evgenii Malkov
 */
@Component
@RequiredArgsConstructor
public class AbstractPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    protected void publish(String topic, Object message) {
        String msg = convertObject(message);
        redisTemplate.convertAndSend(topic, msg);
    }

    private String convertObject(Object message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed parse redis message object", e);
        }
    }
}
