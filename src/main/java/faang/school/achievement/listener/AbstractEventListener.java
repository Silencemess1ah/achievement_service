package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventListener<T> implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;
    private final Class<T> type;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T mentorshipStartEvent = objectMapper.readValue(message.getBody(), type);
            eventHandlers.forEach(eventHandler -> eventHandler.handleEvent(mentorshipStartEvent));
        } catch (IOException e) {
            log.error("Could not value from JSON: %s".formatted(message));
            throw new RuntimeException(e);
        }
    }
}
