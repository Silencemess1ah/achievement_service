package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.TeamEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamEventListener extends AbstractEventListener<TeamEvent> implements MessageListener {

    public TeamEventListener(ObjectMapper objectMapper, List<EventHandler<TeamEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, TeamEvent.class,
                teamEvent -> eventHandlers.forEach(handler -> handler.reaction(teamEvent)));
    }
}