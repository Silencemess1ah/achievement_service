package faang.school.achievement.redis.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterAchievementHandlerPost extends PostEventHandler {

    public WriterAchievementHandlerPost(AchievementService achievementService,
                                        AchievementCache achievementCacheService,
                                        @Value("${achievements.writer}") String writerAchievement) {
        super(achievementService, achievementCacheService, writerAchievement);
    }
}
