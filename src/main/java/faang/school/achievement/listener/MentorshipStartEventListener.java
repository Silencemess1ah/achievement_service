package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipStartEventListener extends AbstractEventListener<MentorshipStartEvent> {
    public MentorshipStartEventListener(ObjectMapper objectMapper,
                                        List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected Class<MentorshipStartEvent> getInstance() {
        return MentorshipStartEvent.class;
    }
}
