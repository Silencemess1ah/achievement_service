package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AlbumCreatedEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AlbumCreateEventListener extends AbstractEventListener<AlbumCreatedEvent> implements MessageListener {

    public AlbumCreateEventListener(ObjectMapper objectMapper, List<EventHandler<AlbumCreatedEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            AlbumCreatedEvent event = objectMapper.readValue(message.getBody(), AlbumCreatedEvent.class);
            handleEvent(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
