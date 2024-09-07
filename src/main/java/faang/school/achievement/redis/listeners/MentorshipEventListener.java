package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.MentorshipEvent;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipEvent> {
    public MentorshipEventListener(ObjectMapper objectMapper,
                                   List<AbstractEventHandler<MentorshipEvent>> abstractEventHandlers) {
        super(objectMapper, abstractEventHandlers);
    }

    @Override
    protected Class<MentorshipEvent> getEventClassType() {
        return MentorshipEvent.class;
    }
}
