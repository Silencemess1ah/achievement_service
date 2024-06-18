package faang.school.achievement.handlers;


import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class Skill5AchievementHandler extends SkillAchievementHandler {

    private static final String ACHIEVE_NAME = "SKILL5";

    public Skill5AchievementHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(ACHIEVE_NAME, achievementCache, achievementService);
    }
}
