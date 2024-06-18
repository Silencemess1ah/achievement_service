package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.handlers.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class SkillEventListener extends AbstractListener<SkillAcquiredEvent> {

    public SkillEventListener(ObjectMapper objectMapper, List<EventHandler<SkillAcquiredEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    protected SkillAcquiredEvent listenEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), SkillAcquiredEvent.class);
    }
}
