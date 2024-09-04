package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;
    private final Class<T> eventType;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T event = objectMapper.readValue(message.getBody(), eventType);
            eventHandlers.forEach(handler -> handler.handle(event));
        } catch (IOException e) {
            throw new RuntimeException("Failed attempt to convert object");
        }
    }
}
