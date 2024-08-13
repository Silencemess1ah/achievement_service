package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MentorshipStartEventListener
        extends AbstractEventListener<MentorshipStartEvent>
        implements MessageListener {

    @Autowired
    public MentorshipStartEventListener(ObjectMapper objectMapper, List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        MentorshipStartEvent mentorshipStartEvent = mapEvent(message, MentorshipStartEvent.class);
        eventHandlers.forEach(evenHandler->evenHandler.handle(mentorshipStartEvent.getMentorId()));
    }
}
