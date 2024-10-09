package faang.school.achievement.event.handler;

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
public class EventHandlerManagerImpl implements EventHandlerManager {

    private final List<EventHandler<?>> eventHandlers;
    private final Map<Class<?>, List<EventHandler<?>>> mapEventByHandler = new HashMap<>();

    @PostConstruct
    public void initHandlers() {
        eventHandlers.forEach(handler -> {
            var handlers = mapEventByHandler.computeIfAbsent(handler.getEventClass(), key -> new ArrayList<>());
            handlers.add(handler);
        });
    }

    @Override
    @Async("mainExecutorService")
    public <T> void processEvent(T event) {
        log.info("Processing event: {}", event);
        List<EventHandler<?>> eventHandlers = mapEventByHandler.get(event.getClass());
        Optional.ofNullable(eventHandlers).ifPresent(handlers -> handlers.forEach(
                handler -> invokeHandler(handler, event)
        ));
    }

    @Async("mainExecutorService")
    @SuppressWarnings("unchecked")
    public <T> void invokeHandler(EventHandler<?> handler, T event) {
        EventHandler<T> typedHandler = (EventHandler<T>) handler;
        typedHandler.handleEvent(event);
    }
}
