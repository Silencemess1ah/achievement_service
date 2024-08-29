package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.redis.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractEventListener<PostEvent> {

    public PostEventListener(ObjectMapper objectMapper, List<EventHandler<PostEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, PostEvent.class);
    }
}
