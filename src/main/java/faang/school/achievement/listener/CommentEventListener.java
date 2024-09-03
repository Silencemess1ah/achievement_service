package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> {
    public CommentEventListener(ObjectMapper objectMapper,
                                List<EventHandler<CommentEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, CommentEvent.class);
    }
}
