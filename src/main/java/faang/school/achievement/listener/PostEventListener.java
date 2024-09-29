package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.PostEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractListener<PostEventDto> implements MessageListener {

    @Autowired
    public PostEventListener(ObjectMapper objectMapper, List<EventHandler<PostEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        PostEventDto postEvent = mapEvent(message, PostEventDto.class);
        eventHandlers.forEach(evenHandler -> evenHandler.handle(postEvent.getUserId()));
    }
}
