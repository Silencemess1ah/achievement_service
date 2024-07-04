package faang.school.achievement.redis.handler.like;

import faang.school.achievement.redis.handler.EventHandler;
import faang.school.achievement.service.AchievementService;

public abstract class LikeEventHandler extends EventHandler {

    public LikeEventHandler(AchievementService achievementService) {
        super(achievementService);
        type = "like";
    }
}
