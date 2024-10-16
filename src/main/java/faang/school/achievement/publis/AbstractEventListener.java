package faang.school.achievement.publis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.service.handlers.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {
    protected final ObjectMapper objectMapper;
    protected final List<EventHandler<T>> eventHandlers;

    protected T mapToEvent(String messageBody, Class<T> eventType) {
        try {
            return objectMapper.readValue(messageBody, eventType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    protected void handleEvent(T event) {
        eventHandlers.forEach(eventHandler -> eventHandler.handle(event));
    }
}
