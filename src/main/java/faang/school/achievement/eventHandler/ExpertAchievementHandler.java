package faang.school.achievement.eventhandler;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.achievement.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends CommentEventHandler{

    private static final String ACHIEVEMENT_NAME = "EXPERT";

    public ExpertAchievementHandler(AchievementCache achievementCache,
                                    AchievementService achievementService) {
        super(achievementCache, achievementService, ACHIEVEMENT_NAME);
    }
}
