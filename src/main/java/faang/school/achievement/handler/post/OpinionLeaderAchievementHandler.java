package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends PostEventHandler {
    public OpinionLeaderAchievementHandler(AchievementCache achievementCache,
                                           AchievementService achievementService,
                                           @Value("OPINION LEADER") String title) {
        super(achievementCache, achievementService, title);
    }

    @Async
    @Override
    public void handle(PostEvent postEvent) {
        processEvent(postEvent);
    }
}
