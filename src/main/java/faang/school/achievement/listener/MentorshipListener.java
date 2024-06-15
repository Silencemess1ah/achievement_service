package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.eventhandler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class MentorshipListener extends AbstractEventListener<MentorshipStartEvent>{

    public MentorshipListener(ObjectMapper objectMapper,
                              List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected MentorshipStartEvent readEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), MentorshipStartEvent.class);
    }
}
