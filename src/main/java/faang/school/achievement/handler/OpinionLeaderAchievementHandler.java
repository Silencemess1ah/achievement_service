package faang.school.achievement.handler;

import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractEventHandler<PostEvent> {
    public OpinionLeaderAchievementHandler(AchievementCache achievementCache,
                                           AchievementService achievementService,
                                           @Value("${achievement-titles.leader}")
                                           String leaderTitle) {
        super(achievementCache, achievementService, leaderTitle);
    }
}
