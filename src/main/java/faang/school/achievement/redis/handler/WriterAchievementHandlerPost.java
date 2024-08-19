package faang.school.achievement.redis.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.event.PostEvent;
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


    @Override
    public void createProgressIfNecessary(PostEvent event, long achievementId) {
        achievementService.createProgressIfNecessary(event.getAuthorId(), achievementId);
    }

    @Override
    public void tryGiveAchievement(AchievementProgress achievementProgress, Achievement achievement) {
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, achievementProgress.getUserId());
        }
    }

    @Override
    public AchievementProgress incrementAchievementProgress(PostEvent event, long achievementId) {
        return achievementService.incrementAchievementProgress(event.getAuthorId(), achievementId);
    }
}
