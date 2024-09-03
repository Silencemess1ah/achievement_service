package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import org.springframework.context.MessageSource;

import java.util.List;

public class CommentEventListener extends AbstractEventListener<CommentEvent> {

    public CommentEventListener(ObjectMapper objectMapper, List<AbstractEventHandler<CommentEvent>> eventHandlers,
                                MessageSource messageSource, Class<CommentEvent> clazz, String channelName) {
        super(objectMapper, eventHandlers, messageSource, clazz, channelName);
    }
}
