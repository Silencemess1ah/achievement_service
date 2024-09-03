package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import faang.school.achievement.service.UserEventCounterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends CommentEventHandler {
    @Value("${project-service.achievements.expert}")
    private String achievement;

    public ExpertAchievementHandler(AchievementService achievementService,
                                    AchievementProgressService achievementProgressService,
                                    UserAchievementService userAchievementService,
                                    UserEventCounterService userEventCounterService) {
        super(achievementService, achievementProgressService, userAchievementService, userEventCounterService);
    }

    @Override
    public String getAchievementName() {
        return achievement;
    }
}