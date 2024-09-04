package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.PostEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostEventListener extends AbstractListener<PostEventDto> {
    public PostEventListener(ObjectMapper objectMapper, List<EventHandler<PostEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers, PostEventDto.class);
    }
}
