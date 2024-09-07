package faang.school.achievement.redis.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CommentEventListener extends AbstractEventListener<CommentEvent> {
    public CommentEventListener(ObjectMapper objectMapper, List<AbstractEventHandler<CommentEvent>> handlers) {
        super(objectMapper, handlers);
    }

    @Override
    protected Class<CommentEvent> getEventClassType() {
        return CommentEvent.class;
    }
}
