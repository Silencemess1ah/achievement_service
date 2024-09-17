package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import org.springframework.context.MessageSource;

import java.util.List;

public class FollowerEventListener extends AbstractEventListener<FollowerEvent> {

    public FollowerEventListener(ObjectMapper objectMapper, List<AbstractEventHandler<FollowerEvent>> eventHandlers,
                                 MessageSource messageSource, Class<FollowerEvent> clazz, String channelName) {
        super(objectMapper, eventHandlers, messageSource, clazz, channelName);
    }

}
