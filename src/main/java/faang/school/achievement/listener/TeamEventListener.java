package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.TeamEvent;
import faang.school.achievement.service.handler.teamEvent.EventHandler;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamEventListener extends AbstractEventListener<TeamEvent> implements MessageListener {

    public TeamEventListener(ObjectMapper objectMapper, List<EventHandler<TeamEvent>> eventHandlers,
                             MessageSource messageSource) {
        super(objectMapper, eventHandlers, messageSource);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, TeamEvent.class,
                teamEvent -> eventHandlers.forEach(handler -> handler.process(teamEvent)));
    }
}