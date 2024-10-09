package faang.school.achievement.event.handler;

import faang.school.achievement.event.Event;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventHandlerManagerImpl<T extends Event> implements EventHandlerManager<T> {

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
        List<EventHandler<? extends T>> eventHandlers = mapEventByHandler.get(event.getClass());

        Optional.ofNullable(eventHandlers).ifPresent(handlers -> handlers.forEach(
                handler -> invokeHandler(handler, event)
        ));
    }

    @Async("mainExecutorService")
    @SuppressWarnings("unchecked")
    public void invokeHandler(EventHandler<? extends T> handler, T event) {
        EventHandler<T> typedHandler = (EventHandler<T>) handler;
        typedHandler.handleEventIfNotProcessed(event);
    }
}
