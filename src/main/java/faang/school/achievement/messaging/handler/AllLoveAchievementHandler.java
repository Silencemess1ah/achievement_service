package faang.school.achievement.messaging.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllLoveAchievementHandler extends LikeEventHandler {

    public AllLoveAchievementHandler(AchievementService achievementService,
                                     AchievementCache achievementCache,
                                     @Value("${achievement.all_love}") String achievementTitle) {
        super(achievementService, achievementCache, achievementTitle);
    }

    @Override
    public void handle(LikeEvent event) {
        processEvent(event);
    }
}
