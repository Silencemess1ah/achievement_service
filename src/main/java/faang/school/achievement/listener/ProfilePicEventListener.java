package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProfilePicEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePicEventListener extends AbstractEventListener<ProfilePicEvent> {
    public ProfilePicEventListener(ObjectMapper objectMapper, List<EventHandler<ProfilePicEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, ProfilePicEvent.class);
    }
}
