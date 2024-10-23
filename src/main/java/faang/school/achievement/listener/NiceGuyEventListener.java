package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.NiceGuyEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class NiceGuyEventListener extends AbstractEventListener<NiceGuyEvent> implements MessageListener {
    public NiceGuyEventListener(ObjectMapper objectMapper, List<EventHandler<NiceGuyEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, NiceGuyEvent.class);
    }
}
