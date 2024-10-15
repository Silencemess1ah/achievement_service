package faang.school.achievement.listener;


import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final List<EventHandler<CommentEvent>> handlers;

    public void handleEvent(CommentEvent event) {
        handlers.stream()
                .filter(handler -> handler.getEventClass() == (event.getClass()))
                .forEach(handler -> handler.handleEvent(event));
    }
}
