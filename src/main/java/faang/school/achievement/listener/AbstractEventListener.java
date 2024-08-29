package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {
    protected final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;

    protected void handleEvent(T event) {
        eventHandlers.forEach(handler -> handler.handle(event));
    }
}
