package faang.school.achievement.messaging.handler.like;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.like.LikeEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AllLoveAchievementHandler extends LikeEventHandler {

    public AllLoveAchievementHandler(AchievementCache achievementCache,
                                     AchievementService achievementService,
                                     @Value("${listener.type.achievements.all_love}") String title,
                                     AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }

    @Override
    public void handle(LikeEvent event) {
        processEvent(event);
    }
}
