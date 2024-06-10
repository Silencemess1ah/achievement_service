package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MentorshipEventListener extends AbstractListener<MentorshipStartEvent> {
    public MentorshipEventListener(ObjectMapper objectMapper, List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected MentorshipStartEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), MentorshipStartEvent.class);
    }
}