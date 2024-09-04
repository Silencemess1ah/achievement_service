package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.handler.AbstractAchievementHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipStartEventListener extends AbstractEventListener<MentorshipStartEvent> {
    public MentorshipStartEventListener(ObjectMapper objectMapper,
                                        List<AbstractAchievementHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, MentorshipStartEvent.class);
    }
}
