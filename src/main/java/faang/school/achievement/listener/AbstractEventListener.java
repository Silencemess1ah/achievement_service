package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.Event;
import faang.school.achievement.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T extends Event> implements MessageListener {

    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;

    protected abstract T readEvent(Message message) throws IOException;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        T event = null;
        try {
            event = readEvent(message);
        } catch (IOException e) {
            log.error("Mapping error {}", message);
            e.printStackTrace();
        }
        T finalEvent = event;
        handlers.forEach(handler -> handler.handleEvent(finalEvent));
    }
}
