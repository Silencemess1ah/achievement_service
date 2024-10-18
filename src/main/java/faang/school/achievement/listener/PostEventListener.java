package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.event.PostEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractEventListener<PostEvent> implements MessageListener {
    public PostEventListener(ObjectMapper objectMapper,
                             List<EventHandler<PostEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, PostEvent.class);
    }
}
