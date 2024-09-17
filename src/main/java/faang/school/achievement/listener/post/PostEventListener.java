package faang.school.achievement.listener.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.listener.AbstractEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractEventListener<PostEvent> {
    public PostEventListener(
            List<EventHandler<PostEvent>> eventHandlers,
            ObjectMapper mapper) {
        super(eventHandlers, mapper, PostEvent.class);
    }
}
