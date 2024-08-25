package faang.school.achievement.messaging.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.messaging.handler.AbstractEventHandler;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class PostEventHandler extends AbstractEventHandler<PostEvent> {

    public PostEventHandler(AchievementCache achievementCache,
                            AchievementService achievementService,
                            String title,
                            AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }
}