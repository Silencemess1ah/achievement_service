package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.AlbumCreatedEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumCreateEventListener extends AbstractEventListener<AlbumCreatedEvent> {

    public AlbumCreateEventListener(ObjectMapper objectMapper, List<EventHandler<AlbumCreatedEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, AlbumCreatedEvent.class);
    }
}
