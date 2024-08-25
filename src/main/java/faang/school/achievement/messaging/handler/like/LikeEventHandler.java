package faang.school.achievement.messaging.handler.like;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.like.LikeEvent;
import faang.school.achievement.messaging.handler.AbstractEventHandler;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class LikeEventHandler extends AbstractEventHandler<LikeEvent> {

    public LikeEventHandler(AchievementCache achievementCache,
                            AchievementService achievementService,
                            String title,
                            AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }
}
