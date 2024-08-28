package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.TeamEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.EventType;
import faang.school.achievement.service.UserEventCounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMessageListener extends RedisAbstractMessageListener<TeamEvent> {
    private final UserEventCounterService userEventCounterService;


    public TeamMessageListener(ObjectMapper objectMapper,
                               List<EventHandler<TeamEvent>> abstractEventHandlers, UserEventCounterService userEventCounterService) {
        super(objectMapper, TeamEvent.class, abstractEventHandlers);
        this.userEventCounterService = userEventCounterService;
    }

    @Override
    public void countEvent(TeamEvent teamEvent) {
        if (userEventCounterService.hasProgress(teamEvent.getAuthorId(), EventType.TEAM_EVENT)) {
            userEventCounterService.updateAllProgress(teamEvent.getAuthorId(), EventType.TEAM_EVENT);
        } else {
            userEventCounterService.addNewAllProgress(teamEvent.getAuthorId(), EventType.TEAM_EVENT);
        }
    }
}
