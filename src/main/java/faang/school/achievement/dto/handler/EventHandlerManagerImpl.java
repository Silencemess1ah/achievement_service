package faang.school.achievement.dto.handler;

import faang.school.achievement.dto.Event;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class EventHandlerManagerImpl<T extends Event> implements EventHandlerManager<T> {

    private final ExecutorService executorService;
    private final List<EventHandler<? extends T>> eventHandlers;
    private final Map<Class<?>, List<EventHandler<? extends T>>> mapEventByHandler = new HashMap<>();

    @PostConstruct
    public void initHandlers() {
        eventHandlers.forEach(handler -> {
            var handlers = mapEventByHandler.computeIfAbsent(handler.getEventClass(), key -> new ArrayList<>());
            handlers.add(handler);
        });
    }

    @Override
    @Async("mainExecutorService")
    public void processEvent(T event) {
        log.info("Processing event: {}", event);
        var handlers = mapEventByHandler.getOrDefault(event.getClass(), new ArrayList<>());
        handlers.forEach(handler -> invokeHandler(handler, event));
    }

    @SuppressWarnings("unchecked")
    private void invokeHandler(EventHandler<? extends T> handler, T event) {
        executorService.execute(() -> {
            EventHandler<T> typedHandler = (EventHandler<T>) handler;
            typedHandler.handleEventIfNotProcessed(event);
        });
    }
}
