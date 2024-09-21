package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.TeamEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import org.springframework.context.MessageSource;

import java.util.List;

public class TeamEventListener extends AbstractEventListener<TeamEvent> {

    public TeamEventListener(ObjectMapper objectMapper, List<AbstractEventHandler<TeamEvent>> eventHandlers,
                             MessageSource messageSource, Class<TeamEvent> clazz, String channelName) {
        super(objectMapper, eventHandlers, messageSource, clazz, channelName);
    }
}