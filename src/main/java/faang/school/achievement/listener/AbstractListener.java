package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractListener<T> implements MessageListener {
    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;
    private final Class<T> eventType;

    protected T handleEvent(Message message) throws IOException {
        return null;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        T event;
        try {
            event = handleEvent(message);
        } catch (IOException e) {
            throw new RuntimeException(e + "couldn't deserialize message");
        }
        eventHandlers.forEach(handler -> handler.handle(event));
        log.info("{} events have been handled", eventHandlers.size());
    }
}
