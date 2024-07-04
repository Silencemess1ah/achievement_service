package faang.school.achievement.redis.handler.goal;

import faang.school.achievement.redis.handler.EventHandler;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;

@Deprecated
public abstract class GoalEventHandler extends EventHandler {
    public GoalEventHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
        type = "goal";
    }
}
