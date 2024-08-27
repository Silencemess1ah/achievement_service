package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.TeamEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMessageListener extends RedisAbstractMessageListener<TeamEvent> {


    public TeamMessageListener(ObjectMapper objectMapper,
                               List<EventHandler<TeamEvent>> abstractEventHandlers) {
        super(objectMapper, TeamEvent.class, abstractEventHandlers);
    }
}
