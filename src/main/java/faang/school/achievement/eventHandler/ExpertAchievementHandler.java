package faang.school.achievement.eventHandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends CommentEventHandler{

    private static final String ACHIEVEMENT_NAME = "EXPERT";

    public ExpertAchievementHandler(AchievementCache achievementCache,
                                    AchievementService achievementService) {
        super(achievementCache, achievementService, ACHIEVEMENT_NAME);
    }
}
