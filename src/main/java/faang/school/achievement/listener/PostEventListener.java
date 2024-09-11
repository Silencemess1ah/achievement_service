package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.PostEvent;
import faang.school.achievement.handler.AbstractAchievementHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractEventListener<PostEvent> {

    public PostEventListener(ObjectMapper objectMapper,
                             List<AbstractAchievementHandler<PostEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, PostEvent.class);
    }
}
