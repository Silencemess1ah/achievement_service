package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractEventListener<E> implements MessageListener {
    final private List<EventHandler<E>> handlers;
    final private ObjectMapper mapper;
    final private Class<E> eventType;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        E event;
        try {
            event = mapper.readValue(message.getBody(), eventType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        handlers.stream()
                .filter(h -> h.canBeHandled(event))
                .forEach(h -> h.handle(event));
    }
}
