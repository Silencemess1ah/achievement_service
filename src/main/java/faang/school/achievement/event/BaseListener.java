package faang.school.achievement.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseListener<T> implements MessageListener {

    private final EventHandler eventHandler;
    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T event = objectMapper.readValue(message.getBody(), clazz);
            onEvent(event);
        } catch (IOException e) {
            log.error("Failed to process event start message", e);
            throw new RuntimeException("Failed to process event start message", e);
        }
    }

    public void onEvent(T event) {
        eventHandler.handleEvent(event);
    }

}

