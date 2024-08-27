package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ManagerAchievementHandler extends TeamEventHandler {
    @Value("${project-service.achievements.manager}")
    private String achievement;

    public ManagerAchievementHandler(AchievementService achievementService,
                                     AchievementProgressService achievementProgressService,
                                     UserAchievementService userAchievementService) {
        super(achievementService, achievementProgressService, userAchievementService);
    }


    @Override
    public String getAchievementName() {
        return achievement;
    }
}
