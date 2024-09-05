package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProfilePicEvent;
import faang.school.achievement.handler.AbstractAchievementHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePicEventListener extends AbstractEventListener<ProfilePicEvent> {
    public ProfilePicEventListener(ObjectMapper objectMapper,
                                   List<AbstractAchievementHandler<ProfilePicEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, ProfilePicEvent.class);
    }
}
