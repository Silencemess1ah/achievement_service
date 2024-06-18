package faang.school.achievement.handlers;


import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class WhoeverAchievementHandler extends SkillAchievementHandler {

    private static final String ACHIEVE_NAME = "SKILLER";

    public WhoeverAchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(ACHIEVE_NAME, achievementCache, achievementService);
    }
}
