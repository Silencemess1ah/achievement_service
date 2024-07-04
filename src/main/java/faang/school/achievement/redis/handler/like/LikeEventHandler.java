package faang.school.achievement.redis.handler.like;

import faang.school.achievement.redis.handler.EventHandler;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;

@Deprecated
public abstract class LikeEventHandler extends EventHandler {

    public LikeEventHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
        type = "like";
    }
}
