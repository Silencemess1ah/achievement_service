package faang.school.achievement.event;

import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.handler.AchievementHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AchievementEventDispatcher {
    private final List<AchievementHandler> handlers;

    @Autowired
    public AchievementEventDispatcher(List<AchievementHandler> handlers) {
        this.handlers = handlers;
    }

    public void dispatchEvent(ProjectEvent event) {
        for (AchievementHandler handler : handlers) {
            handler.handleAchievement(event);
        }
    }
}
