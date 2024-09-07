package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.eventHandler.EventHandler;
import faang.school.achievement.model.CommentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class CommentEventListener extends AbstractListener<CommentEvent> {

    public CommentEventListener(ObjectMapper objectMapper, List<EventHandler<CommentEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, CommentEvent.class);
    }

    @Override
    public CommentEvent handleEvent(Message message) throws IOException {
        return objectMapper.readValue(message.getBody(), CommentEvent.class);
    }

}
