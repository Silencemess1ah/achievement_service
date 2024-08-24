package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.ProfilePicEvent;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProfilePicEventListener extends AbstractEventListener<ProfilePicEvent> {
    public ProfilePicEventListener(ObjectMapper objectMapper, List<AbstractEventHandler<ProfilePicEvent>> abstractEventHandlers) {
        super(objectMapper, abstractEventHandlers);
    }

    @Override
    protected Class<ProfilePicEvent> getType() {
        return ProfilePicEvent.class;
    }
}
