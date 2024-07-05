package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProfilePicEvent;
import faang.school.achievement.handlers.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ProfilePicEventListener extends AbstractListener<ProfilePicEvent> {

    public ProfilePicEventListener(ObjectMapper objectMapper, List<EventHandler<ProfilePicEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected ProfilePicEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), ProfilePicEvent.class);
    }
}
