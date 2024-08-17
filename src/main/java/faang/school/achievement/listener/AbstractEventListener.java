package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {

    private final ObjectMapper objectMapper;

    protected T handleEvent(Class<T> clazz, Message message) {
        try {
            return objectMapper.readValue(message.getBody(), clazz);
        } catch (IOException e) {
            log.error("Failed to deserialize follower event", e);
            throw new RuntimeException(e);
        }
    }
}
