package faang.school.achievement.listener.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.achievement.AchievementEvent;
import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.listener.AbstractEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AchievementListener extends AbstractEventListener<AchievementEvent> {
    public AchievementListener(
            List<EventHandler<AchievementEvent>> eventHandlers,
            ObjectMapper mapper
    ) {
        super(eventHandlers, mapper, AchievementEvent.class);
    }
}
