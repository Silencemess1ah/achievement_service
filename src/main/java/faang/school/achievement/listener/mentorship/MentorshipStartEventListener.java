package faang.school.achievement.listener.mentorship;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.achievement.mentorship.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.listener.AbstractEventListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MentorshipStartEventListener extends AbstractEventListener<MentorshipStartEvent> {

    public MentorshipStartEventListener(Map<Class<?>, List<EventHandler<?>>> handlers,
                                        ObjectMapper objectMapper) {
        super(handlers, objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, MentorshipStartEvent.class);
    }
}
