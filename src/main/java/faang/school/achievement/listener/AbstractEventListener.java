package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {

    private final ObjectMapper objectMapper;

    protected void handleEvent(Class<T> clazz, Message message) {
        try {
            T event  = objectMapper.readValue(message.getBody(), clazz);
            getHandlers(event).forEach(handler -> handler.handle(event));
        } catch (IOException e) {
            log.error("Failed to deserialize follower event", e);
            throw new RuntimeException(e);
        }
    }

    abstract List<EventHandler<T>> getHandlers(T event);
}