package faang.school.achievement.handler.like_event;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllLoveAchievementHandler extends LikeEventHandler {

    @Value("${achievements.like-achievements.ALL_LOVE.name}")
    private String title;

    public AllLoveAchievementHandler(AchievementService achievementService,
                                     AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    protected String getAchievementTitle() {
        return title;
    }
}
