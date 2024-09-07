package faang.school.achievement.eventhandler.post;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.achievement.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends PostEventHandler {
    public OpinionLeaderAchievementHandler(
            AchievementService achievementService,
            AchievementCache cache) {
        super(achievementService, cache, "OPINION_LEADER");
    }
}
