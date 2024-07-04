package faang.school.achievement.redis.handler.goal;

import faang.school.achievement.redis.handler.EventHandler;
import faang.school.achievement.service.AchievementService;

public abstract class GoalEventHandler extends EventHandler {
    public GoalEventHandler(AchievementService achievementService) {
        super(achievementService);
        type = "goal";
    }
}
